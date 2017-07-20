package org.sireum.awas.witness

import java.io.StringWriter
import java.util

import org.jgrapht.ext.{ComponentAttributeProvider, DOTExporter, StringComponentNameProvider}
import org.sireum.awas.fptc.{FlowEdge, FlowGraph, FlowNode}
import org.sireum.awas.symbol.SymbolTableHelper
import org.sireum.util.{FileResourceUri, mlinkedMapEmpty}

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
      .replace("strict digraph G {", "strict digraph G { \n rankdir=LR;")
  }

  type Edge = FlowEdge[FlowNode]

  val H = SymbolTableHelper

  private val attProvider = new ComponentAttributeProvider[FlowNode] {
    override def getComponentAttributes(node: FlowNode): util.Map[String, String] = {
      import scala.collection.JavaConverters._
      val res = mlinkedMapEmpty[String, String]
      res("shape") = "plaintext"
      res("id") = node.getUri
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
      val label = table(attr("border") := 0,
        attr("cellborder") := 1,
        attr("cellspacing") := 0,
        attr("cellpadding") := 1,
        attr("VALIGN") := "Middle",
        tr(
          td(colspan := 3, attr("bgcolor") := "#eeccff",
            b(
              if (vertex.isComponent) "Component: " + H.uri2IdString(vertex.getUri)
              else "Connection: " + H.uri2IdString(vertex.getUri))
          )
        ),
        tr(
          td(
            attr("align") := "Center")
          (attr("bgcolor") := "#F8F8F8")
          (
            i("In ports")
          ),
          td(
            attr("align") := "Center")
          (attr("bgcolor") := "#F8F8F8")
          (i("Flows")),
          td(
            attr("align") := "Center")
          (attr("bgcolor") := "#F8F8F8")
          (i("Out ports"))
        ),
        tr(td(attr("cellpadding") := 0, table(attr("border") := 0,
          attr("cellspacing") := 0, attr("cellpadding") := 0,
          for ((portid, uri, text) <- inPorts) yield
            tr(
              td(attr("port") := portid, attr("href") := "templink", attr("border") := 0,
                attr("target") := uri, attr("cellpadding") := 0, id := "badlink",
                attr("cellspacing") := 0, tabledata(text))
            ))),
          td(),
          td(attr("cellpadding") := 0, table(attr("border") := 0,
            attr("cellspacing") := 0, attr("cellpadding") := 0,
            for ((portid, uri, text) <- outPorts) yield
              tr(
                td(attr("port") := portid, attr("href") := "templink", attr("border") := 0,
                  attr("target") := uri, attr("cellpadding") := 0, id := "badlink",
                  attr("cellspacing") := 0, tabledata(text))
              ))))
      ).render
      "<" + label + ">"
    }
  }

  private def tabledata(text: String) = table(attr("border") := 0,
    attr("bgcolor") := "white", attr("cellborder") := 0, attr("cellspacing") := 0,
    attr("cellpadding") := 0, tr(td(text)))


  private val eAttrProvider = new ComponentAttributeProvider[Edge] {
    override def getComponentAttributes(component: Edge): util.Map[String, String] = {
      import scala.collection.JavaConverters._
      val res = mlinkedMapEmpty[String, String]
      if (component.source.isComponent) {
        res("tailport") = component.sourcePort.get.split('$').last
        res("headport") = component.targetPort.get.split('$').last
      }
      if (component.target.isComponent) {
        res("headport") = component.targetPort.get.split('$').last
        res("tailport") = component.sourcePort.get.split('$').last
      }
      res.asJava
    }
  }

}
