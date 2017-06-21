/*
 Copyright (c) 2017, Robby, Kansas State University
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

package org.sireum.awas.sanitycheck

import org.sireum.awas.ast._
import org.sireum.awas.symbol.SymbolTableMessage._
import org.sireum.awas.symbol.{ComponentSymbolTable, SymbolTable}
import org.sireum.util._

object IntraComponentChecks {
  var model: Model = _


  def apply(m: Model, st: SymbolTable)
           (implicit reporter: AccumulatingTagReporter): Model = {
    this.model = m
    build(m, st)
    m
  }

  def build(m: Model, st: SymbolTable)(
    implicit reporter: AccumulatingTagReporter): Unit = {
    st.components.foreach {
      c =>
        val cst = st.componentTable(c)
        val cd = st.component(c)
        checkFlows(cst, cd)
    }
  }

  def checkFlows(cst: ComponentSymbolTable, cd: ComponentDecl)(
    implicit reporter: AccumulatingTagReporter): Unit = {
    cst.flows.foreach {
       furi =>
         val flow = cst.flow(furi)
         if(flow.from.isDefined) {
           flowExpCheck(cst, cd, flow.from.get, flow.fromE)
         }
        if(flow.to.isDefined) {
          flowExpCheck(cst, cd, flow.to.get, flow.toE)
        }
    }
  }

  def flowExpCheck(cst : ComponentSymbolTable,
                   cd : ComponentDecl,
                   pName : Id,
                   eNames: Seq[Fault])(
                    implicit reporter: AccumulatingTagReporter): Unit = {
    val puri = cst.ports.find(_.endsWith("#" + pName.value))
    if(puri.isDefined) {
      eNames.foreach {
        err =>
          val error = cst.propagation(puri.get).find(_.endsWith(err.enum.value.last.value))
          if(error.isEmpty) {
            reporter.report(errorMessageGen(FLOW_MISSING,
              cd,
              model, puri + " -  " + err.enum.value.last.value))
            //error not found
          }
      }
    } else {
      //port not found
    }
  }

//  def checkBehaviors(cst : ComponentSymbolTable, cd : ComponentDecl) = {
//    if(cd.behaviour.isDefined) {
//       cd.behaviour.get.exprs.foreach {
//         exp => if(exp.lhs.isDefined) {}
//       }
//    }
//  }

//  def tupleFlowCheck(cst : ComponentSymbolTable,
//                     cd : ComponentDecl, tuple : Tuple,
//                     isFrom : Boolean) = {
//    tuple.tokens.keySet.foreach { pname =>
//      //  val errorSet =
//      var errorSet = tuple.tokens(pname) match {
//        case f : Fault => isetEmpty + f.enum
//        case fs : FaultSet => fs.value.map(_.enum)
//      }
//      val matchFlows = if(isFrom) {cst.flows.filter(x => cst.flow(x).from.isDefined &&
//        cst.flow(x).from.get.value == pname.value)}
//      else {
//        cst.flows.filter(x => cst.flow(x).to.isDefined &&
//          cst.flow(x).to.get.value == pname.value)
//      }
//
//      matchFlows.foreach {
//
//        furi =>
//          val flow = cst.flow(furi)
//          flow
//      }
//
//
//
//
//    }
//
//  }

}
