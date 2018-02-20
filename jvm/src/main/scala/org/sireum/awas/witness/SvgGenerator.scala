package org.sireum.awas.witness

import java.io.StringWriter
import java.util

import org.jgrapht.io._
import org.sireum.awas.ast.PrettyPrinter
import org.sireum.awas.fptc.{FlowEdge, FlowGraph, FlowNode}
import org.sireum.awas.symbol.{Resource, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{FileResourceUri, IMap, mlinkedMapEmpty, ilistEmpty}

import scalatags.Text.all._

object SvgGenerator {
  def apply(graph: FlowGraph[FlowNode]): String = {
    val de = new DOTExporter[FlowNode, Edge](nIdProvider,
      nlabelProvide, null,
      attProvider, eAttrProvider)
    val sw = new StringWriter()
    de.exportGraph(graph.graph, sw)
    //TODO: avoid replaces, write your own dot exporter
    sw.toString
      .replaceAll("label=\"<<", "label=<<")
      .replaceAll(">>\"", ">>")
      .replace("strict digraph G {", "strict digraph G { \n rankdir=TB; ")
  }

  type Edge = FlowEdge[FlowNode]

  val H = SymbolTableHelper

  private val attProvider = new ComponentAttributeProvider[FlowNode] {
    override def getComponentAttributes(node: FlowNode): util.Map[String, Attribute] = {
      import scala.collection.JavaConverters._
      val res = mlinkedMapEmpty[String, Attribute]
      res("shape") = new DefaultAttribute("plaintext", AttributeType.STRING)
      res("id") = new DefaultAttribute(node.getUri, AttributeType.STRING)
      res.asJava
    }
  }

  private val nIdProvider = new StringComponentNameProvider[FlowNode] {
    override def getName(component: FlowNode): FileResourceUri = {
      component.getUri.split(H.ID_SEPARATOR).last
    }
  }

  private val nlabelProvide = new StringComponentNameProvider[FlowNode] {
    override def getName(vertex: FlowNode): String = {
      val inPorts = vertex.inPorts.map(it => (it.split('$').last, it, H.uri2IdString(it))).toSeq
      val outPorts = vertex.outPorts.map(it => (it.split('$').last, it, H.uri2IdString(it))).toSeq
      val errors = vertex.ports.map(it => (it, vertex.getPropagation(it).toSeq)).toMap
      val flows = if (vertex.isComponent) vertex.getFlows.map(it => (it._1, it._2.toString)).toSeq else ilistEmpty

      val label = tag("font")(attr("POINT-SIZE") := 12, table(attr("border") := 0,
        attr("cellborder") := 1,
        attr("cellspacing") := 0,
        attr("cellpadding") := 1,
        attr("VALIGN") := "Middle",
        tr(
          td(if (vertex.isFlowDefined) colspan := 3 else colspan := 2, // attr("bgcolor") := "#eeccff",
            attr("cellpadding") := 5, attr("href") := "templink", attr("target") := vertex.getUri,
            b(
              if (vertex.isComponent) "Component: " + H.uri2IdString(vertex.getUri)
              else "Connection: " + H.uri2IdString(vertex.getUri))
          )
        ),
        if (vertex.isFlowDefined && vertex.isComponent) tr(
          td(attr("align") := "Center", attr("bgcolor") := "#F8F8F8", i("In ports")),
          td(attr("align") := "Center", attr("bgcolor") := "#F8F8F8", i("Flows")),
          td(attr("align") := "Center", attr("bgcolor") := "#F8F8F8", i("Out ports")))
        else
          tr(
            td(attr("align") := "Center", attr("bgcolor") := "#F8F8F8", i("In ports")),
            td(attr("align") := "Center", attr("bgcolor") := "#F8F8F8", i("Out ports"))),
        if (vertex.isFlowDefined && vertex.isComponent) tr(portContent(inPorts, errors),
          flowContent(flows), portContent(outPorts, errors))
        else tr(portContent(inPorts, errors),
          portContent(outPorts, errors))
      )).render
      "<" + label + ">"
    }
  }

  private def flowContent(flows: Seq[(ResourceUri, String)]) =
    td(attr("cellpadding") := 0, table(attr("border") := 0,
      attr("cellspacing") := 0, attr("cellpadding") := 0,
      for ((uri, text) <- flows) yield
        tr(td(attr("cellpadding") := 0, table(attr("border") := 0,
          attr("cellspacing") := 0, attr("cellpadding") := 0,
          tr(td(attr("border") := 0, attr("href") := "templink",
            attr("target") := uri, attr("cellpadding") := 0, id := "badlink",
            attr("cellspacing") := 0, tabledata(text, false)))
        )))
    ))

  private def portContent(inPorts: Seq[(String, ResourceUri, String)],
                          errors: IMap[String, Seq[String]]) =
    td(attr("cellpadding") := 0, table(attr("border") := 0,
      attr("cellspacing") := 0, attr("cellpadding") := 0, attr("ROWS") := "*",
      for ((portid, uri, text) <- inPorts) yield
        tr(td(attr("cellpadding") := 0,
          table(attr("border") := 0,
            attr("cellspacing") := 0, attr("cellpadding") := 0,
            tr(td(attr("port") := portid, attr("href") := "templink", attr("border") := 0,
              if (errors(uri).size > 1) colspan := errors(uri).size else colspan := 1,
              //if(errors(uri).isEmpty) attr("border") := 1 else attr("border") := 0, attr("sides") := "B",
              attr("target") := uri, attr("cellpadding") := 0, id := "badlink",
              attr("cellspacing") := 0, tabledata(text, false))),
            tr(
              if (errors(uri).nonEmpty)
                for (error <- errors(uri)) yield
                  td(attr("target") := "Error" + ":" + uri + ':' + error, attr("href") := "templink",
                    attr("cellpadding") := 2, //id := "badlink", attr("border") := 1, attr("sides") := "B",
                    attr("cellspacing") := 0, tag("font")(attr("POINT-SIZE") := 8,
                      tabledata(H.uri2IdString(error),
                        if (inPorts.last != (portid, uri, text)) true else false)))
              else
                td()
            )
          )))))


  private def tabledata(text: String, border: Boolean) = table(attr("border") := 0,
    attr("bgcolor") := "white", attr("cellborder") := 0, attr("cellspacing") := 0,
    attr("cellpadding") := 0, tr(if (border) td(//attr("border") := 0, attr("sides") := "B",
      attr("cellspacing") := 0, attr("cellpadding") := 0, text)
    else td(text)))


  private val eAttrProvider = new ComponentAttributeProvider[Edge] {
    override def getComponentAttributes(component: Edge): util.Map[String, Attribute] = {
      import scala.collection.JavaConverters._
      val res = mlinkedMapEmpty[String, Attribute]
      if (component.source.isComponent) {
        res("tailport") = new DefaultAttribute(component.sourcePort.get.split('$').last, AttributeType.STRING)
        res("headport") = new DefaultAttribute(component.targetPort.get.split('$').last, AttributeType.STRING)
        res("edgehref") = new DefaultAttribute("templink", AttributeType.STRING)
        res("target") = new DefaultAttribute("Edge+" + component.sourcePort.get + ":" + component.targetPort.get, AttributeType.STRING)
        res("arrowsize") = new DefaultAttribute(".7", AttributeType.STRING)
        res("weight") = new DefaultAttribute("1", AttributeType.STRING)
        res("penwidth") = new DefaultAttribute("2", AttributeType.STRING)
      }
      if (component.target.isComponent) {
        res("headport") = new DefaultAttribute(component.targetPort.get.split('$').last, AttributeType.STRING)
        res("tailport") = new DefaultAttribute(component.sourcePort.get.split('$').last, AttributeType.STRING)
        res("edgehref") = new DefaultAttribute("templink", AttributeType.STRING)
        res("target") = new DefaultAttribute("Edge+" + component.sourcePort.get + ":" + component.targetPort.get, AttributeType.STRING)
        res("arrowsize") = new DefaultAttribute(".7", AttributeType.STRING)
        res("weight") = new DefaultAttribute("1", AttributeType.STRING)
        res("penwidth") = new DefaultAttribute("2", AttributeType.STRING)

      }
      res.asJava
    }
  }

}
