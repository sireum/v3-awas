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

package org.sireum.awas.symbol

import java.net.URI

import org.sireum.awas.ast.Node
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{ISeq, _}

object Resource {

  //TODO: Refactor this, to restrict the exposure of resourceInfo,
  // use of private is a temp fix
  private [Resource] val resourceInfo = MIdMap[Node, Resource]

  def hasResourceInfo(n: Node): Boolean = resourceInfo.keySet.contains(n)

  def getResource(n : Node) : Option[Resource] = {
    if(hasResourceInfo(n)) {
      Some(resourceInfo(n))
    } else {
      None
    }
  }

  def useDefResolve(use : Node, defn: Node) = {
    assert(resourceInfo.get(defn).isDefined)
    val defResource = resourceInfo(defn)
    resourceInfo(use) = ResourceBean(defResource.uriType,
      defResource.uriPaths, defResource.uri,
      Some(!defResource.isDef))
  }

  def apply(uriType: String) = build(uriType, ivectorEmpty[String], "")

  def apply(uriType: String,
            uriPath: ISeq[String],
            uri: ResourceUri,
            isDef: Option[Boolean],
            n: Node) = build(uriType, uriPath, uri, isDef, Some(n))

  def apply(uriType: String,
            res: Resource,
            uri: ResourceUri,
            isDef: Option[Boolean],
            n: Node) = build(uriType, res, uri, isDef, Some(n))

  def apply(uriType: String,
            res: Resource,
            uri: ResourceUri,
            isDef: Option[Boolean] = None) = build(uriType, res, uri, isDef, None)

  def build(uriType: String,
            uriPath: ISeq[String],
            uri: ResourceUri,
            isDef: Option[Boolean] = None,
            n: Option[Node] = None): Resource = {
    val res = ResourceBean(uriType, uriPath, uri, isDef)
    if(n.isDefined)
      resourceInfo(n.get) = res
    res
  }

  def build(uriType: String,
            res: Resource,
            uri: ResourceUri,
            isDef: Option[Boolean],
            n: Option[Node]): Resource =
    build(uriType, res.uriPaths :+ res.uri, uri, isDef, n)

  def reset: Unit = {
    resourceInfo.retain((_,_) => false)
  }

}

case class ResourceBean(var _uriType: String,
                        var _uriPaths: ISeq[String],
                        var _uri: ResourceUri,
                        var _def: Option[Boolean] = None
                       ) extends Resource {
  self =>
  def uriType: String = {
    this._uriType
  }

  def uriPaths: ISeq[String] = {
    _uriPaths
  }

  def uri: ResourceUri = {
    _uri
  }

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

  override def toUri : ResourceUri =  new URI(
    self.uriType,
    self.uriPaths.foldLeft("")(_ + "/" + _),
    self.uri
  ).toASCIIString

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
}
