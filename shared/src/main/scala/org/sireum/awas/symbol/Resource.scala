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

import java.net.URI

import org.sireum.awas.ast.{Node, PrettyPrinter}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

object Resource {

  //TODO: Refactor this, to restrict the exposure of resourceInfo,
  // use of private is a temp fix
  private[Resource] val resourceInfo = MIdMap[Node, Resource]

  private[Resource] val resourceUri = mmapEmpty[String, Resource]

  def getResource(n : Node) : Option[Resource] = {
    if(hasResourceInfo(n)) {
      Some(resourceInfo(n))
    } else {
      None
    }
  }

  def hasResourceInfo(n: Node): Boolean = resourceInfo.keySet.contains(n)

  //TODO: Rework this
  def getParentUri(uri: ResourceUri): Option[ResourceUri] = {
    val H = SymbolTableHelper
    if (getDefResource(uri).isDefined) {
      val uri_par = uri.split(":").last.split(H.ID_SEPARATOR).head
      val puri = uri_par.split('$').init.mkString("$") + H.ID_SEPARATOR + uri_par.split('$').last
      if (getDefResource(puri).isDefined) Some(getDefResource(puri).get.toUri) else None
    } else {
      None
    }
  }

  def getDefResource(uri: ResourceUri): Option[Resource] = {
    resourceUri.get(uri.split(":").last)
  }

  def useDefResolve(use: Node, defn: Node): Unit = {
    require(resourceInfo.get(defn).isDefined, "node definition :"+PrettyPrinter.print(defn)+" must have an associated resource")
    val defResource = resourceInfo(defn)
    resourceInfo(use) = ResourceBean(defResource.uriType,
      defResource.uriPaths, defResource.uri,
      Some(false))
    resourceUri(resourceInfo(use).toUri.split(':').last) = resourceInfo(defn)

  }

  def apply(uriType: String): Resource = build(uriType, ivectorEmpty[String] :+ "", "AWAS")

  def apply(uriType: String,
            uriPath: ISeq[String],
            uri: ResourceUri,
            isDef: Option[Boolean],
            n: Node): Resource = build(uriType, uriPath, uri, isDef, Some(n))

  def apply(uriType: String,
            res: Resource,
            uri: ResourceUri,
            isDef: Option[Boolean],
            n: Node): Resource = build(uriType, res, uri, isDef, Some(n))

  def apply(uriType: String,
            res: Resource,
            uri: ResourceUri,
            isDef: Option[Boolean] = None): Resource = build(uriType, res, uri, isDef, None)

  def build(uriType: String,
            res: Resource,
            uri: ResourceUri,
            isDef: Option[Boolean],
            n: Option[Node]): Resource =
    build(uriType, res.uriPaths :+ res.uri, uri, isDef, n)

  def build(uriType: String,
            uriPath: ISeq[String],
            uri: ResourceUri,
            isDef: Option[Boolean] = None,
            n: Option[Node] = None): Resource = {
    val res = ResourceBean(uriType, uriPath, uri, isDef)
    if (n.isDefined) {
      resourceInfo(n.get) = res
    }
    resourceUri(res.toUri.split(":").last) = res
    res
  }

  def reset(): Unit = {
    resourceInfo.retain((_,_) => false)
    resourceUri.clear()
  }

}

case class ResourceBean(var _uriType: String,
                        var _uriPaths: ISeq[String],
                        var _uri: ResourceUri,
                        var _def: Option[Boolean] = None
                       ) extends Resource {
  self =>
  def isDef: Boolean = {
    if (_def.isDefined)
      _def.get
    else false
  }

  def isUse: Boolean = {
    if (_def.isDefined)
      !_def.get
    else
      false
  }

  def uri(uriType: String,
          paths: ISeq[String],
          uri: ResourceUri,
          isDef: Boolean): Unit = {
    require(paths != null && !paths.contains(null) && uri != null)
    this._uriType = uriType
    this._uriPaths = uriPaths
    this._uri = uri
    this._def = Some(isDef)
  }

  def uriPaths: ISeq[String] = {
    _uriPaths
  }

  override def toUri : ResourceUri =  new URI(
    self.uriType,
    if (self.uriPaths.nonEmpty) self.uriPaths.foldLeft("")(_ + "$" + _) else "",
    self.uri
  ).toASCIIString

  def uriType: String = {
    this._uriType
  }

  def uri: ResourceUri = {
    _uri
  }
  
  override def toFullyQualifiedName: String = {
    if (this._uriPaths.size > 2) {
      val res = (this._uriPaths.drop(2) :+ _uri)
      if(res.head == "") {
        res.tail.mkString(".")
      } else {
        res.mkString(".")
      }

    } else {
      ""
    }
  }
}


trait Resource {

  def uriType: String

  def uriPaths: ISeq[String]

  def uri: ResourceUri

  def isDef: Boolean

  def isUse: Boolean

  def uri(uriType: String,
          paths: ISeq[String],
          uri: ResourceUri,
          isDef: Boolean)

  def toUri : ResourceUri

  def toFullyQualifiedName: String
}
