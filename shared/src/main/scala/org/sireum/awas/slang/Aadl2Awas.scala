package org.sireum.awas.slang


import org.sireum.aadl._
import org.sireum.aadl.ir.Transformer.PrePost
import org.sireum.aadl.ir.{EndPoint, Flow => _, Name => _, Property => _, _}
import org.sireum.awas.ast._
import org.sireum.util._
import org.sireum.{B, Z}


final class Aadl2Awas private() {
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


  var typeDecls = Node.emptySeq[TypeDecl]

  def build(aadl: ir.Aadl): Model = {
    typeDecls = typeDecls ++
      aadl.errorLib.elements.map(build).toVector ++
      buildAlias(aadl.errorLib.elements)

    val r = Model(typeDecls,
      Node.emptySeq[StateMachineDecl],
      Node.emptySeq[ConstantDecl],
      build(aadl.components.elements.head))
    r
  }

  def build(errorLib: ir.Emv2Library): TypeDecl = {
    EnumDecl(buildId(errorLib.name.name.elements.last.value),
      Node.emptySeq[Name],
      errorLib.tokens.elements.map(_.value).map(buildId).toVector)
  }

  def buildAlias(errorLibs: Seq[ir.Emv2Library]): ISeq[TypeDecl] = {
    def getAlisedType(errorLibs: Seq[ir.Emv2Library],
                      possibleLibs: Seq[String],
                      errorType: String): Name = {
      val lib = possibleLibs.filter(p => errorLibs.exists(el =>
        (el.name.name.elements.head.value == p) &&
          el.tokens.elements.map(_.value).toSet.contains(errorType)))
      Name(ivectorEmpty[Id] :+ buildId(lib.head) :+ buildId(errorType))
    }

    ivectorEmpty ++ errorLibs.flatMap(errorLib =>
      errorLib.alias.entries.elements.map(e =>
        AliasDecl(
          Name(ivectorEmpty[Id] :+
            buildId(errorLib.name.name.elements.last.value) :+
              buildId(e._1.value)
          ),
          NamedTypeDecl(
          getAlisedType(errorLibs, errorLib.useTypes.elements.map(_.value) :+
            errorLib.name.name.elements.last.value,
            e._2.value)))))
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
          Port(true, buildId(ACCESS_IN_PORT), None) :+ Port(false, buildId(ACCESS_OUT_PORT), None)
      } else { comp.features.elements.flatMap(build).toVector}
      val propagations = comp.annexes.elements.flatMap(getPropagations).toVector
      val flows = getFlows(comp)
      val subcomp = comp.subComponents.elements.flatMap { sc =>
        build(sc).map((sc.identifier, _))
      }.toMap
      val conns = comp.connections.elements.flatMap(n =>
        build(n, comp.identifier.name.map(_.value).elements)).toVector

      val (deplDecls, addedPorts) = mineBindingProps(comp.subComponents.elements,
        comp.connectionInstances.elements)



      val UpdatedSubComp = subcomp.map { sc =>
        if (addedPorts.contains(sc._1)) {
          val usc = ComponentDecl(sc._2.compName,
            sc._2.withSM,
            sc._2.ports ++ addedPorts(sc._1),
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
        None,
        None,
        UpdatedSubComp.values.toVector,
        conns,
        deployments,
        Node.emptySeq[Property]))
    } else None
  }

  def mineBindingProps(subComps: Seq[ir.Component], connInst: Seq[ir.ConnectionInstance])
  : (ISet[DeploymentDecl], IMap[ir.Name, ISet[Port]]) = {
    var resDepl = isetEmpty[DeploymentDecl]
    var resPorts = imapEmpty[ir.Name, ISet[Port]]
    subComps.foreach { subComp =>
      subComp.properties.elements.foreach { p =>
        p.name.name.elements.last.value match {
          case ACTUAL_CONNECTION_BINDING => {
            resPorts += (subComp.identifier ->
              (resPorts.getOrElse(subComp.identifier, isetEmpty[Port]) +
                Port(true, Id(CONNECTION_IN), None)))
            resPorts += (subComp.identifier ->
              (resPorts.getOrElse(subComp.identifier, isetEmpty[Port]) +
                Port(false, Id(CONNECTION_OUT), None)))
            p.propertyValues.elements.foreach {
              case rp: ir.ReferenceProp => {
                if (subComps.map(_.identifier).toSet.contains(rp.value)) {
                  resPorts += (rp.value ->
                    (resPorts.getOrElse(rp.value, isetEmpty[Port]) +
                      Port(true, Id(BINDINGS_IN), None)))
                  resPorts += (rp.value ->
                    (resPorts.getOrElse(rp.value, isetEmpty[Port]) +
                      Port(false, Id(BINDINGS_OUT), None)))
                  resDepl = resDepl + DeploymentDecl(
                    Name(subComp.identifier.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(CONNECTION_OUT)), Name(rp.value.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(BINDINGS_IN)))
                  resDepl = resDepl + DeploymentDecl(Name(rp.value.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(BINDINGS_OUT)), Name(subComp.identifier.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(CONNECTION_IN)))
                }
              }
              case _ =>
            }
          }
          case ACTUAL_FUNCTION_BINDING =>
          case ACTUAL_MEMORY_BINDING =>
          case ACTUAL_PROCESSOR_BINDING => {
            resPorts += (subComp.identifier ->
              (resPorts.getOrElse(subComp.identifier, isetEmpty[Port]) +
                Port(true, Id(PROCESSOR_IN), None)))
            resPorts += (subComp.identifier ->
              (resPorts.getOrElse(subComp.identifier, isetEmpty[Port]) +
                Port(false, Id(PROCESSOR_OUT), None)))
            p.propertyValues.elements.foreach {
              case rp: ir.ReferenceProp => {
                if (subComps.map(_.identifier).toSet.contains(rp.value)) {
                  resPorts += (rp.value ->
                    (resPorts.getOrElse(rp.value, isetEmpty[Port]) +
                      Port(true, Id(BINDINGS_IN), None)))
                  resPorts += (rp.value ->
                    (resPorts.getOrElse(rp.value, isetEmpty[Port]) +
                      Port(false, Id(BINDINGS_OUT), None)))
                  resDepl = resDepl + DeploymentDecl(
                    Name(subComp.identifier.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(PROCESSOR_OUT)), Name(rp.value.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(BINDINGS_IN)))
                  resDepl = resDepl + DeploymentDecl(Name(rp.value.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(BINDINGS_OUT)), Name(subComp.identifier.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(PROCESSOR_IN)))
                }
              }
              case _ =>
            }
          }
          case ACTUAL_SUBPROGRAM_CALL_BINDING =>
          case ALLOWED_CONNECTION_BINDING =>
          case ALLOWED_MEMORY_BINDING =>
          case ALLOWED_PROCESSOR_BINDING =>
          case ALLOWED_SUBPROGRAM_CALL_BINDING =>
          case _ =>
        }

      }
    }
    connInst.foreach { ci =>
      ci.properties.elements.foreach { p =>
        p.name.name.elements.last.value match {
          case ACTUAL_CONNECTION_BINDING => {
            p.propertyValues.elements.foreach {
              case rp: ir.ReferenceProp => {
                if (subComps.map(_.identifier).toSet.contains(rp.value)) {
                  resPorts += (rp.value ->
                    (resPorts.getOrElse(rp.value, isetEmpty[Port]) +
                      Port(true, Id(BINDINGS_IN), None)))
                  resPorts += (rp.value ->
                    (resPorts.getOrElse(rp.value, isetEmpty[Port]) +
                      Port(false, Id(BINDINGS_OUT), None)))
                  resDepl = resDepl + DeploymentDecl(
                    Name(ci.connectionRefs.elements.head.name.name.elements.map(it => Id(it.value)).toVector),
                    None, Name(rp.value.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(BINDINGS_IN)))
                  resDepl = resDepl + DeploymentDecl(Name(rp.value.name.elements.map(it => Id(it.value)).toVector),
                    Some(Id(BINDINGS_OUT)),
                    Name(ci.connectionRefs.elements.head.name.name.elements.map(it => Id(it.value)).toVector),
                    None)
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
      if (kind == ConnectionKind.Access) { srcPort.get + BUS_ACCESS } else srcPort.get
    }

    val actualSinkPort = if (sinkPort.isEmpty) {
      "ACCESS"
    } else {
      if (kind == ConnectionKind.Access) { sinkPort.get + BUS_ACCESS} else sinkPort.get
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
      if (id.startsWith("al_")) {
        println("break")
      }

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
        withs = withs ++ clause.libraries.elements.map(buildName)
      }
    }
    withs
  }

  def build(featureEnd: FeatureEnd,
            isInverse: B, portId: String): Seq[Port] = {
    var pId = portId + featureEnd.identifier.name.elements.last.value
    if (featureEnd.category == ir.FeatureCategory.BusAccess) {
      pId = pId + BUS_ACCESS
    }
    if (featureEnd.direction == ir.Direction.InOut) {
      Seq(Port(isIn = true, buildId(pId + "_IN"), None),
        Port(isIn = false, buildId(pId + "_OUT"), None))
    } else if (featureEnd.direction == ir.Direction.In) {
      Seq(Port(isIn = true, buildId(pId), None))
    } else {
      Seq(Port(isIn = false, buildId(pId), None))
    }
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
    val id = buildId(prop.propagationPoint.elements.last.value)
    val uid = id.value match {
      case PROCESSOR => {
        if (prop.direction == ir.PropagationDirection.In) buildId(PROCESSOR_IN)
        else buildId(PROCESSOR_OUT)
      }
      case BINDINGS => {
        if (prop.direction == ir.PropagationDirection.In) buildId(BINDINGS_IN)
        else buildId(BINDINGS_OUT)
      }
      case CONNECTION => {
        if (prop.direction == ir.PropagationDirection.In) buildId(CONNECTION_IN)
        else buildId(CONNECTION_OUT)
      }
      case _ => id
    }

    val errors = prop.errorTokens.elements.map { et =>
      Fault(buildName(et.value))
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
