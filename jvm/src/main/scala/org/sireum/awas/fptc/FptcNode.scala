/*
 Copyright (c) 2016, Robby, Kansas State University
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this
    list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sireum.awas.fptc
//
//import org.sireum.awas.fptc.FptcTuple2Tup.{Ones, getOnes}
//import org.sireum.awas.fptc.FptcUtilities.NTup
//import org.sireum.awas.graph.AwasEdge
import org.sireum.awas.symbol.{ComponentSymbolTable, Resource, SymbolTable, SymbolTableHelper}
import org.sireum.util._
//import org.sireum.util.IMap
//
import org.sireum.awas.util.AwasUtil.ResourceUri

trait FptcNode {
  def getResourceType : String
  def uri : ResourceUri
  def ports : Iterable[ResourceUri]
  def inPorts: Iterable[ResourceUri]
  def outPorts : Iterable[ResourceUri]
  def addPortEdge(port : ResourceUri, edge : FptcEdge)
  def getEdge(port : ResourceUri) : Set[FptcEdge]
  //def getNode : Option[FptcNode]
  def getPropagation(port : ResourceUri) : Set[ResourceUri]
  def getFptcPropagation(port : ResourceUri) : Set[ResourceUri]
  def addFptcPropagation(port: ResourceUri, error_type: ResourceUri): Unit
}




object FptcNode {
  type Edge = FptcEdge
  private var nodepool = imapEmpty[ResourceUri,FptcNode]

  def createNode(uri : ResourceUri, st : SymbolTable): FptcNode = {
    if(nodepool.contains(uri)) {
      nodepool(uri)
    } else {
      val node = new FN(uri, st)
      nodepool += (uri -> node)
      node
    }
  }

  def getNode(uri : ResourceUri): Option[FptcNode] = nodepool.get(uri)

  def newPool(): Unit ={
    nodepool = imapEmpty[ResourceUri,FptcNode]
  }
}

final case class FN(uri : ResourceUri, st : SymbolTable) extends FptcNode {

  val H = SymbolTableHelper
  type Edge = FptcEdge

  var portList = isetEmpty[ResourceUri]

  val uriType = if(uri.startsWith(H.CONNECTION_TYPE)) H.CONNECTION_TYPE else H.COMPONENT_TYPE

  val compST : Option[ComponentSymbolTable] = if(uriType == H.COMPONENT_TYPE) Some(st.componentTable(uri)) else None

   if(uri.startsWith(H.CONNECTION_TYPE)) {
    val conDecl = st.connection(uri)

    val inPortUri = Resource(H.PORT_IN_VIRTUAL_TYPE,
      Resource.getResource(conDecl).get,
      conDecl.fromComp.value.map(_.value).mkString("/")+"/"+conDecl.fromPort.value,
      Some(true))

    val outPortUri = Resource(H.PORT_OUT_VIRTUAL_TYPE,
      Resource.getResource(conDecl).get,
      conDecl.toComp.value.map(_.value).mkString("/")+"/"+conDecl.toPort.value,
      Some(true))

    portList += Resource.getResourceUri(inPortUri)
    portList += Resource.getResourceUri(outPortUri)
  } else {
    portList ++= compST.get.ports.toSeq
  }

  //initialized with no error
  var fptcPropagation = ports.map(_ -> isetEmpty[ResourceUri]).toMap
  //adding initial state
  var fptcState = {
    if(uriType == H.COMPONENT_TYPE)
      if(st.compStateMachine(uri).isDefined)
        isetEmpty[ResourceUri] + st.smTable(st.compStateMachine(uri).get).states(0)
    else isetEmpty[ResourceUri]
  }

  var portEdgeMap = imapEmpty[ResourceUri, Set[Edge]]



  override def getResourceType: String = {
    if(uri.startsWith(H.COMPONENT_TYPE)) H.COMPONENT_TYPE else H.CONNECTION_TYPE
  }

  override def ports: Iterable[ResourceUri] = portList.toIterable

  override def inPorts: Iterable[ResourceUri] = ports.filter(_.startsWith(H.PORT_IN_TYPE))

  override def outPorts: Iterable[ResourceUri] = ports.filter(_.startsWith(H.PORT_OUT_TYPE))

  override def getPropagation(port: ResourceUri): Set[ResourceUri] = {
    if(uriType == H.COMPONENT_TYPE) {
      compST.get.propagation(port)
    } else {
      isetEmpty[ResourceUri]
    }
  }

  override def addFptcPropagation(port: ResourceUri, error_type: ResourceUri): Unit = {
    require(fptcPropagation.keySet.contains(port))
    var etSet = fptcPropagation(port)
    fptcPropagation += (port -> (etSet+error_type))
  }

  override def getFptcPropagation(port: ResourceUri): Set[ResourceUri] = fptcPropagation(port)

  override def addPortEdge(port: ResourceUri, edge: Edge): Unit = {
    if(portEdgeMap.keySet.contains(port)) {
      portEdgeMap = portEdgeMap + (port -> (portEdgeMap(port)+edge))
    } else portEdgeMap = portEdgeMap + (port -> (isetEmpty[Edge] + edge))
  }

  override def getEdge(port: ResourceUri): Set[Edge] =
    portEdgeMap.getOrElse(port, isetEmpty[Edge])
}

final case class FptcEdge(owner : AwasGraph[FptcNode],
                                source : FptcNode,
                                target : FptcNode) extends AwasEdge[FptcNode]{
  self : FptcEdge =>
  //either source or target should be a connection
  val conn : FptcNode = if(source.uri.startsWith(SymbolTableHelper.CONNECTION_TYPE)) source else target

  val isSourceConn : Boolean = conn == source

  override def sourcePort: Option[ResourceUri] = {
    if(isSourceConn) {
      Some(conn.outPorts.head)
    } else {
      source.outPorts.find(source.getEdge(_).contains(self))
    }
  }

  override def targetPort: Option[ResourceUri] = {
    if(isSourceConn) {
      target.inPorts.find(target.getEdge(_).contains(self))
    } else {
      Some(conn.inPorts.head)
    }
  }
}

//trait FptcNode {
//  def getType : String
//  def toString : String
//  def getTups : IVector[((NTup) => Option[Ones])]
//  def getBehaviourRhs(lhs : Tuple) : Option[Tuple]
//  def getCompInPorts: Node.Seq[Port]
//  def getCompOutPorts: Node.Seq[Port]
//  def addToInSet(in : IVector[Option[Fault]]): Unit
//  def inSetContains(in : IVector[Option[Fault]]) : Boolean
//  def addToOutSet(out : IVector[Option[Fault]]): Unit
//  def outSetContains(out : IVector[Option[Fault]]): Boolean
//  def getInSet: Set[IVector[Option[Fault]]]
//  def getOutSet: Set[IVector[Option[Fault]]]
//  def addPortEdgeInfo(port : Port, edge : AwasEdge[FptcNode])
//  def getPortEdgeInfo : IMap[Port, AwasEdge[FptcNode]]
//}
//

//  override def toString : String  = {
//    node match {
//      case n : ComponentDecl => PrettyPrinter.print(n.compName) +"::"+ `type`
//      case n : ConnectionDecl => PrettyPrinter.print(n.connName) +"::"+ `type`
//      case _ => "Error in node name"
//    }
//  }
//
//  private var inSet = isetEmpty[IVector[Option[Fault]]]
//  private var outSet = isetEmpty[IVector[Option[Fault]]]
//  private var behaviour = imapEmpty[Tuple, Tuple]
//  private val selectTup = buildMatcher()
//  private var portEdgeMap = imapEmpty[Port, AwasEdge[FptcNode]]
//
//  def getTups : IVector[((NTup) => Option[Ones])] = selectTup
//
//  private def buildMatcher(): IVector[((NTup) => Option[Ones])] = {
//
//    var result : IMap[Tuple, Tuple] = imapEmpty[Tuple, Tuple]
//
//    val temp = node match {
//      case comp : ComponentDecl => comp.behaviour
//      case conn : ConnectionDecl => conn.behaviour
//      case _ => None
//    }
//
//    if(temp.isDefined) {
//      result = temp.get.expr
//    }
//
//    behaviour = result
//
//    result.keySet.map{k => BehaviourFactory(getOnes(k))}.toVector
//  }
//
//  def getBehaviourRhs(lhs : Tuple) : Option[Tuple] = {
//    behaviour.lift(lhs)
//  }
//
//  def getType = `type`
//
//  def getCompInPorts: Node.Seq[Port] = {
//    node match {
//      case comp : ComponentDecl =>
//        comp.ports.filter{p : Port => p.isIn}
//      case _ => Node.emptySeq[Port]
//    }
//  }
//
//  def getCompOutPorts: Node.Seq[Port] = {
//    node match {
//      case comp : ComponentDecl =>
//        comp.ports.filter{p : Port => !p.isIn}
//      case _ => Node.emptySeq[Port]
//    }
//  }
//
//  def getConnFromPortName: Id = {
//    assert(this.getType == FptcNodeProperty.CONN_NODE,
//      "Node is not Connection to get from port")
//    node.asInstanceOf[ConnectionDecl].fromPort
//  }
//
//  def addToInSet(in : IVector[Option[Fault]]): Unit = {
//    inSet = inSet + in
//  }
//
//  def inSetContains(in : IVector[Option[Fault]]) : Boolean = {
//    inSet.contains(in)
//  }
//
//  def addToOutSet(out : IVector[Option[Fault]]): Unit = {
//    outSet = outSet + out
//  }
//
//  def outSetContains(out : IVector[Option[Fault]]): Boolean = {
//    outSet.contains(out)
//  }
//
//  def getInSet: Set[IVector[Option[Fault]]] = inSet
//
//  def getOutSet: Set[IVector[Option[Fault]]] = outSet
//
//  def addPortEdgeInfo(port : Port, edge : AwasEdge[FptcNode]) = {
//    portEdgeMap = portEdgeMap + ((port, edge))
//  }
//
//  def getPortEdgeInfo : IMap[Port, AwasEdge[FptcNode]] = this.portEdgeMap

//
//object FptcNodeProperty {
//  val COMP_NODE = "Component"
//  val CONN_NODE = "Connection"
//  val ERROR_NODE = "Node type unknown"
//}