/*
 *
 *  Copyright (c) 2017, Hariharan Thiagarajan, Kansas State University
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.sireum.awas.slang


import org.sireum.aadl._
import org.sireum.aadl.ir.Transformer.PrePost
import org.sireum.aadl.ir.{EndPoint, Flow => _, Name => _, Property => _, _}
import org.sireum.awas.ast._
import org.sireum.util._
import org.sireum.{B, Z, ISZ}


final class Aadl2Awas private() {

  var typeDecls = Node.emptySeq[TypeDecl]

  def build(aadl: ir.Aadl): Model = {
    typeDecls = typeDecls ++
      aadl.errorLib.elements.map(build).toVector ++
      buildAlias(aadl.errorLib.elements)

    val r = Model(typeDecls,
      Node.emptySeq[StateMachineDecl] ++ aadl.errorLib.elements.flatMap(buildSM),
      Node.emptySeq[ConstantDecl],
      build(aadl.components.elements.head))
    r
  }

  def build(errorLib: ir.Emv2Library): TypeDecl = {
    EnumDecl(buildId(errorLib.name.name.elements.last.value),
      errorLib.useTypes.elements.map(buildName).toVector,
      errorLib.errorTypeDef.elements.map(_.id).map(it =>
        buildId(it.name.elements.last.value)).toVector)
  }

  def buildAlias(errorLibs: Seq[ir.Emv2Library]): ISeq[TypeDecl] = {
    errorLibs.toVector.flatMap { lib =>
      lib.alias.elements.flatMap { als =>
        val et = als.errorType.name
        val at = als.aliseType.name
        val uses = lib.useTypes.elements
        val alibs = errorLibs.filter(it => uses.toSet.contains(it.name.name.elements.last))
        if (at.elements.size >= 2) {

          val ael = at.elements.head
          val aet = at.elements.last

          val al = alibs.find(it => it.name.name.elements.last == ael)
          if (al.isDefined) {
            al.get.errorTypeDef.elements.map(_.id.name.elements.last.value).find(_ == aet.value).map { a =>
               AliasDecl(
                Name(ivectorEmpty),
                NamedTypeDecl(Name(ivectorEmpty[Id] :+ buildId(al.get.name.name.elements.last.value) :+ buildId(a)))
              )

            }
          } else {
            None
          }
        } else {
          None
        }
      }
    }
  }

  def build(name: ir.Name): Name = {
    Name(Node.emptySeq ++ name.name.elements.map(it => buildId(it.value)))
  }

  def buildSM(errorLib: ir.Emv2Library): Seq[StateMachineDecl] = {
    errorLib.behaveStateMachine.elements.map(build)
  }

  def build(stateMachine: ir.BehaveStateMachine): StateMachineDecl = {
    StateMachineDecl(buildId(stateMachine.id.name.elements.mkString(".")),
      buildStates(stateMachine.states),
      buildEvents(stateMachine.events)
    )
  }

  def buildEvents(events: ISZ[ErrorEvent]): Node.Seq[Name] = {
    Node.emptySeq ++ events.elements.map(it => build(it.id))
  }

  def buildStates(states: ISZ[ErrorState]): Node.Seq[Name] = {
    var res = Node.emptySeq[Name]
    states.elements.foreach { s =>
      if (s.isInitial) {
        res = res.+:(build(s.id))
      } else {
        res = res :+ build(s.id)
      }
    }
    res
  }

  def buildName(name: org.sireum.String): Name = {
    Name(name.value.split("\\.").map(buildId).toVector)
  }

  def buildId(id: String): Id = {
    Id(id)
  }

  def mineComponents(comps: Seq[ir.Component])
  : (Set[ir.Component], Set[ir.Connection]) = {
    var components = isetEmpty[ir.Component]
    var connections = isetEmpty[ir.Connection]
    var workList = ilistEmpty[ir.Component]

    workList = workList ++ comps

    while (workList.nonEmpty) {
      val current = workList.head
      if (!components.contains(current)) {
        components = components + current
        connections = connections ++ current.connections.elements
        workList = workList ++ current.subComponents.elements
      }
      workList = workList.tail
    }
    (components, connections)
  }

  def build(comp: ir.Component): Option[ComponentDecl] = {
    if (comp.identifier.name.nonEmpty) {
      val id = buildId(comp.identifier.name.elements.last.value)
      val withs = getWiths(comp.annexes.elements)

      val features = if (comp.category == ir.ComponentCategory.Bus) {
        comp.features.elements.flatMap(build).toVector :+
          Port(true, buildId(Aadl2Awas.ACCESS_IN_PORT), None) :+ Port(false, buildId(Aadl2Awas.ACCESS_OUT_PORT), None)
      } else { comp.features.elements.flatMap(build).toVector}
      val propagations = comp.annexes.elements.flatMap(getPropagations).toVector
      val flows = getFlows(comp)
      val transistions = getTrans(comp)
      val behavior = getBehave(comp)
      val properties = getProperties(comp)
      val subcomp = comp.subComponents.elements.flatMap { sc =>
        build(sc).map((sc.identifier, _))
      }.toMap
      val conns = comp.connections.elements.flatMap(n =>
        build(n, comp.identifier.name.map(_.value).elements)).toVector

      val (deplDecls, addedPorts) = mineBindingProps(comp.subComponents.elements,
        comp.connectionInstances.elements)

      val UpdatedSubComp = subcomp.map { sc =>
        if (addedPorts.contains(sc._1.name)) {
          val usc = ComponentDecl(sc._2.compName,
            sc._2.withSM,
            sc._2.ports ++ addedPorts(sc._1.name),
            sc._2.propagations,
            sc._2.flows,
            sc._2.transitions,
            sc._2.behaviour,
            sc._2.subComp,
            sc._2.connections,
            sc._2.deployment,
            sc._2.properties)
          (sc._1, usc)
        } else {
          sc
        }
      }

      val deployments = deplDecls.toVector
      Some(ComponentDecl(
        id,
        withs,
        features,
        propagations,
        flows,
        transistions,
        behavior,
        UpdatedSubComp.values.toVector,
        conns,
        deployments,
        properties))
    } else None
  }

  def buildInit(pval: ir.PropertyValue): Init = {
    pval match {
      case cp: ClassifierProp => StringInit(cp.name.value)
      case rp: RangeProp => {
        RecordInit(buildName(Aadl2Awas.RANGE_PROP),
          imapEmpty[Id, Init]
            + (buildId(Aadl2Awas.RANGE_PROP_LOW) -> buildInit(rp.low))
            + (buildId(Aadl2Awas.RANGE_PROP_HIGH) -> buildInit(rp.high))
        )
      }
      case rec: RecordProp => {
        val res = rec.properties.elements.map { p =>
          p.name.name.elements.last.value
          (buildName(p.name.name.elements.last.value).value.last,
            p.propertyValues.elements.map(buildInit))
        }
        RecordInit(buildName(Aadl2Awas.RECORD_PROP), res.map { r =>
          if (r._2.size > 1) {
            (r._1, SeqInit(NamedTypeDecl(buildName(Aadl2Awas.LIST_PROP)), Node.seq(r._2)))
          } else {
            (r._1, r._2.head)
          }
        }.toMap)
      }
      case ref: ReferenceProp => NameRefInit(build(ref.value), None)
      case unit: UnitProp => {
        RecordInit(buildName(Aadl2Awas.UNIT_PROP), imapEmpty[Id, Init]
          + (buildId(Aadl2Awas.UNIT_PROP_VAL) -> StringInit(unit.value.value))
          + (buildId(Aadl2Awas.UNIT_PROP_UNIT) -> (if (unit.unit.nonEmpty) {
          SomeInit(NamedTypeDecl(buildName(Aadl2Awas.STRING_PROP)), StringInit(unit.unit.get.value))
        } else {
          NoneInit(NamedTypeDecl(buildName(Aadl2Awas.STRING_PROP)))
        })))
      }
      case vp: ValueProp => StringInit(vp.value.value)
    }
  }

  def getProperties(comp: ir.Component): Node.Seq[Property] = {
    var props = comp.properties.elements
    comp.annexes.foreach { annex =>
      if (annex.name.value == "Emv2") {
        val clause = annex.clause.asInstanceOf[Emv2Clause]
        props = props ++ clause.properties.elements
      }
    }
    val res = props.flatMap { prop =>
      val bindingPropSet = Set(Aadl2Awas.ACTUAL_CONNECTION_BINDING,
        Aadl2Awas.ACTUAL_FUNCTION_BINDING,
        Aadl2Awas.ACTUAL_MEMORY_BINDING,
        Aadl2Awas.ACTUAL_PROCESSOR_BINDING,
        Aadl2Awas.ACTUAL_SUBPROGRAM_CALL_BINDING,
        Aadl2Awas.ALLOWED_CONNECTION_BINDING,
        Aadl2Awas.ALLOWED_MEMORY_BINDING,
        Aadl2Awas.ALLOWED_PROCESSOR_BINDING,
        Aadl2Awas.ALLOWED_SUBPROGRAM_CALL_BINDING)

      if (!bindingPropSet.contains(prop.name.name.elements.last.value)) {
        val id = buildId(prop.name.name.elements.last.value)
        val value = if (prop.propertyValues.nonEmpty) {
          if (prop.propertyValues.elements.size > 1) {
            Some(SeqInit(NamedTypeDecl(buildName(Aadl2Awas.LIST_PROP)),
              Node.emptySeq ++ prop.propertyValues.elements.map(buildInit)))
          } else {
            Some(buildInit(prop.propertyValues.elements.head))
          }
        } else {
          None
        }
        val at = prop.appliesTo.elements.flatMap { it =>
          it match {
            case eer: Emv2ElementRef => {
              val errRefs = eer.errorTypes.elements.map(_.name.elements.mkString("."))
              Some(eer.name.name.elements.mkString(".") -> errRefs.toSet)
            }
            case aer: AadlElementRef => None
          }
        }
        Some(Property(id, value, at.toMap))
      } else {
        None
      }
    }
    Node.seq(res)
  }

  def mineBindingProps(subComps: Seq[ir.Component], connInst: Seq[ir.ConnectionInstance])
  : (ISet[DeploymentDecl], IMap[ISZ[org.sireum.String], ISet[Port]]) = {
    var resDepl = isetEmpty[DeploymentDecl]
    var resPorts = imapEmpty[ISZ[org.sireum.String], ISet[Port]]
    subComps.foreach { subComp =>
      subComp.properties.elements.foreach { p =>
        p.name.name.elements.last.value match {
          case Aadl2Awas.ACTUAL_CONNECTION_BINDING => {
            resPorts += (subComp.identifier.name ->
              (resPorts.getOrElse(subComp.identifier.name, isetEmpty[Port]) +
                Port(true, Id(Aadl2Awas.CONNECTION_IN), None)))
            resPorts += (subComp.identifier.name ->
              (resPorts.getOrElse(subComp.identifier.name, isetEmpty[Port]) +
                Port(false, Id(Aadl2Awas.CONNECTION_OUT), None)))
            p.propertyValues.elements.foreach {
              case rp: ir.ReferenceProp => {
                if (subComps.map(_.identifier.name).toSet.contains(rp.value.name)) {
                  resPorts += (rp.value.name ->
                    (resPorts.getOrElse(rp.value.name, isetEmpty[Port]) +
                      Port(true, Id(Aadl2Awas.BINDINGS_IN), None)))
                  resPorts += (rp.value.name ->
                    (resPorts.getOrElse(rp.value.name, isetEmpty[Port]) +
                      Port(false, Id(Aadl2Awas.BINDINGS_OUT), None)))
                  resDepl = resDepl + DeploymentDecl(
                    Name(subComp.identifier.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(Aadl2Awas.CONNECTION_OUT)),
                    Name(rp.value.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(Aadl2Awas.BINDINGS_IN))
                  )
                  resDepl = resDepl + DeploymentDecl(Name(rp.value.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(Aadl2Awas.BINDINGS_OUT)),
                    Name(subComp.identifier.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(Aadl2Awas.CONNECTION_IN))
                  )
                }
              }
              case _ =>
            }
          }
          case Aadl2Awas.ACTUAL_FUNCTION_BINDING =>
          case Aadl2Awas.ACTUAL_MEMORY_BINDING =>
          case Aadl2Awas.ACTUAL_PROCESSOR_BINDING => {
            resPorts += (subComp.identifier.name ->
              (resPorts.getOrElse(subComp.identifier.name, isetEmpty[Port]) +
                Port(true, Id(Aadl2Awas.PROCESSOR_IN), None)))
            resPorts += (subComp.identifier.name ->
              (resPorts.getOrElse(subComp.identifier.name, isetEmpty[Port]) +
                Port(false, Id(Aadl2Awas.PROCESSOR_OUT), None)))
            p.propertyValues.elements.foreach {
              case rp: ir.ReferenceProp => {
                if (subComps.map(_.identifier.name).toSet.contains(rp.value.name)) {
                  resPorts += (rp.value.name ->
                    (resPorts.getOrElse(rp.value.name, isetEmpty[Port]) +
                      Port(true, Id(Aadl2Awas.BINDINGS_IN), None)))
                  resPorts += (rp.value.name ->
                    (resPorts.getOrElse(rp.value.name, isetEmpty[Port]) +
                      Port(false, Id(Aadl2Awas.BINDINGS_OUT), None)))
                  resDepl = resDepl + DeploymentDecl(
                    Name(subComp.identifier.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(Aadl2Awas.PROCESSOR_OUT)),
                    Name(rp.value.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(Aadl2Awas.BINDINGS_IN))
                  )
                  resDepl = resDepl + DeploymentDecl(Name(rp.value.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(Aadl2Awas.BINDINGS_OUT)),
                    Name(subComp.identifier.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(Aadl2Awas.PROCESSOR_IN))
                  )
                }
              }
              case _ =>
            }
          }
          case Aadl2Awas.ACTUAL_SUBPROGRAM_CALL_BINDING =>
          case Aadl2Awas.ALLOWED_CONNECTION_BINDING =>
          case Aadl2Awas.ALLOWED_MEMORY_BINDING =>
          case Aadl2Awas.ALLOWED_PROCESSOR_BINDING =>
          case Aadl2Awas.ALLOWED_SUBPROGRAM_CALL_BINDING =>
          case _ =>
        }

      }
    }
    connInst.foreach { ci =>
      ci.properties.elements.foreach { p =>
        p.name.name.elements.last.value match {
          case Aadl2Awas.ACTUAL_CONNECTION_BINDING => {
            p.propertyValues.elements.foreach {
              case rp: ir.ReferenceProp => {
                if (subComps.map(_.identifier.name).toSet.contains(rp.value.name)) {
                  resPorts += (rp.value.name ->
                    (resPorts.getOrElse(rp.value.name, isetEmpty[Port]) +
                      Port(true, Id(Aadl2Awas.BINDINGS_IN), None)))
                  resPorts += (rp.value.name ->
                    (resPorts.getOrElse(rp.value.name, isetEmpty[Port]) +
                      Port(false, Id(Aadl2Awas.BINDINGS_OUT), None)))
                  val bus_parent = rp.value.name.elements.dropRight(1).last
                  val conn_ref = ci.connectionRefs.elements.filter(_.name.name.elements.dropRight(1).last == bus_parent)
                  if (conn_ref.nonEmpty) {
                    resDepl = resDepl + DeploymentDecl(
                      Name(conn_ref.head.name.name.elements.map(it => Id(it.value)).toVector),
                      None,
                      Name(rp.value.name.elements.map(it => Id(it.value)).toVector),
                      Some(Id(Aadl2Awas.BINDINGS_IN))
                    )
                    resDepl = resDepl + DeploymentDecl(
                      Name(rp.value.name.elements.map(it => Id(it.value)).toVector),
                      Some(Id(Aadl2Awas.BINDINGS_OUT)),
                      Name(conn_ref.head.name.name.elements.map(it => Id(it.value)).toVector),
                      None
                    )
                  }
                }
              }
              case _ =>
            }
          }
          case _ =>
        }
      }
    }

    (resDepl, resPorts)
  }

  def build(conn: ir.Connection, compName: Seq[String]): Seq[ConnectionDecl] = {
    if (conn.name.name.nonEmpty) {
      if ((conn.src.size == Z(1)) && (conn.dst.size == Z(1))) {
        buildConnection(conn.name.name.elements.last.value,
          conn.src.elements.last,
          conn.dst.elements.last,
          conn.kind,
          conn.isBiDirectional,
          compName)
      } else if (conn.src.size == conn.dst.size) {
        var res = ilistEmpty[ConnectionDecl]
        for (i <- 0 until conn.src.size.get.toInt) {
          res = res ++ buildConnection(
            conn.name.name.elements.last.value + "_" + conn.src(i).feature.get.name.elements.last,
            conn.src(i),
            conn.dst(i),
            conn.kind,
            conn.isBiDirectional,
            compName)
        }
        res
      } else {
        assert(false, "feature groups end points must be same")
        ilistEmpty[ConnectionDecl]
      }
    } else ilistEmpty[ConnectionDecl]
  }

  def buildConnection(id: String,
                      src: EndPoint,
    dst: EndPoint,
    kind : ir.ConnectionKind.Type,
                      isBidirectional: B,
                      compName: Seq[String]): Seq[ConnectionDecl] = {

    //val id = conn.name.name.elements.last.value
    val srcComp = if (src.component.name.map(_.value).elements == compName) {
      None
    } else {
      Some(buildName(src.component.name.elements.last.value))
    }
    val srcPort = src.feature.map(_.name.elements.last.value)
    val sinkComp = if (dst.component.name.map(_.value).elements == compName) {
      None
    } else {
      Some(buildName(dst.component.name.elements.last.value))
    }
    val sinkPort = dst.feature.map(_.name.elements.last.value)

    val actualSrcPort = if (srcPort.isEmpty) {
      "ACCESS"
    } else {
      if (kind == ConnectionKind.Access) { srcPort.get} else srcPort.get
    }

    val actualSinkPort = if (sinkPort.isEmpty) {
      "ACCESS"
    } else {
      if (kind == ConnectionKind.Access) { sinkPort.get} else sinkPort.get
    }

    if (isBidirectional) {
      var res = ilistEmpty[ConnectionDecl]
//      if(kind == ir.ConnectionKind.Access){
//
//
//        res = res :+ ConnectionDecl(
//          build(id + "_FORWARD"),
//          srcComp,
//          if()
//        )
//      } else {

      val superComp = if (srcComp.isEmpty) srcComp else sinkComp
      val superDir = if (srcComp.isEmpty) src.direction else dst.direction
      val forwardDirChk = if (superComp == srcComp) ir.Direction.Out else ir.Direction.In
      val backwardDirChk = if (superComp == srcComp) ir.Direction.In else ir.Direction.Out
      if (!(superComp.isEmpty && superDir.nonEmpty && (superDir.get == forwardDirChk))) {
        if (src.direction.nonEmpty && dst.direction.nonEmpty &&
          ((src.direction.get != ir.Direction.In) ||
          (dst.direction.get != ir.Direction.Out))) {

          res = res :+ ConnectionDecl(
            buildId(id + "_FORWARD"),
            srcComp,
            if (src.direction.nonEmpty && (src.direction.get == ir.Direction.InOut)) {
              if (srcComp.isEmpty) buildId(actualSrcPort + "_IN") else buildId(actualSrcPort + "_OUT")
            } else buildId(actualSrcPort),
            false,
            sinkComp,
            if (dst.direction.nonEmpty && (dst.direction.get == ir.Direction.InOut)) {
              if (sinkComp.isEmpty) buildId(actualSinkPort + "_OUT") else buildId(actualSinkPort + "_IN")
            } else buildId(actualSinkPort),
            Node.emptySeq[CFlow],
            None,
            Node.emptySeq[Property]
          )
        }
      }
      if (!(superComp.isEmpty && superDir.nonEmpty && (superDir.get == backwardDirChk))) {
        if (dst.direction.nonEmpty && src.direction.nonEmpty &&
          ((dst.direction.get != ir.Direction.In) ||
          (src.direction.get != ir.Direction.Out))) {
          res = res :+ ConnectionDecl(
            buildId(id + "_BACKWARD"),
            sinkComp,
            if (dst.direction.nonEmpty && (dst.direction.get == ir.Direction.InOut)) {
              if (sinkComp.isEmpty) buildId(actualSinkPort + "_IN") else buildId(actualSinkPort + "_OUT")
            } else buildId(actualSinkPort),
            false,
            srcComp,
            if (src.direction.nonEmpty && (src.direction.get == ir.Direction.InOut)) {
              if (srcComp.isEmpty) buildId(actualSrcPort + "_OUT") else buildId(actualSrcPort + "_IN")
            } else buildId(actualSrcPort),
            Node.emptySeq[CFlow],
            None,
            Node.emptySeq[Property]
          )
          }
        }
      //}
      res
    } else {
      ilistEmpty :+ ConnectionDecl(
        buildId(id),
        srcComp,
        if (src.direction.nonEmpty && (src.direction.get == ir.Direction.InOut)) {
          if (srcComp.isEmpty) buildId(actualSrcPort + "_IN") else buildId(actualSrcPort + "_OUT")
        } else buildId(actualSrcPort),
        false,
        sinkComp,
        if (dst.direction.nonEmpty && (dst.direction.get == ir.Direction.InOut)) {
          if (sinkComp.isEmpty) buildId(actualSinkPort + "_OUT") else buildId(actualSinkPort + "_IN")
        } else buildId(actualSinkPort),
        Node.emptySeq[CFlow],
        None,
        Node.emptySeq[Property])
    }
  }

  def getWiths(annexs: Seq[ir.Annex]): Node.Seq[Name] = {
    var withs = Node.emptySeq[Name]
    annexs.foreach { annex =>
      if (annex.name.value == "Emv2") {
        val clause = annex.clause.asInstanceOf[Emv2Clause]
        withs = withs ++ clause.libraries.elements.map(it => buildName(it.name.elements.last))
      }
    }
    withs
  }

  def build(featureEnd: FeatureEnd,
            isInverse: B, portId: String): Seq[Port] = {
    var pId = portId + featureEnd.identifier.name.elements.last.value
    if (featureEnd.direction == ir.Direction.InOut) {
      Seq(Port(isIn = true, buildId(pId + "_IN"), None),
        Port(isIn = false, buildId(pId + "_OUT"), None))
    } else if (featureEnd.direction == ir.Direction.In) {
      Seq(Port(isIn = true, buildId(pId), None))
    } else {
      Seq(Port(isIn = false, buildId(pId), None))
    }
  }

  def build(featureAccess: FeatureAccess, isInverse: B, portId: String): Seq[Port] = {
    var pId = portId + featureAccess.identifier.name.elements.last.value
//    if (featureAccess.category == ir.FeatureCategory.BusAccess) {
//      pId = pId + Aadl2Awas.BUS_ACCESS
//    }
    Seq(Port(isIn = true, buildId(pId + "_IN"), None), Port(isIn = false, buildId(pId + "_OUT"), None))
  }

  def build(featureGroup: FeatureGroup,
            isInverse: B,
            portId: String): Seq[Port] = {
    var pId = portId + featureGroup.identifier.name.elements.last.value
    featureGroup.features.elements.flatMap {
      case fe: FeatureEnd => build(fe,
        featureGroup.isInverse || isInverse,
        pId + "_")
      case fg: FeatureGroup => build(fg,
        featureGroup.isInverse || isInverse,
        pId + "_")
    }
  }

  def build(feature: ir.Feature): Seq[Port] = {
    feature match {
      case fe: FeatureEnd => build(fe, false, "")
      case fg: FeatureGroup => build(fg, false, "")
      case fa: FeatureAccess => build(fa, isInverse = false, portId = "")
    }

  }

  def getPropagations(annex: ir.Annex): Node.Seq[Propagation] = {
    var prop = isetEmpty[Propagation]
    if (annex.name.value == "Emv2") {
      val clause = annex.clause.asInstanceOf[Emv2Clause]
      prop = prop ++ clause.propagations.elements.map(getPropagation)
    }
    prop.toVector
  }

  def getPropagation(prop: ir.Emv2Propagation): Propagation = {
    val id = buildId(prop.propagationPoint.name.elements.last.value)
    val uid = id.value match {
      case Aadl2Awas.PROCESSOR => {
        if (prop.direction == ir.PropagationDirection.In) buildId(Aadl2Awas.PROCESSOR_IN)
        else buildId(Aadl2Awas.PROCESSOR_OUT)
      }
      case Aadl2Awas.BINDINGS => {
        if (prop.direction == ir.PropagationDirection.In) buildId(Aadl2Awas.BINDINGS_IN)
        else buildId(Aadl2Awas.BINDINGS_OUT)
      }
      case Aadl2Awas.CONNECTION => {
        if (prop.direction == ir.PropagationDirection.In) buildId(Aadl2Awas.CONNECTION_IN)
        else buildId(Aadl2Awas.CONNECTION_OUT)
      }
      case _ => id
    }

    val errors = prop.errorTokens.elements.map { et =>
      Fault(build(et))
    }
    val resProp = Propagation(uid, errors.toVector)
    prop.propagationPoint
    prop.direction

    resProp
  }

  def getFlows(comp: ir.Component): Node.Seq[Flow] = {
    var flows = isetEmpty[Flow]
    comp.flows.foreach { flow =>
      val id = buildId(flow.name.name.elements.last.value)

      val from = if (flow.source.nonEmpty) {

        Some(build(flow.source.get).map(_.id).filterNot(_.value.endsWith("_OUT")).last)
      } else None

      val to = if (flow.sink.nonEmpty) {
        Some(build(flow.sink.get).map(_.id).last)
      } else None

      flows = flows + Flow(id, from, Node.emptySeq[Fault], to, Node.emptySeq[Fault])
    }
    comp.annexes.foreach { annex =>
      if (annex.name.value == "Emv2") {
        val clause = annex.clause.asInstanceOf[Emv2Clause]
        clause.flows.foreach { flow =>
          val id = buildId(flow.identifier.name.elements.last.value)
          var from: Option[Id] = None
          var fromE = Node.emptySeq[Fault]
          var to: Option[Id] = None
          var toE = Node.emptySeq[Fault]
          if (flow.sourcePropagation.nonEmpty) {
            val sprop = getPropagation(flow.sourcePropagation.get)
            from = Some(sprop.id)
            fromE = sprop.errorTypes
          }
          if (flow.sinkPropagation.nonEmpty) {
            val sprop = getPropagation(flow.sinkPropagation.get)
            to = Some(sprop.id)
            toE = sprop.errorTypes
          }
          flows = flows + Flow(id, from, fromE, to, toE)
        }
      }
    }
    flows.toVector
  }

  def getTrans(comp: ir.Component): Option[Transition] = {
    var transCount = 0
    val defaultName = "T"
    var transExpr = Node.emptySeq[TransExpr]
    comp.annexes.foreach { annex =>
      val clause = annex.clause.asInstanceOf[Emv2Clause]
      if (clause.componentBehavior.nonEmpty) {
        clause.componentBehavior.get.transitions.elements.foreach { trans =>
          transExpr = transExpr :+ TransExpr(if (trans.id.nonEmpty) {
            buildId(trans.id.get.name.elements.last.value)
          } else {
            transCount = transCount + 1
            buildId(defaultName + transCount)
          }, Node.emptySeq :+ buildId(trans.sourceState.name.elements.last.value),
            Node.emptySeq :+ buildId(trans.targetState.name.elements.last.value),
            Some(build(trans.condition)))
        }
      }
    }
    if (transExpr.nonEmpty) Some(Transition(transExpr)) else None
  }

  def getBehave(component: ir.Component): Option[Behaviour] = {
    var behaveCount = 0
    val defaultName = "B"
    var behaveExpr = Node.emptySeq[BehaveExpr]
    component.annexes.foreach { annex =>
      val clause = annex.clause.asInstanceOf[Emv2Clause]
      if (clause.componentBehavior.nonEmpty) {
        clause.componentBehavior.get.propagations.elements.foreach { prop =>
          behaveExpr = behaveExpr :+ BehaveExpr(
            if (prop.id.nonEmpty) buildId(prop.id.get.name.elements.last.value) else {
              behaveCount = behaveCount + 1
              buildId(defaultName + behaveCount)
            }, if (prop.condition.nonEmpty) {
              Some(build(prop.condition.get))
            } else {
              None
            }, if (prop.target.nonEmpty) Some(buildTuple(prop.target)) else None,
            Node.seq(prop.source.elements.map(it => buildId(it.name.elements.last.value)))
          )
        }
      }
    }
    if (behaveExpr.nonEmpty) Some(Behaviour(behaveExpr)) else None
  }

  def build(errorCond: ir.ErrorCondition): ConditionTuple = {
    errorCond match {
      case and: ir.AndCondition => And(build(and.op.elements.head), build(and.op.elements.last))
      case or: ir.OrCondition => Or(build(or.op.elements.head), build(or.op.elements.last))
      case all: ir.AllCondition => All(Node.emptySeq ++ all.op.elements.map(build))
      case orMore: ir.OrMoreCondition => {
        OrMore(orMore.number.toInt, Node.emptySeq ++ orMore.conditions.elements.map(build))
      }
      case orLess: ir.OrLessCondition => {
        OrLess(orLess.number.toInt, Node.emptySeq ++ orLess.conditions.elements.map(build))
      }
      case pc: ir.ConditionTrigger => {
        if (pc.events.nonEmpty) {
          EventRef(Node.seq(pc.events.elements.map(it => buildId(it.name.elements.last.value))))
        } else {
          buildTuple(pc.propagationPoints)
        }
      }
    }
  }

  def buildTuple(props: ISZ[Emv2Propagation]): Tuple = {
    val tokens = props.elements.map { pp =>
      val id = buildId(pp.propagationPoint.name.elements.last.value)
      val faults = pp.errorTokens.elements.map { et => Fault(build(et)) }
      val errors = if (faults.size > 1) FaultSet(ilinkedSetEmpty ++ faults) else faults.head
      (id, errors)
    }
    Tuple(tokens.toList)
  }


  def collectComp(aadlModel: ir.Aadl): Unit = {
    var resColl = isetEmpty[ir.Component]

    val trans: ir.Transformer[Unit] =
      new ir.Transformer[Unit](new PrePost[Unit] {
        def string: org.sireum.String = ""

        override def preComponent(ctx: Unit, o: Component):
        Transformer.PreResult[Unit, Component] = {
          resColl = resColl + o
          super.preComponent(ctx, o)
        }
      })

    trans.transformAadl((), aadlModel)
    resColl.foreach(c => println(c.identifier.name.elements.last))
  }

  //  def getQualifiedErrorToken(token : Id) : String = {
  //    typeDecls.foreach{td =>
  //      td match {
  //        case en : EnumDecl => if(en.elements.contains
  //
  //      }
  //    }
  //  }

}


object Aadl2Awas {

  val ALLOWED_CONNECTION_BINDING = "Deployment_Properties::Allowed_Connection_Binding"
  val ACTUAL_CONNECTION_BINDING = "Deployment_Properties::Actual_Connection_Binding"
  val ACTUAL_PROCESSOR_BINDING = "Deployment_Properties::Actual_Processor_Binding"
  val ALLOWED_PROCESSOR_BINDING = "Deployment_Properties::Allowed_Processor_Binding"
  val ALLOWED_MEMORY_BINDING = "Deployment_Properties::Allowed_Memory_Binding"
  val ACTUAL_MEMORY_BINDING = "Deployment_Properties::Actual_Memory_Binding"
  val ACTUAL_FUNCTION_BINDING = "Deployment_Properties::Actual_Function_Binding"
  val ALLOWED_SUBPROGRAM_CALL_BINDING = "Deployment_Properties::Allowed_Subprogram_Call_Binding"
  val ACTUAL_SUBPROGRAM_CALL_BINDING = "Deployment_Properties::Actual_Subprogram_Call_Binding"

  val PROCESSOR_IN = "processor_IN"
  val PROCESSOR_OUT = "processor_OUT"
  val BINDINGS_IN = "bindings_IN"
  val BINDINGS_OUT = "bindings_OUT"
  val CONNECTION_IN = "connection_IN"
  val CONNECTION_OUT = "connection_OUT"

  val BINDINGS = "bindings"
  val PROCESSOR = "processor"
  val CONNECTION = "connection"

  val BUS_ACCESS = "_bus_access"
  val BUS_ACCESS_IN = "_bus_access_IN"
  val BUS_ACCESS_OUT = "_bus_access_OUT"

  val ACCESS_PORT = "ACCESS"
  val ACCESS_IN_PORT = "ACCESS_IN"
  val ACCESS_OUT_PORT = "ACCESS_OUT"

  val RANGE_PROP = "RANGE_PROP"
  val RANGE_PROP_LOW = "RANGE_PROP_LOW"
  val RANGE_PROP_HIGH = "RANGE_PROP_HIGH"
  val RECORD_PROP = "RECORD_PROP"
  val LIST_PROP = "LIST_PROP"
  val UNIT_PROP = "UNIT_PROP"
  val UNIT_PROP_VAL = "UNIT_PROP_VAL"
  val UNIT_PROP_UNIT = "UNIT_PROP_UNIT"
  val STRING_PROP = "STRING_PROP"



  def apply(aadlModel: ir.Aadl): Model = {

    new Aadl2Awas().build(aadlModel)
  }

  def apply(json: String): Option[Model] = {
    val aadl = org.sireum.aadl.ir.JSON.toAadl(json)
    if(aadl.leftOpt.nonEmpty) {
      Some(new Aadl2Awas().build(aadl.left))
    } else None
  }

  //  def generateVisualizer(awasFile: String,
  //                         queryFile: String,
  //                         outputFolder: String): Unit = {
  //    Visualizer.main(Array(awasFile, queryFile, outputFolder))
  //  }
  //
  //  def generateVisualizer(awasFile: String,
  //                         queryFile: String,
  //                         outputFolder: String,
  //                         sireumJarLoc: String): Unit = {
  //    Visualizer.main(Array(awasFile, queryFile, outputFolder, sireumJarLoc))
  //  }


}
