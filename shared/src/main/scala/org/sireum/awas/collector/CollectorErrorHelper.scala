package org.sireum.awas.collector

import org.sireum.awas.collector.CollectorErrorHelper.ReachAnalysisStage.ReachAnalysisStage
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{ErrorMessage, ErrorTag, WarningMessage, WarningTag}

object CollectorErrorHelper {

  object ReachAnalysisStage extends Enumeration {
    type ReachAnalysisStage = Value
    val Node, Port, FlowError, FPTC, Behave, Other, Query = Value
  }

  val INSUFFICIENT_FLOW_INFO_ERROR =
    "Missing flow relationship for port & Error: '%s'"

  val MISSING_CRITERIA =
    "Incorrect or undefined criteria '%s'"

  val FLOW_INFO_MISSING =
    "Missing flow relationships for node '%s'"

  val INSUFFICIENT_FLOW_INFO =
    "Missing flow relationship for port '%s'"

  val MISSING_NODE =
    "Missing node related to port '%s'"

  val MISSING_PORT =
    "Missing port definition '%s' in graph"

  val GRAPH_INCONSISTENT =
    "Operation on two or more different graphs '%s'"

  val TYPE_UNKNOWN =
    "Query criteria type unknown"

  val MISSING_RESULT =
    "Result for query '%s' not found"

  def errorMessageGen(template: String,
                      uri: ResourceUri,
                      Kind: ReachAnalysisStage): ErrorTag = {
    val KIND = Kind.toString
    val message = template.format(uri)
    ErrorMessage(KIND, message)
  }

  def warningMessageGen(template: String,
                        uri: ResourceUri,
                        Kind: ReachAnalysisStage): WarningTag = {
    val KIND = Kind.toString
    val message = template.format(uri)
    WarningMessage(KIND, message)
  }
}
