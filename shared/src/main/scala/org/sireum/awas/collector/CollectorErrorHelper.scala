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
