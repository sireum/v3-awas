/*
 Copyright (c) 2016, Robby, Hariharan Thiagarajan, Kansas State University
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

package org.sireum.awas.util

import org.sireum.util.{ErrorTag, FileLocationInfoErrorMessage, ISeq, LocationInfoErrorMessage, ivectorEmpty}
import org.sireum.awas.ast._
import org.sireum.awas.symbol.TypeTable

/**
  * Created by hariharan on 12/8/16.
  */
object AwasUtil {
  type ResourceUri = String



  def toUri(n : ISeq[Name], tt:TypeTable) : ISeq[ResourceUri] = {
    var res = ivectorEmpty[ResourceUri]
    n.map(_.value.map(_.value).mkString("/")).foreach{
      e => val eUri = tt.enumElements.find(_.endsWith(e))
        res :+= eUri.get
    }
    res
  }
}
