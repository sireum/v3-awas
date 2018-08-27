/*
 * // #Sireum
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
 *
 */

package org.sireum.awas.query

import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

object QResMinType extends Enumeration {
  type QResMinType = Value
  val Uri, PathUri, Error, PathError = Value
}

sealed trait QueryResult extends Product

sealed trait UnitResult extends QueryResult

final case class QRes(unitRes: ISet[UnitResult])
  extends QueryResult {
  def toPorts: ISet[ResourceUri] = {

    var res = this.unitRes.flatMap { (f: QueryResult) =>
      f match {
        case u: UriResult => isetEmpty[ResourceUri] + u.value
        case p: PathResult => p.values
        case e: ErrorResult => isetEmpty[ResourceUri] + e.port
        case ep: ErrorPathResult => ep.path.keySet
        case _ => isetEmpty[ResourceUri]
      }
    }
    res
  }

  override def toString: String = {
    var res = ""
    val sortedURes = this.unitRes.toSeq.sortWith(_.hashCode() < _.hashCode())
    sortedURes.foreach {
      case u: UriResult => res = res + u.value.toString + "\n"
      case p: PathResult => res =
        res + "{ " + p.values.toSeq.sorted.mkString(", ") + " } \n"
      case e: ErrorResult => res =
        res + e.port.toString + "[ " + e.errors.toSeq.sorted.mkString(", ") + " ]\n"
      case ep: ErrorPathResult => res =
        res + "Path "+sortedURes.indexOf(ep)+1+":\n"+ep.path.toSeq.sortWith(_.hashCode() < _.hashCode()).map(it =>
          it._1.toString + "{" + it._2.toSeq.sorted.mkString(", ") + "}").mkString("\n")
      case _ =>
    }
    res
  }

}

final case class UriResult(value: ResourceUri) extends UnitResult

final case class PathResult(values: ISet[ResourceUri]) extends UnitResult

final case class ErrorResult(port: ResourceUri,
                             errors: ISet[ResourceUri]) extends UnitResult

final case class ErrorPathResult(path: IMap[ResourceUri, ISet[ResourceUri]])
  extends UnitResult


object QueryResult {

  import QResMinType._

  def union(v1: QRes, v2: QRes): QRes = {
    val v1Min = getMinType(v1)
    val v2Min = getMinType(v2)
    val vmin = if (v1Min <= v2Min) v1Min else v2Min
    QRes(convertToType(v1, vmin).unitRes.union(convertToType(v2, vmin).unitRes))
  }

  def intersect(v1: QRes, v2: QRes): QRes = {
    val v1Min = getMinType(v1)
    val v2Min = getMinType(v2)
    val vmin = if (v1Min < v2Min) v1Min else v2Min
    val v1converted = convertToType(v1, vmin)
    val v2converted = convertToType(v2, vmin)
    if (vmin == QResMinType.Error) {
      var res = imapEmpty[ResourceUri, ISet[ResourceUri]]
      var v1Map = imapEmpty[ResourceUri, ISet[ResourceUri]]
      v1converted.unitRes.foreach {
        f =>
          v1Map += (f.asInstanceOf[ErrorResult].port ->
            (v1Map.getOrElse(f.asInstanceOf[ErrorResult].port, isetEmpty[ResourceUri]) ++ f.asInstanceOf[ErrorResult].errors))
      }
      var v2Map = imapEmpty[ResourceUri, ISet[ResourceUri]]
      v2converted.unitRes.foreach {
        f =>
          v2Map += (f.asInstanceOf[ErrorResult].port ->
            (v2Map.getOrElse(f.asInstanceOf[ErrorResult].port, isetEmpty[ResourceUri]) ++ f.asInstanceOf[ErrorResult].errors))
      }
      v1Map.keySet.foreach { v1k =>
        if (v2Map.keySet.contains(v1k)) {
          res = res + (v1k -> v1Map(v1k).intersect(v2Map(v1k)))
        }
      }

      QRes(res.map(it => ErrorResult(it._1, it._2)).toSet)
    } else {
      QRes(v1converted.unitRes.intersect(v2converted.unitRes))

    }
  }

  def diff(v1: QRes, v2: QRes): QRes = {
    val v1Min = getMinType(v1)
    val v2Min = getMinType(v2)
    val vmin = if (v1Min < v2Min) v1Min else v2Min
    val v1converted = convertToType(v1, vmin)
    val v2converted = convertToType(v2, vmin)
    QRes(v1converted.unitRes.diff(v2converted.unitRes))
  }

  def getMinType(in: QRes): QResMinType = {
    var res = QResMinType.PathError
    in.unitRes.foreach { v =>
      if (getType(v) < res) {
        res = getType(v)
      }
    }
    res
  }

  def convertToType(in: QRes, minType: QResMinType): QRes = {
    val res = QRes(isetEmpty[UnitResult])
    assert(minType <= getMinType(in))
    minType match {
      case QResMinType.Uri => QRes(in.unitRes.map(toMinType(_, minType)).flatMap(_.unitRes))
      case QResMinType.PathUri => QRes(in.unitRes.map(toMinType(_, minType)).flatMap(_.unitRes))
      case QResMinType.Error => {
        var res = imapEmpty[ResourceUri, ISet[ResourceUri]]
        in.unitRes.foreach { f =>
          toMinType(f, minType).unitRes.foreach {
            inner =>
              val er = inner.asInstanceOf[ErrorResult]
              res += (er.port -> res.getOrElse(er.port, isetEmpty[ResourceUri]).++(er.errors))
          }
        }
        QRes(res.map(it => ErrorResult(it._1, it._2)).toSet)
      }
      case _ => in
    }
  }

  def toMinType(in: UnitResult, minType: QResMinType): QRes = {
    //    assert(getType(in) <= minType)

    getType(in) match {
      case Uri => QRes(isetEmpty[UnitResult] + in)
      case PathUri => if (minType == Uri) {
        QRes(in.asInstanceOf[PathResult].values.map(UriResult))
      } else {
        QRes(isetEmpty[UnitResult] + in)
      }
      case Error => {
        assert(minType != QResMinType.PathUri)
        minType match {
          case QResMinType.Uri => {
            QRes(isetEmpty[UnitResult] +
              UriResult(in.asInstanceOf[ErrorResult].port))
          }
          case _ => QRes(isetEmpty[UnitResult] + in)

        }
      }
      case PathError => {
        minType match {
          case Uri => {
            QRes(in.asInstanceOf[ErrorPathResult].path.keySet.map(UriResult))
          }
          case PathUri => {
            QRes(isetEmpty[UnitResult] +
              PathResult(in.asInstanceOf[ErrorPathResult].path.keySet))
          }
          case Error => {
            var res = imapEmpty[ResourceUri, ISet[ResourceUri]]
            in.asInstanceOf[ErrorPathResult].path.foreach {
              f =>
                if (res.keySet.contains(f._1)) {
                  res += (f._1 -> res.getOrElse[ISet[ResourceUri]](f._1, isetEmpty[ResourceUri]).++(f._2))
                }
            }
            QRes(res.map(it => ErrorResult(it._1, it._2)).toSet)
          }
          case PathError => QRes(isetEmpty[UnitResult] + in)
        }
      }
    }
  }

  def isSameType(in: QRes): Boolean = {
    var res = true
    if (in.unitRes.nonEmpty) {
      val first = getType(in.unitRes.head)
      in.unitRes.foreach {
        f => if (res) {
          if (first != getType(f)) {
            res = !res
          }
        }
      }
      res
    } else {
      !res
    }
  }

  def getType(in: UnitResult): QResMinType = {
    in match {
      case p: UriResult => QResMinType.Uri
      case p: PathResult => QResMinType.PathUri
      case p: ErrorResult => QResMinType.Error
      case p: ErrorPathResult => QResMinType.PathError
    }
  }
}