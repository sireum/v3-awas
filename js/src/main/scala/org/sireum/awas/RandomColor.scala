/*
 *
 * Copyright (c) 2019, Robby, Kansas State University
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sireum.awas

import org.scalajs.dom.document
import org.scalajs.dom.svg.Defs
import org.sireum.util.ISet

object RandomColor extends Function0[String] {
  private var i = 0
  private var cid = 0

  val colors = Seq(
    "#f07830",
    "#c0d830",
    "#184890",
    "#303090",
    "#189048",
    "#f0f018",
    "#1878c0",
    "#903090",
    "#c01830",
    "#a81860",
    "#d8a818",
    "#f06060",
    "#60c048",
    "#f03018",
    "#603090",
    "#78a848",
    "#6048a8",
    "#48a848",
    "#d83030",
    "#f0f0a8",
    "#f0f078",
    "#48a8d8",
    "#18a8c0",
    "#c04830",
    "#ffd8a8",
    "#4860a8",
    "#f0c0a8",
    "#ffd878",
    "#f01830",
    "#78c0d8",
    "#f0c078",
    "#f0a8c0",
    "#d878a8",
    "#ffc000",
    "#90d8f0",
    "#ffd830",
    "#30c078",
    "#f01860",
    "#a890c0",
    "#60c090",
    "#4890c0",
    "#c0d878",
    "#90a8d8",
    "#f07890",
    "#f04878",
    "#7890c0",
    "#9060a8",
    "#6078c0",
    "#78c0a8"
  )

  def apply(): String = {
    if (i == colors.size) {
      i = 0
    }
    val res = colors(i)
    i = i + 1
    res
  }

  def reset() = {
    cid = 0
    i = 0
  }

  def stripeColor(colors: ISet[String]): (String, Defs) = {
    val sortedColors = colors.toList.sorted
    val id = "c" + cid
    cid = cid + 1
    val namespaceURI = "http://www.w3.org/2000/svg"
    val stripedef = document.createElementNS(namespaceURI, "defs").asInstanceOf[Defs]
    val gradient = document.createElementNS(namespaceURI, "linearGradient")
    gradient.setAttribute("id", id)
    gradient.setAttribute("x1", "0%")
    gradient.setAttribute("y1", "0%")
    gradient.setAttribute("x2", "100%")
    gradient.setAttribute("y2", "100%")

    for (a <- 0 to 20) {
      val stop = document.createElementNS(namespaceURI, "stop")
      stop.setAttribute("offset", (a * 5).toString + "%")
      stop.setAttribute("style", "stop-color:" + sortedColors(a % sortedColors.length) + ";stop-opacity:1")
      gradient.appendChild(stop)
    }
    stripedef.appendChild(gradient)
    (id, stripedef)
  }

  def colorViolation(violationColor: String, typeColor: String): (String, Defs) = {

    val id = "c" + cid
    cid = cid + 1
    val namespaceURI = "http://www.w3.org/2000/svg"
    val stripedef = document.createElementNS(namespaceURI, "defs").asInstanceOf[Defs]
    val gradient = document.createElementNS(namespaceURI, "linearGradient")
    gradient.setAttribute("id", id)
    gradient.setAttribute("x1", "0%")
    gradient.setAttribute("y1", "0%")
    gradient.setAttribute("x2", "100%")
    gradient.setAttribute("y2", "0%")


    val stop1 = document.createElementNS(namespaceURI, "stop")
    stop1.setAttribute("offset", "10%")
    stop1.setAttribute("style", "stop-color:" + violationColor + ";stop-opacity:1")
    gradient.appendChild(stop1)

    val stop = document.createElementNS(namespaceURI, "stop")
    stop.setAttribute("offset", "20%")
    stop.setAttribute("style", "stop-color:" + typeColor + ";stop-opacity:1")
    gradient.appendChild(stop)

    stripedef.appendChild(gradient)
    (id, stripedef)
  }
}
