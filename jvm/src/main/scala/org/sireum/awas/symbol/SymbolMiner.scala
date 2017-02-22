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

package org.sireum.awas.symbol

import org.sireum.awas.ast._
import org.sireum.awas.symbol.SymbolTableMessage._
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._
/**
  * Created by hariharan on 12/17/16.
  */

// TODO: Refactoring, make it extensible by using traits

object SymbolMiner {

  def apply(m: Model, stp: STProducer)(implicit reporter: AccumulatingTagReporter): STProducer = {
    new ModelElemMiner(stp).miner(m)
    stp
  }

  def modelMiner(stp: STProducer): Unit = {
    val H = SymbolTableHelper
    //    var parentRes =
    val tables = stp.tables
    /* TODO: Fill the symbol miners for other nodes on need basis
   *       Refactor this into multiple visitor function
   *  Fun: On each miner, create Resource and add it in symbol table
   */

  }
}

class ModelElemMiner(stp: STProducer) //extends STProducer
{
  val st = stp.tables
  val H = SymbolTableHelper

  def modelMiner(m: Model): Model = {
    var parentRes = Resource(H.HEAD)
    Visitor.build({
      case md: Model => {
        if (md.fileUriOpt.isDefined) {
          val r = Resource(H.MODEL_TYPE, parentRes, md.fileUriOpt.get, None, md)
          st.declaredSymbols(md.fileUriOpt.get) = msetEmpty + r.toUri
          stp.modelMap(r.toUri) = m
          parentRes = r
        } else {
          val r = Resource(H.MODEL_TYPE, parentRes, "", None, md)
          stp.modelMap(r.toUri) = m
          parentRes = r
        }
        false
      }
    })(m)
    m
  }

  def typeDeclMiner(m: Model)(
    implicit reporter: AccumulatingTagReporter): Model = {
    var parentRes = Resource(H.HEAD)
    Visitor.build({
      case m: Model => {
        require(Resource.getResource(m).isDefined)
        parentRes = Resource.getResource(m).get
        true
      }

      case ad: AliasDecl => false

      case ed: EnumDecl => {
        val r = Resource(H.ENUM_TYPE, parentRes, ed.name.value, Some(true), ed)
        st.typeDeclTable(r.toUri) = ed
        parentRes = r
        val tType = new stp.TypeT(r.toUri)
        st.typeTable(r.toUri) = tType
        val tt = tType.tables
        val tempSet = mmapEmpty[ResourceUri, Id]
        ed.elements.foreach { elem =>
          val er = Resource(H.ERROR_TYPE, parentRes, elem.value, Some(true), elem)

          if (!tempSet.keySet.contains(er.toUri))
            tempSet(er.toUri) = elem
          else {
            reporter.report(errorMessageGen(
              DUPLICATE_TYPE,
              elem,
              m, elem.value))
          }
        }
        tt.enumTable(r.toUri) = tempSet
        false
      }

      case ld: LatticeDecl => false

      case ed: RecordDecl => false
    })(m)
    m
  }


  def stateMachineMiner(m: Model)
                       (implicit reporter: AccumulatingTagReporter): Model = {
    var parentRes = Resource(H.HEAD)
    Visitor.build({
      case sm: StateMachineDecl => {
        var parentRes = Resource(H.HEAD)
        require(Resource.getResource(m).isDefined)
        parentRes = Resource.getResource(m).get

        val r = Resource(H.STATE_MACHINE_TYPE, parentRes, sm.smName.value, Some(true), sm)
        st.stateMachineDeclTable(r.toUri) = sm
        val smt = stp.stateMachineTableProducer(r.toUri)
        parentRes = r

        sm.states.foreach {
          s => {
            val sr = Resource(H.STATE_TYPE, parentRes, s.value, Some(true), s)
            if (!smt.tables.statesTable.keySet.contains(sr.toUri))
              smt.tables.statesTable(sr.toUri) = s
            else {
              reporter.report(errorMessageGen(
                DUPLICATE_STATE,
                s,
                m, s.value))
            }
          }
        }
        sm.events.foreach {
          e => {
            val se = Resource(H.EVENT_TYPE, parentRes, e.value, Some(true), e)
            if (!smt.tables.eventsTable.keySet.contains(se.toUri))
              smt.tables.eventsTable(se.toUri) = e
            else {
              reporter.report(errorMessageGen(DUPLICATE_EVENT,
                e,
                m, e.value)
              )
            }
          }

        }

      }
        false

    })(m)
    m
  }

  def componentElemMiner(m: Model)(
    implicit reporter: AccumulatingTagReporter): Model = {
    var parentRes = Resource(H.HEAD)
    Visitor.build({
      case m: Model =>
        require(Resource.getResource(m).isDefined)
        parentRes = Resource.getResource(m).get
        true

      case comp: ComponentDecl =>
        val r = Resource(H.COMPONENT_TYPE, parentRes, comp.compName.value, Some(true), comp)
        if (!st.componentDeclTable.keySet.contains(r.toUri)) {
          st.componentDeclTable(r.toUri) = comp
          val compTableProducer = stp.componentSymbolTableProducer(r.toUri)

          comp.withSM.foreach {
            withs => {
              //TODO: Will change in the presence of multiple files/models
              val withName = withs.value.last.value
              val smUri = st.stateMachineDeclTable.find(_._1.endsWith(withName))
              val tyUri = st.typeDeclTable.find(_._1.endsWith(withName))
              if (smUri.isDefined) {
                st.compSMTable(r.toUri) = smUri.get._1
              } else if (tyUri.isDefined) {
                st.compTypeTable(r.toUri) = tyUri.get._1
              } else {
                reporter.report(errorMessageGen(MiSSING_TYPE_OR_STATE_MACHINE,
                  withs,
                  m, withName))
              }
            }
          }

          comp.ports.foreach(portMiner(m, _, comp, r))

          comp.propagations.foreach(propagationMiner(m, _, r))

          comp.flows.foreach {
            flow => {
              val fr = Resource(H.FLOW_TYPE, r, flow.id.value, some(true), flow)
              if (!compTableProducer.tables.flowTable.contains(fr.toUri)) {
                compTableProducer.tables.flowTable(fr.toUri) = flow
                flowCheck(m, flow, r)

              } else {
                reporter.report(errorMessageGen(DUPLICATE_FLOW_NAME,
                  flow.id,
                  m, flow.id.value))
              }
            }
          }

          if (comp.behaviour.isDefined) {
            val ttUri = stp.compTypeDecl(r.toUri)
            val smUri = stp.compStateMachine(r.toUri)
            if (ttUri.isDefined) {
              val typeTable = stp.typeTable(ttUri.get)
              comp.behaviour.get.exprs.foreach(exprMiner(m, _, r, typeTable, smUri))
            }
          }
          //TODO : resolve Fault, state and events from behavior and transistion,
          // so that nodes points to the right uri
        } else {
          reporter.report(errorMessageGen(DUPLICATE_COMPONENT,
            comp,
            m, comp.compName.value))
        }

        false
    })(m)
    m
  }

  def flowCheck(m: Model, flow: Flow, r: Resource)(
    implicit reporter: AccumulatingTagReporter): Unit = {
    val ctp = stp.compMap(r.toUri)
    if(flow.from.isDefined) {
      val fromP = ctp.ports.find(_.endsWith(flow.from.get.value))
      if(fromP.isDefined) {
        Resource.useDefResolve(flow.from.get, ctp.port(fromP.get))
        if(flow.fromE.nonEmpty) {
          flow.fromE.foreach{
            e =>
              val err = e.value.map(_.value).mkString("/")
              val ttUri = stp.compTypeDecl(r.toUri).get
              val typeTable = stp.typeTable(ttUri)
              val  edecl = typeTable.enumElements.find(_.endsWith(err))
              if(edecl.isDefined) {
                Resource.useDefResolve(e,typeTable.enumElement(edecl.get))
              } else {
                reporter.report(errorMessageGen(Missing_TYPE_DECL,
                  e,
                  m, err))
              }
          }
        }
      } else {
        reporter.report(errorMessageGen(MISSING_PORT_DECL,
          flow,
          m, flow.from.get.value))
      }

    }
    if(flow.to.isDefined) {
      val fromP = ctp.ports.find(_.endsWith(flow.to.get.value))
      if(fromP.isDefined) {
        Resource.useDefResolve(flow.to.get, ctp.port(fromP.get))
        if(flow.toE.nonEmpty) {
          flow.toE.foreach{
            e =>
              val err = e.value.map(_.value).mkString("/")
              val ttUri = stp.compTypeDecl(r.toUri).get
              val typeTable = stp.typeTable(ttUri)
              val  edecl = typeTable.enumElements.find(_.endsWith(err))
              if(edecl.isDefined) {
                Resource.useDefResolve(e,typeTable.enumElement(edecl.get))
              } else {
                reporter.report(errorMessageGen(Missing_TYPE_DECL,
                  e,
                  m, err))
              }
          }
        }
      } else {
        reporter.report(errorMessageGen(MISSING_PORT_DECL,
          flow,
          m, flow.id.value))
      }

    }
  }

  def exprMiner(m: Model, expr: Expression, r: Resource, tt: TypeTable, smUri: Option[ResourceUri])(
    implicit reporter: AccumulatingTagReporter): Unit = {
    if (expr.lhs.isDefined) {
      tupleMiner(m, expr.lhs.get, r, tt)
    }
    if (expr.rhs.isDefined) {
      tupleMiner(m, expr.rhs.get, r, tt)
    }
    if (smUri.isDefined) {
      val smt = stp.smTable(smUri.get)
      expr.states.foreach {
        s =>
          val sUri = smt.states.find(_.endsWith(s.value))
          if (sUri.isDefined) {
            Resource.useDefResolve(s, smt.state(sUri.get))
          } else {
            reporter.report(errorMessageGen(MISSING_STATE_DECL,
              s,
              m, s.value))
          }
      }
    }
  }


  def tupleMiner(m: Model, tup: Tuple, r: Resource, tt: TypeTable)(
    implicit reporter: AccumulatingTagReporter): Unit = {
    tup.tokens.foreach {
      t =>
        val port = stp.componentTable(r.toUri).ports.find(_.endsWith(t._1.value))
        if (port.isDefined) {
          Resource.useDefResolve(t._1, stp.componentTable(r.toUri).port(port.get))
        } else {
          reporter.report(errorMessageGen(MISSING_PORT_DECL,
            t._1,
            m, t._1.value))
        }
        t._2 match {
          case f: Fault => mineFault(m, f, r, tt)
          case fs: FaultSet => fs.value.foreach(mineFault(m, _, r, tt))
        }
    }
  }

  def mineFault(m: Model, f: Fault, r: Resource, tt: TypeTable)(
    implicit reporter: AccumulatingTagReporter): Unit = {
    val fif = f.enum.value.map(_.value).mkString("/")
    val elem = tt.enumElements.find(_.endsWith(fif))
    if (elem.isDefined) {
      Resource.useDefResolve(f.enum, tt.enumElement(elem.get))
    } else {
      reporter.report(errorMessageGen(Missing_TYPE_DECL,
        f,
        m, fif))

    }
  }

  def propagationMiner(m: Model, p: Propagation, r: Resource)(
    implicit reporter: AccumulatingTagReporter): Unit = {
    val ctp = stp.compMap(r.toUri)
    val portUri = ctp.tables.portTable.keySet.find(_.endsWith("#" + p.id.value))

    if (portUri.isEmpty) {
      reporter.report(errorMessageGen(MISSING_PORT_DECL,
        p,
        m, p.id.value))

    } else if (st.compTypeTable.get(r.toUri).isEmpty) {
      reporter.report(errorMessageGen(MISSING_TYPE_ASSOCIATION,
        p,
        m, ""))

      val tempSet = msetEmpty[ResourceUri]
      Resource.useDefResolve(p, ctp.tables.portTable(portUri.get))
      val tt = st.typeTable(st.compTypeTable(r.toUri))
      p.errorTypes.foreach {
        et => {
          val etUri = tt.enumElements.find(_.endsWith("#" + et.value.last.value))
          if (etUri.isDefined) {
            tempSet + etUri.get
            Resource.useDefResolve(et, tt.enumElement(etUri.get))
          } else {
            val td = st.typeDeclTable(tt.uri)
            if (Resource.getResource(td).isDefined) {
              Resource.getResource(td).get.uriType match {
                case H.ENUM_TYPE =>
                  val enumD = td.asInstanceOf[EnumDecl]

                  reporter.report(errorMessageGen(Missing_TYPE_DECL,
                    p,
                    m, et.value.last.value))

              }
            } else {
              reporter.report(errorMessageGen(Missing_TYPE_DECL,
                p,
                m, et.value.last.value))

            }
          }
        }
      }
      ctp.tables.propagationTable(portUri.get) = tempSet
    }
  }


  def portMiner(m: Model, p: Port, c: ComponentDecl, r: Resource)(
    implicit reporter: AccumulatingTagReporter): Unit = {
    val ctp = stp.compMap(r.toUri)
    val pr = Resource(if (p.isIn) H.PORT_IN_TYPE else H.PORT_OUT_TYPE, r, p.id.value, Some(true), p)
    if (!ctp.tables.portTable.keySet.contains(pr.toUri)) {
      ctp.tables.portTable(pr.toUri) = p
    } else {
      reporter.report(errorMessageGen(DUPLICATE_PORT,
        p,
        m, p.id.value))

    }
  }

  def connectionMiner(m: Model)(
    implicit reporter: AccumulatingTagReporter): Model = {
    var parentRes = Resource(H.HEAD)
    Visitor.build({
      case conn: ConnectionDecl =>
        require(Resource.getResource(m).isDefined)
        parentRes = Resource.getResource(m).get

        val r = Resource(H.CONNECTION_TYPE, parentRes, conn.connName.value, Some(true), conn)

        if (!st.connectionTable.contains(r.toUri)) {
          st.connectionTable(r.toUri) = conn
          compPortCheck(m, conn.fromComp, conn.fromPort)
          compPortCheck(m, conn.toComp, conn.toPort)
        } else {

          reporter.report(errorMessageGen(DUPLICATE_CONNECTION,
            conn,
            m,conn.connName.value))
        }
        false
    })(m)
    m
  }

  def compPortCheck(m: Model, comp: Name, port: Id)(
    implicit reporter: AccumulatingTagReporter) : Unit = {
    val fcompUri = findComponent(comp)
    if (fcompUri.isDefined) {
      Resource.useDefResolve(comp, stp.component(fcompUri.get))
      val fportUri = stp.compMap(fcompUri.get).ports.find(_.endsWith("#" + port.value))
      if (fportUri.isDefined) {
        Resource.useDefResolve(port, stp.componentTable(fcompUri.get).port(fportUri.get))
      } else {
        reporter.report(errorMessageGen(MISSING_PORT_DECL,
          port,
          m, port.value))
      }
    } else {
      reporter.report(errorMessageGen(MISSING_COMPONENT,
        comp,
        m,comp.value.map(_.value).mkString("::")))
    }
  }

  def findComponent(compName: Name): Option[ResourceUri] = {
    st.componentDeclTable.keySet.find(_.endsWith("#" + compName.value.last.value))
  }

  def miner(m: Model)(implicit reporter: AccumulatingTagReporter) =
    connectionMiner(componentElemMiner(stateMachineMiner(
      typeDeclMiner(modelMiner(m)))))

}

/*
TODO: 1. error out intersection of component name and connection name
 */