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

package org.sireum.awas.fptc

import org.sireum.awas.fptc.NodeType.NodeType
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

class BasicNodeImpl[G](uri: ResourceUri,
                       st: SymbolTable,
                       owner: G)
  extends BasicNode[G] {
  self: BasicNode[G] =>

  val H = SymbolTableHelper

  //  var portList = isetEmpty[ResourceUri]

  //three types of nodes, components, connections, and ports
  def portList: Iterable[ResourceUri] = getResourceType match {
    case NodeType.COMPONENT => st.componentTable(uri).ports
    case NodeType.CONNECTION => {
      Resource.getParentUri(uri) match {
        case Some(puri) => st.componentTable(puri).connectionTable(uri).ports
        case None => {
          assert(false, "Connection " + uri + " parent not found")
          ilistEmpty
        }
      }
    }
    case NodeType.PORT => {
      Resource.getParentUri(uri) match {
        case Some(puri) => if (st.componentTable(puri).ports.toSet.contains(uri)) Seq(uri) else {
          assert(false, "Port not found in its parent component")
          ilistEmpty
        }
        case None => {
          assert(false, "Port's parent not found")
          ilistEmpty
        }
      }
    }
  }

  //  if (isComponent) {
  //    val compST = st.componentTable(uri)
  //    portList ++= compST.ports.toSeq
  //  } else {
  //    val connST = st.connectionTable(uri)
  //    portList ++= connST.ports.toSeq
  //  }

  override def getResourceType: NodeType = {
    val uriType = H.getUriType(uri)
    if (H.isPort(uri)) {
      NodeType.PORT
    } else if (uriType == H.COMPONENT_TYPE) {
      NodeType.COMPONENT
    } else {
      NodeType.CONNECTION
    }
  }


  override def getUri: ResourceUri = uri

  override def ports: Iterable[ResourceUri] = portList

  override def inPorts: Iterable[ResourceUri] = ports.filter(_.startsWith(H.PORT_IN_TYPE))

  override def outPorts: Iterable[ResourceUri] = ports.filter(_.startsWith(H.PORT_OUT_TYPE))

  override def getPropagation(port: ResourceUri): Set[ResourceUri] = {
    getResourceType match {
      case NodeType.COMPONENT => st.componentTable(uri).propagation(port)
      case NodeType.CONNECTION => {
        Resource.getParentUri(uri) match {
          case Some(puri) => st.componentTable(puri).connectionTable(uri).propagation(port)
          case None => {
            assert(false, "Connection's parent not found")
            isetEmpty[ResourceUri]
          }
        }
      }
      case NodeType.PORT => {
        Resource.getParentUri(uri) match {
          case Some(puri) => st.componentTable(puri).propagation(port)
          case None => {
            assert(false, "Port's parent not found")
            isetEmpty[ResourceUri]
          }
        }
      }
    }
  }

  override def isComponent: Boolean = getResourceType == NodeType.COMPONENT

  override def getOwner: G = owner
}
