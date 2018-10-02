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

package org.sireum.awas.symbol

import org.sireum.awas.ast.{Model, Node}
import org.sireum.util.{ErrorTag, FileLocationInfoErrorMessage, LocationInfoErrorMessage}

/**
  * Created by hariharan on 1/17/17.
  */
object SymbolTableMessage {

  val DUPLICATE_TYPE =
    "Type declaration '%s' has already been defined"

  val DUPLICATE_STATE =
    "State '%s' already declared in the state machine"

  val DUPLICATE_EVENT =
    "Event '%s' already declared in the state machine"

  val MiSSING_TYPE_OR_STATE_MACHINE =
    "Type / State machine '%s' not declared"

  val DUPLICATE_FLOW_NAME =
    "Flow Name '%s' already declared"

  val DUPLICATE_PORT =
    "Port '%s' already declared in '%s' component"

  val DUPLICATE_COMPONENT =
    "Component declaration '%s' has already been defined"

  val DUPLICATE_CONNECTION =
    "Connection declaration '%s' has already been defined"

  val MISSING_COMPONENT_OR_CONNECTION =
    "Component or Connection '%s' not found in the model"

  val MISSING_TYPE_DECL =
    "Error '%s' not found in the associated type declaration"

  val TYPE_RESOLUTION_FAILURE =
    "Error '%s' declared in more than one type declaration"

  val MISSING_PORT_DECL =
    "port '%s' not declared"

  val MISSING_STATE_DECL =
    "STATE '%s' not found in the associated state machine declaration"

  val MISSING_TYPE_ASSOCIATION =
    "Type association missing for '%s' component"

  val MISSING_COMPONENT =
    "Component declaration '%s' not found in the model"

  val FLOW_MISSING =
    "Error flow type '%s' not defined for '%s' port"

  val MISSING_ERROR_TYPE =
    "Error type '%s' not defined"

  val MISSING_PORT_REF =
    "Deployment relation can only be created using ports or connections"


  def errorMessageGen(template: String,
                      node: Node,
                      model: Model,
                      info: String*)
  : ErrorTag = {
    val KIND = "Symbol Checker"
    val message = template.format(info)
    val locInfo = model.nodeLocMap.getOrElse(node,
      LocationInfoErrorMessage("", 0, 0, 0, 0, 0, 0, ""))
    if (model.fileUriOpt.isDefined) {

      FileLocationInfoErrorMessage(KIND,
        model.fileUriOpt.get,
        locInfo.lineBegin,
        locInfo.columnBegin,
        locInfo.lineEnd,
        locInfo.columnEnd,
        locInfo.offset,
        locInfo.length,
        message)
    } else {
      LocationInfoErrorMessage(
        KIND,
        locInfo.lineBegin,
        locInfo.columnBegin,
        locInfo.lineEnd,
        locInfo.columnEnd,
        locInfo.offset,
        locInfo.length,
        message)
    }
  }

  def warningMessageGen(template: String,
                        node: Node,

                        info: String)
  : Unit = {
    val KIND = "Flow Checker"
    val message = template.format(info)

    println(KIND + " " + message)


  }

}
