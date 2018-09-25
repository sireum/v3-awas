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

package org.sireum.awas.witness


import org.sireum.awas.fptc._
import org.sireum.awas.symbol.SymbolTableHelper
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.ops.ISZOps
import org.sireum.util.{IMap, ilistEmpty, ivectorEmpty}
import org.sireum.{$Slang, ISZ, ST, $internal}
import scalatags.Text.all._

object SvgGenerator {

  def apply(graph: FlowGraph[FlowNode, FlowNode.Edge] with FlowGraphUpdate[FlowNode, FlowNode.Edge], isLR: Boolean): String = {
    //    graph.setNodeAttProvider(attProvider)
    //    graph.setNodeIdProvider(nIdProvider)
    //    graph.setEdgeAttrProvider(eAttrProvider)
    //    graph.setNodeLabelProvider(nlabelProvide)

    graph.setNodeToST(nodeToST)
    graph.setEdgeToST(edgeToST)

    graph.setGraphAttributes(ivectorEmpty[ST] :+ (if (isLR) attributeLR else attributeTB))
    graph.toDot.toString
      .replaceAll("label=\"<<", "label=<<")
      .replaceAll(">>\"", ">>")
  }

  type Edge = FlowEdge[FlowNode]

  val H = SymbolTableHelper

  private def nodeToST(node: FlowNode): ST = {
    st""" "${node.getUri}" [label="${getNodeLabel(node)}" ${
      if (H.isInPort(node.getUri)) "rank=max"
      else if (H.isOutPort(node.getUri)) "rank=min" else ""
    } shape=plaintext]"""
  }

  private def edgeToST(edge: Edge): ST = {
    st""" "${edge.source.getUri}" -> "${edge.target.getUri}" [${getEdgeAttributes(edge)}] """
  }

  private val attributeLR = st"""rankdir=LR"""

  private val attributeTB = st"""rankdir=TB"""

  //  private val attProvider = new ComponentAttributeProvider[FlowNode] {
  //    override def getComponentAttributes(node: FlowNode): util.Map[String, Attribute] = {
  //      val res = mlinkedMapEmpty[String, Attribute]
  //      res("shape") = new DefaultAttribute("plaintext", AttributeType.STRING)
  //      res("id") = new DefaultAttribute(node.getUri, AttributeType.STRING)
  //      res
  //    }
  //  }

  //  private val nIdProvider = new StringComponentNameProvider[FlowNode] {
  //    override def getName(component: FlowNode): FileResourceUri = {
  //      component.getUri.split(H.ID_SEPARATOR).last
  //    }
  //  }

  def getNodeLabel(vertex: FlowNode): String = {
    val inPorts = vertex.inPorts.map(it => (it.split('$').last, it, H.uri2IdString(it))).toSeq
    val outPorts = vertex.outPorts.map(it => (it.split('$').last, it, H.uri2IdString(it))).toSeq
    val errors = vertex.ports.map(it => (it, vertex.getPropagation(it).toSeq)).toMap
    val flows = if (vertex.isComponent) vertex.getFlows.map(it => (it._1, it._2.toString)).toSeq else ilistEmpty
    val subGraphIcon = "min/images/sub-graph-icon.png"
    val label =
      if ((vertex.getResourceType == NodeType.COMPONENT) ||
        (vertex.getResourceType == NodeType.CONNECTION)) {
        tag("font")(
          attr("POINT-SIZE") := 12, attr("FACE") := "Courier",
          table(
            attr("border") := 0,
            attr("cellborder") := 1,
            attr("cellspacing") := 0,
            attr("cellpadding") := 1,
            attr("VALIGN") := "Middle",
            attr("TITLE") := vertex.getUri.split(H.ID_SEPARATOR).last,
            tr(
              td(
                if (vertex.isFlowDefined) colspan := 3 else colspan := 2, // attr("bgcolor") := "#eeccff",
                attr("cellpadding") := 5,
                attr("href") := "templink",
                attr("title") := "node",
                attr("target") := vertex.getUri,
                if (vertex.getSubGraph.isDefined) {
                  table(
                    attr("border") := 0,
                    attr("cellborder") := 0,
                    attr("cellspacing") := 0,
                    attr("cellpadding") := 0,
                    attr("VALIGN") := "Middle",
                    tr(
                      td(attr("cellpadding") := 0, img(src := subGraphIcon, attr("SCALE") := "TRUE")),
                      td(attr("cellpadding") := 0, b("Component: " + H.uri2IdString(vertex.getUri)))
                    )
                  )
                } else
                  b(
                    if (vertex.isComponent) {
                      "Component: " + H.uri2IdString(vertex.getUri)
                    }
                    else "Connection: " + H.uri2IdString(vertex.getUri)
                  )
              )
            ),
            if (vertex.isFlowDefined && vertex.isComponent)
              tr(
                td(attr("align") := "Center", attr("bgcolor") := "#F8F8F8", i("In ports")),
                td(attr("align") := "Center", attr("bgcolor") := "#F8F8F8", i("Flows")),
                td(attr("align") := "Center", attr("bgcolor") := "#F8F8F8", i("Out ports"))
              )
            else
              tr(
                td(attr("align") := "Center", attr("bgcolor") := "#F8F8F8", i("In ports")),
                td(attr("align") := "Center", attr("bgcolor") := "#F8F8F8", i("Out ports"))
              ),
            if (vertex.isFlowDefined && vertex.isComponent)
              tr(portContent(inPorts, errors), flowContent(flows), portContent(outPorts, errors))
            else tr(portContent(inPorts, errors), portContent(outPorts, errors))
          )
        ).render
      } else {
        tag("font")(
          attr("POINT-SIZE") := 12, attr("FACE") := "Courier",
          table(
            attr("border") := 0,
            attr("cellborder") := 1,
            attr("cellspacing") := 0,
            attr("cellpadding") := 1,
            attr("VALIGN") := "Middle",
            tr(
              td(
                colspan := 1, // attr("bgcolor") := "#eeccff",
                attr("cellpadding") := 5,
                attr("href") := "templink",
                attr("title") := "node",
                attr("target") := vertex.getUri,
                b(
                  if (H.isInPort(vertex.getUri)) "In port : " + H.uri2IdString(vertex.getUri)
                  else "Out port : " + H.uri2IdString(vertex.getUri)
                )
              )
            ),
            if (H.isInPort(vertex.getUri)) tr(portContent(inPorts, errors))
            else tr(portContent(outPorts, errors))
          )
        ).render
      }
    "<" + label + ">"
  }

  private def flowContent(flows: Seq[(ResourceUri, String)]) =
    td(
      attr("cellpadding") := 0,
      table(
        attr("border") := 0,
        attr("cellspacing") := 0,
        attr("cellpadding") := 0,
        for ((uri, text) <- flows)
          yield
            tr(
              td(
                attr("cellpadding") := 0,
                table(
                  attr("border") := 0,
                  attr("cellspacing") := 0,
                  attr("cellpadding") := 0,
                  tr(
                    td(
                      attr("border") := 0,
                      attr("href") := "templink",
                      attr("title") := "flow",
                      attr("target") := uri,
                      attr("cellpadding") := 0,
                      id := "badlink",
                      attr("cellspacing") := 0,
                      tabledata(text, false)
                    )
                  )
                )
              )
            )
      )
    )

  private def portContent(inPorts: Seq[(String, ResourceUri, String)], errors: IMap[String, Seq[String]]) =
    if (inPorts.nonEmpty)
      td(
        attr("cellpadding") := 0,
        table(
          attr("border") := 0,
          attr("cellspacing") := 0,
          attr("cellpadding") := 0,
          attr("ROWS") := "*",
          for ((portid, uri, text) <- inPorts)
            yield
              tr(
                td(
                  attr("cellpadding") := 0,
                  table(
                    attr("border") := 0,
                    attr("cellspacing") := 0,
                    attr("cellpadding") := 0,
                    tr(
                      td(
                        attr("port") := portid,
                        attr("href") := "templink",
                        attr("title") := "port",
                        attr("border") := 0,
                        if (errors(uri).size > 1) colspan := errors(uri).size else colspan := 1,
                        //if(errors(uri).isEmpty) attr("border") := 1 else attr("border") := 0, attr("sides") := "B",
                        attr("target") := uri,
                        attr("cellpadding") := 0,
                        id := "badlink",
                        attr("cellspacing") := 0,
                        tabledata(text, false)
                      )
                    ),
                    tr(
                      if (errors(uri).nonEmpty)
                        for (error <- errors(uri))
                          yield
                            td(
                              attr("target") := "Error" + ":" + uri + ':' + error,
                              attr("href") := "templink",
                              attr("title") := "error",
                              attr("cellpadding") := 2, //id := "badlink", attr("border") := 1, attr("sides") := "B",
                              attr("cellspacing") := 0,
                              tag("font")(
                                attr("POINT-SIZE") := 8,
                                tabledata(
                                  H.uri2CanonicalName(error),
                                  if (inPorts.last != (portid, uri, text)) true
                                  else false
                                )
                              )
                            )
                      else
                        td()
                    )
                  )
                )
              )
        )
      )
    else td()

  private def tabledata(text: String, border: Boolean) =
    table(
      attr("border") := 0,
      attr("bgcolor") := "white",
      attr("cellborder") := 0,
      attr("cellspacing") := 0,
      attr("cellpadding") := 0,
      tr(
        if (border)
          td( //attr("border") := 0, attr("sides") := "B",
            attr("cellspacing") := 0,
            attr("cellpadding") := 0,
            text
          )
        else td(text)
      )
    )

  def getEdgeAttributes(component: Edge): String = {
    var res = ISZ[String]()
    res = res :+ "tailport" + "=" + "\"" + component.sourcePort.get.split('$').last + "\""
    res = res :+ "headport" + "=" + "\"" + component.targetPort.get.split('$').last + "\""
    res = res :+ "edgehref" + "=" + "templink"
    res = res :+ "title" + "=" + "edgetype"
    res = res :+ "target" + "=" + "\"" + "Edge+" + component.sourcePort.get + "+" + component.targetPort.get + "\""
    res = res :+ "arrowsize" + "=" + "1"
    res = res :+ "weight" + "=" + "1"
    res = res :+ "penwidth" + "=" + "2"
    ISZOps(res).foldLeft[String]({ (x, y) => x + " " + y }, "")
  }

}
