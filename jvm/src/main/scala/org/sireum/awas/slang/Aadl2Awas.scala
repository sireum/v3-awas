package org.sireum.awas.slang

import org.sireum.aadl.skema.ast
import org.sireum.aadl.skema.ast.Emv2Clause
import org.sireum.awas.ast._
import org.sireum.awas.witness.Visualizer
import org.sireum.util._


final class Aadl2Awas private() {
  var typeDecls = Node.emptySeq[TypeDecl]

  def build(aadl: ast.Aadl): Model = {
    typeDecls = typeDecls ++ aadl.errorLib.elements.map(build).toVector
    val (comps, conns) = mineComponents(aadl.components.elements)
    val tcomp: Node.Seq[ComponentDecl] = comps.flatMap { it: ast.Component => build(it) }.toVector
    val tconn: Node.Seq[ConnectionDecl] = conns.flatMap { it: ast.Connection => build(it) }.toVector

    val r = Model(typeDecls,
      Node.emptySeq[StateMachineDecl],
      Node.emptySeq[ConstantDecl],
      tcomp,
      tconn,
      Node.emptySeq[DeploymentDecl])
    r
  }

  def build(errorLib: ast.Emv2Library): TypeDecl = {
    EnumDecl(buildId(errorLib.name.name.elements.last.value),
      Node.emptySeq[Name],
      errorLib.tokens.elements.map(_.value).map(buildId).toVector)
  }

  def buildName(name: org.sireum.String): Name = {
    Name(name.value.split("\\.").map(buildId).toVector)
  }

  def buildId(id: String): Id = {
    Id(id)
  }

  def mineComponents(comps: Seq[ast.Component])
  : (Set[ast.Component], Set[ast.Connection]) = {
    var components = isetEmpty[ast.Component]
    var connections = isetEmpty[ast.Connection]
    var workList = ilistEmpty[ast.Component]

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

  def build(comp: ast.Component): Option[ComponentDecl] = {
    if (comp.identifier.name.nonEmpty) {
      Some(ComponentDecl(
        buildId(comp.identifier.name.elements.last.value),
        getWiths(comp.annexes.elements),
        comp.features.elements.flatMap(build).toVector,
        comp.annexes.elements.flatMap(getPropagations).toVector,
        getFlows(comp),
        None,
        None,
        Node.emptySeq[Property]))
    } else None
  }

  def build(conn: ast.Connection): Option[ConnectionDecl] = {
    if (conn.name.name.nonEmpty) {
      val id = buildId(conn.name.name.elements.last.value)
      val srcComp = buildName(conn.src.component.name.elements.last.value)
      val srcPort = buildId(conn.src.feature.name.elements.last.value)
      val sinkComp = buildName(conn.dst.component.name.elements.last.value)
      val sinkPort = buildId(conn.dst.feature.name.elements.last.value)
      Some(ConnectionDecl(id, srcComp, srcPort, false, sinkComp,
        sinkPort, Node.emptySeq[CFlow], None, Node.emptySeq[Property]))
    } else None
  }

  def getWiths(annexs: Seq[ast.Annex]): Node.Seq[Name] = {
    var withs = Node.emptySeq[Name]
    annexs.foreach { annex =>
      if (annex.name.value == "Emv2") {
        val clause = annex.clause.asInstanceOf[Emv2Clause]
        withs = withs ++ clause.libraries.elements.map(buildName)
      }
    }
    withs
  }

  def build(feature: ast.Feature): Seq[Port] = {
    var portId = feature.identifier.name.elements.last.value
    if (feature.category == ast.FeatureCategory.BusAccess) {
      portId = portId + "__BUS_"
    }
    if (feature.direction == ast.Direction.InOut) {
      Seq(Port(isIn = true, buildId(portId + "IN"), None),
        Port(isIn = false, buildId(portId + "OUT"), None))
    } else if (feature.direction == ast.Direction.In) {
      Seq(Port(isIn = true, buildId(portId), None))
    } else {
      Seq(Port(isIn = false, buildId(portId), None))
    }
  }

  def getPropagations(annex: ast.Annex): Node.Seq[Propagation] = {
    var prop = isetEmpty[Propagation]
    if (annex.name.value == "Emv2") {
      val clause = annex.clause.asInstanceOf[Emv2Clause]
      prop = prop ++ clause.propagations.elements.map(getPropagation)
    }
    prop.toVector
  }

  def getPropagation(prop: ast.Emv2Propagation): Propagation = {
    val id = buildId(prop.propagationPoint.elements.last.value)
    val errors = prop.errorTokens.elements.map { et =>
      Fault(buildName(et.value))
    }
    Propagation(id, errors.toVector)
  }

  def getFlows(comp: ast.Component): Node.Seq[Flow] = {
    var flows = isetEmpty[Flow]
    comp.flows.foreach { flow =>
      val id = buildId(flow.name.name.elements.last.value)
      val from = if (flow.source.nonEmpty) {
        Some(buildId(flow.source.get.value))
      } else None
      val to = if (flow.sink.nonEmpty) {
        Some(buildId(flow.sink.get.value))
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
  def apply(aadlModel: ast.Aadl): Model = {
    new Aadl2Awas().build(aadlModel)
  }

  def apply(json: String): Model = {
    val aadl = org.sireum.aadl.skema.ast.JSON.toAadl(json)
    new Aadl2Awas().build(aadl)
  }

  def generateVisualizer(awasFile: String,
                         queryFile: String,
                         outputFolder: String): Unit = {
    Visualizer.main(Array(awasFile, queryFile, outputFolder))
  }


}
