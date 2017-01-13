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

import org.sireum.awas.ast._
import org.sireum.awas.util.AwasUtil.{ResourceUri, _}
import org.sireum.util._

/**
  * Created by hariharan on 12/17/16.
  */

// TODO: Refactoring, make it extensible by using traits

object SymbolMiner {

  type Seq[T] = IVector[T]

  final def emptySeq[T] = ivectorEmpty[T]

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
          val modelUri = Resource.getResourceUri(r)
          st.declaredSymbols(md.fileUriOpt.get) = msetEmpty + modelUri
          stp.modelMap(modelUri) = m
          parentRes = r
        } else {
          val r = Resource(H.MODEL_TYPE, parentRes, "", None, md)
          val modelUri = Resource.getResourceUri(r)
          //st.declaredSymbols(md.fileUriOpt.get) = msetEmpty + modelUri
          stp.modelMap(modelUri) = m
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
        val typeUri = Resource.getResourceUri(r)
        st.typeDeclTable(typeUri) = ed
        parentRes = r
        val tType = new stp.TypeT(typeUri)
        st.typeTable(Resource.getResourceUri(r)) = tType
        val tt = tType.tables
        val tempSet = mmapEmpty[ResourceUri, Id]
        ed.elements.foreach { elem =>
          val er = Resource(H.ERROR_TYPE, parentRes, elem.value, Some(true), elem)
          val erUri = Resource.getResourceUri(er)
          if (!tempSet.keySet.contains(erUri))
            tempSet(Resource.getResourceUri(er)) = elem
          else {
            reporter.report(errorMessageGen(
              s"Enumeration \'${elem.value}\' already exist in the type declaration ${ed.name.value}",
              elem,
              m))
          }
        }
        tt.enumTable(typeUri) = tempSet
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
        val smUri = Resource.getResourceUri(r)
        st.stateMachineDeclTable(smUri) = sm
        val smt = stp.stateMachineTableProducer(smUri)
        parentRes = r

        sm.states.foreach {
          s => {
            val sr = Resource(H.STATE_TYPE, parentRes, s.value, Some(true), s)
            if (!smt.tables.statesTable.keySet.contains(Resource.getResourceUri(sr)))
              smt.tables.statesTable(Resource.getResourceUri(sr)) = s
            else {
              reporter.report(errorMessageGen(
                s"State \'${s.value}\' already exist in the state machine ${sm.smName.value}",
                s,
                m))
            }
          }
        }


        sm.events.foreach {
          e => {
            val se = Resource(H.EVENT_TYPE, parentRes, e.value, Some(true), e)
            if (!smt.tables.eventsTable.keySet.contains(Resource.getResourceUri(se)))
              smt.tables.eventsTable(Resource.getResourceUri(se)) = e
            else {
              reporter.report(errorMessageGen(
                s"Event \'${e.value}\' already exist in the state machine ${sm.smName.value}",
                e,
                m)
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
      case m: Model => {
        require(Resource.getResource(m).isDefined)
        parentRes = Resource.getResource(m).get
        true
      }

      case comp: ComponentDecl => {
        val r = Resource(H.COMPONENT_TYPE, parentRes, comp.compName.value, Some(true), comp)
        val compUri = Resource.getResourceUri(r)
        if (!st.componentDeclTable.keySet.contains(compUri)) {
          st.componentDeclTable(compUri) = comp
          val compTableProducer = stp.componentSymbolTableProducer(compUri)

          comp.withSM.foreach {
            withs => {
              //TODO: Will change in the presence of multiple files/models
              val withName = withs.value.last.value
              val smUri = st.stateMachineDeclTable.find(_._1.endsWith(withName))
              val tyUri = st.typeDeclTable.find(_._1.endsWith(withName))
              if (smUri.isDefined) {
                st.compSMTable(compUri) = smUri.get._1
              } else if (tyUri.isDefined) {
                st.compTypeTable(compUri) = tyUri.get._1
              } else {
                //TODO: Included Type or State machine not found
              }
            }
          }

          comp.ports.foreach(portMiner(m, _, comp, r))

          comp.propagations.foreach(propagationMiner(m, _, r))

          comp.flows.foreach {
            flow => {
              val fr = Resource(H.FLOW_TYPE, r, flow.id.value, some(true), flow)
              if (!compTableProducer.tables.flowTable.contains(Resource.getResourceUri(fr))) {
                compTableProducer.tables.flowTable(Resource.getResourceUri(fr)) = flow
                flowCheck(m,flow, r,stp.typeTable(stp.compTypeDecl(compUri).get))

              } else {
                //TODO: Error flow id already exist
              }
            }
          }

          if (comp.behaviour.isDefined) {
            val ttUri = stp.compTypeDecl(compUri)
            val smUri = stp.compStateMachine(compUri)
            if (ttUri.isDefined) {
              val typeTable = stp.typeTable(ttUri.get)
              comp.behaviour.get.exprs.foreach(exprMiner(m, _, r, typeTable, smUri))
            }
          }
          //TODO : resolve Fault, state and events from behavior and transistion,
          // so that nodes points to the right uri
        } else {
          //        TODO: "Component name already exist in the model"
        }

        false
      }
    })(m)
    m
  }

  def flowCheck(m : Model, flow:Flow, r : Resource, tt : TypeTable)(
    implicit reporter: AccumulatingTagReporter): Unit = {
    val ctp = stp.compMap(Resource.getResourceUri(r))
    if(flow.from.isDefined) {
      val fromP = ctp.ports.find(_.endsWith(flow.from.get.value))
      if(fromP.isDefined) {
        Resource.useDefResolve(flow.from.get, ctp.port(fromP.get))
        if(flow.fromE.nonEmpty) {
          flow.fromE.foreach{
            e =>
              val err = e.value.map(_.value).mkString("/")
              val ttUri = stp.compTypeDecl(r.getUri).get
              val typeTable = stp.typeTable(ttUri)
              val  edecl = typeTable.enumElements.find(_.endsWith(err))
              if(edecl.isDefined) {
                Resource.useDefResolve(e,typeTable.enumElement(edecl.get))
              } else {
                reporter.report(errorMessageGen(
                  s"Error \'${err}\' not found in the associated type declaration",
                  e,
                  m)
                )
              }
          }

        }

      } else {
        reporter.report(errorMessageGen(
          s"port \'${flow.from.get.value}\' used in flow ${flow.id.value}, not found in component",
          flow,
          m)
        )
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
              val ttUri = stp.compTypeDecl(r.getUri).get
              val typeTable = stp.typeTable(ttUri)
              val  edecl = typeTable.enumElements.find(_.endsWith(err))
              if(edecl.isDefined) {
                Resource.useDefResolve(e,typeTable.enumElement(edecl.get))
              } else {
                reporter.report(errorMessageGen(
                  s"Error \'${err}\' not found in the associated type declaration",
                  e,
                  m)
                )
              }
          }

        }

      } else {
        reporter.report(errorMessageGen(
          s"port \'${flow.to.get.value}\' used in flow ${flow.id.value}, not found in component",
          flow,
          m)
        )
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
            reporter.report(errorMessageGen(
              s"State \'${s.value}\' specified in behavior not found in the component state machine",
              s,
              m)
            )
          }
      }
    }
  }


  def tupleMiner(m: Model, tup: Tuple, r: Resource, tt: TypeTable)(
    implicit reporter: AccumulatingTagReporter): Unit = {
    tup.tokens.foreach {
      t =>
        val port = stp.componentTable(r.getUri).ports.find(_.endsWith(t._1.value))
        if (port.isDefined) {
          Resource.useDefResolve(t._1, stp.componentTable(r.getUri).port(port.get))
        } else {
          reporter.report(errorMessageGen(
            s"Port \'${t._1.value}\' specified in behavior not found in the component",
            t._1,
            m)
          )
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
      reporter.report(errorMessageGen(
        s"Error type \'${fif}\' specified in behavior not found in the type declaration associated with the component",
        f,
        m)
      )
    }
  }

  def propagationMiner(m: Model, p: Propagation, r: Resource)(
    implicit reporter: AccumulatingTagReporter): Unit = {
    val ctp = stp.compMap(Resource.getResourceUri(r))
    val portUri = ctp.tables.portTable.keySet.find(_.endsWith("#" + p.id.value))
    val compUri = Resource.getResourceUri(r)
    if (!portUri.isDefined) {
      reporter.report(errorMessageGen(
        s"Port \'${p.id.value}\' specified in propagation, not found in the component",
        p,
        m)
      )
    } else if (!st.compTypeTable.get(compUri).isDefined) {
      reporter.report(errorMessageGen(
        s"There is no type declaration associated with the component",
        p,
        m)
      )
    } else {
      val tempSet = msetEmpty[ResourceUri]
      Resource.useDefResolve(p, ctp.tables.portTable(portUri.get))
      val tt = st.typeTable(st.compTypeTable(Resource.getResourceUri(r)))
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
                case H.ENUM_TYPE => {
                  val enumD = td.asInstanceOf[EnumDecl]
                  reporter.report(errorMessageGen(
                    s"Error type \'${et.value.last}\' not fount in type declaration: ${enumD.name.value}",
                    p,
                    m)
                  )
                }
              }
            } else {
              reporter.report(errorMessageGen(
                s"Error type \'${et.value.last}\' not fount in type declaration: ?",
                p,
                m)
              )
            }
          }
        }
      }
      ctp.tables.propagationTable(portUri.get) = tempSet
    }
  }


  def portMiner(m: Model, p: Port, c: ComponentDecl, r: Resource)(
    implicit reporter: AccumulatingTagReporter): Unit = {
    val ctp = stp.compMap(Resource.getResourceUri(r))
    val pr = Resource(if (p.isIn) H.PORT_IN_TYPE else H.PORT_OUT_TYPE, r, p.id.value, Some(true), p)
    if (!ctp.tables.portTable.keySet.contains(Resource.getResourceUri(pr))) {
      ctp.tables.portTable(Resource.getResourceUri(pr)) = p
    } else {
      reporter.report(errorMessageGen(
        s"Port \'${p.id.value}\' already exist in the component ${c.compName.value}",
        p,
        m)
      )
    }
  }

  def connectionMiner(m: Model)(
    implicit reporter: AccumulatingTagReporter) = {
    var parentRes = Resource(H.HEAD)
    Visitor.build({
      case conn: ConnectionDecl => {
        require(Resource.getResource(m).isDefined)
        parentRes = Resource.getResource(m).get

        val r = Resource(H.CONNECTION_TYPE, parentRes, conn.connName.value, Some(true), conn)
        val connUri = Resource.getResourceUri(r)
        if (!st.connectionTable.contains(connUri)) {
          st.connectionTable(connUri) = conn
          compPortCheck(m, conn.fromComp, conn.fromPort)
          compPortCheck(m, conn.toComp, conn.toPort)
        } else {
          reporter.report(errorMessageGen(
            s"Connection \'${conn.connName.value}\' already exist in the model",
            conn.connName,
            m)
          )
        }
        false
      }
    })(m)
    m
  }

  def compPortCheck(m: Model, comp: Name, port: Id)(
    implicit reporter: AccumulatingTagReporter) = {
    val fcompUri = findComponent(comp)
    if (fcompUri.isDefined) {
      Resource.useDefResolve(comp, stp.component(fcompUri.get))
      val fportUri = stp.compMap(fcompUri.get).ports.find(_.endsWith("#" + port.value))
      if (fportUri.isDefined) {
        Resource.useDefResolve(port, stp.componentTable(fcompUri.get).port(fportUri.get))
      } else {
        reporter.report(errorMessageGen(
          s"Port \'${port.value}\' not found in component: ${st.componentDeclTable(fcompUri.get).compName.value}",
          port,
          m)
        )
      }
    } else {
      reporter.report(errorMessageGen(
        s"Component \'${comp.value.map(_.value).mkString("::")}\' not found in connection",
        comp,
        m)
      )
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
2. behavior and transistion error type, state and event resolving
 */




