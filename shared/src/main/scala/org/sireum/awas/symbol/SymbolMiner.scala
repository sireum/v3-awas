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
  val st: SymbolTableData = stp.tables
  val H = SymbolTableHelper

  def miner(m: Model)(implicit reporter: AccumulatingTagReporter):
  Unit = {
    if (m.system.isDefined) {
      componentElemMiner(modelMiner(m), m.system.get, None)
    }
  }

  def modelMiner(m: Model)(
    implicit reporter: AccumulatingTagReporter): Model = {
    var parentRes = Resource(H.HEAD)
    //    Visitor.build({
    //      case md: Model =>
    if (m.fileUriOpt.isDefined) {
      val r = Resource(H.MODEL_TYPE, parentRes, m.fileUriOpt.get, None, m)
      st.declaredSymbols(m.fileUriOpt.get) = msetEmpty + r.toUri
      stp.modelMap(r.toUri) = m
      parentRes = r
    } else {
      val r = Resource(H.MODEL_TYPE, parentRes, "", None, m)
      stp.modelMap(r.toUri) = m
      parentRes = r
    }

    typeDeclMiner(m)
    stateMachineMiner(m)

    if (m.system.isDefined) {
      m.system.get
    }
    //    })(m)
    m
  }

  /**
    * Type miner : Currently mines only Enum type and alias
    *
    * @param m        model
    * @param reporter to collect errors
    * @return model and side effect : mine enum type error table
    */
  def typeDeclMiner(m: Model)(
    implicit reporter: AccumulatingTagReporter): Model = {
    var parentRes = Resource(H.HEAD)
    Visitor.build({
      case m: Model =>
        require(Resource.getResource(m).isDefined)
        parentRes = Resource.getResource(m).get
        true

      case ed: EnumDecl =>
        val r = Resource(H.ENUM_TYPE, parentRes, ed.name.value, Some(true), ed)
        st.symbol2Uri(ed.name.value) = r.toUri
        st.typeDeclTable(r.toUri) = ed
        val tType = new stp.TypeT(r.toUri)
        st.typeTable(r.toUri) = tType
        val tt = tType.tables
        val tempSet = mmapEmpty[ResourceUri, Id]
        ed.elements.foreach { elem =>
          val er = Resource(H.ERROR_TYPE, r, elem.value, Some(true), elem)
          tt.symbol2Uri(elem.value) = er.toUri
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

      case ad: AliasDecl => {
        def getErrorTypeUri(symbol: Name): Option[ResourceUri] = {
          val typeUri = stp.getUriFromSymbol(symbol.value.head.value)
          if (typeUri.isDefined && typeUri.get.startsWith(H.ENUM_TYPE)) {
            stp.typeTable(typeUri.get).getUriFromSymbol(
              symbol.value.last.value)
          } else {
            None
          }
        }

        val typeUri = getErrorTypeUri(ad.typeName)
        val aliasUri = getErrorTypeUri(ad.aliasName match {
          case ntd: NamedTypeDecl => ntd.value
          case _ => Name(Node.emptySeq)
        })

        if (typeUri.isDefined && aliasUri.isDefined) {
          st.aliasTable.getOrElseUpdate(typeUri.get, msetEmpty[ResourceUri]).add(aliasUri.get)
          st.aliasTable.getOrElseUpdate(aliasUri.get, msetEmpty[ResourceUri]).add(typeUri.get)
        } else {
          reporter.report(errorMessageGen(
            MISSING_ERROR_TYPE,
            ad,
            m, PrettyPrinter.print(ad.typeName) + " or " + PrettyPrinter.print(ad.aliasName)))
        }
        false
      }

      //stp.tables.aliasTable

      case ld: LatticeDecl => {
        val r = Resource(H.LATTICE_TYPE, parentRes, ld.name.value, Some(true), ld)
        st.symbol2Uri(ld.name.value) = r.toUri
        st.typeDeclTable(r.toUri) = ld
        val tType = new stp.TypeT(r.toUri)
        st.typeTable(r.toUri) = tType
        val tt = tType.tables
        val lr = Resource(H.LATTICE_TYPE, r, ld.name.value, Some(true), ld.name)
        tt.symbol2Uri(ld.name.value) = lr.toUri
        val sid = ld.superLattice.flatMap { it =>
          val x = stp.typeTable(stp.getUriFromSymbol(it.value.last.value).get)
          x.latticeElements.find(e => H.uri2IdString(e) == it.value.last.value)
        }
        tt.latticeTable(lr.toUri) = LatticeTableData(ld.name, msetEmpty ++ sid)
        // st.typeTable =
        false
      }

      case ed: RecordDecl => false
    })(m)
    m
  }

  /**
    * State machine miner
    *
    * @param m        model
    * @param reporter for collecting symbolc errors
    * @return the model itself, as side effect build the table
    */
  def stateMachineMiner(m: Model)
                       (implicit reporter: AccumulatingTagReporter): Model = {
    Visitor.build({
      case sm: StateMachineDecl => {
        var parentRes = Resource(H.HEAD)
        require(Resource.getResource(m).isDefined)
        parentRes = Resource.getResource(m).get

        val r = Resource(H.STATE_MACHINE_TYPE, parentRes, sm.smName.value, Some(true), sm)
        st.symbol2Uri(sm.smName.value) = r.toUri
        st.stateMachineDeclTable(r.toUri) = sm
        val smt = stp.stateMachineTableProducer(r.toUri)
        parentRes = r

        sm.states.foreach {
          s => {
            val sr = Resource(H.STATE_TYPE, parentRes, s.value.last.value, Some(true), s)
            smt.tables.symbol2Uri(s.value.last.value) = sr.toUri
            if (!smt.tables.statesTable.keySet.contains(sr.toUri))
              smt.tables.statesTable(sr.toUri) = s
            else {
              reporter.report(errorMessageGen(
                DUPLICATE_STATE,
                s,
                m, s.value.last.value))
            }
          }
        }
        sm.events.foreach {
          e => {
            val se = Resource(H.EVENT_TYPE, parentRes, e.value.last.value, Some(true), e)
            smt.tables.symbol2Uri(e.value.last.value) = se.toUri
            if (!smt.tables.eventsTable.keySet.contains(se.toUri))
              smt.tables.eventsTable(se.toUri) = e
            else {
              reporter.report(errorMessageGen(DUPLICATE_EVENT,
                e,
                m, e.value.last.value)
              )
            }
          }
        }
      }
        false

    })(m)
    m
  }

  /**
    * Mines the elements of the component/system
    *
    * @param m        : Model
    * @param reporter : for collecting errors and warnings
    * @return : Model
    *         : side-effect component symbol table
    */

  def componentElemMiner(m: Model,
                         comp: ComponentDecl,
                         parentRes: Option[Resource])(
                          implicit reporter: AccumulatingTagReporter):
  ComponentTable = {

    var isSystem = false
    val pRes = if (parentRes.isEmpty) {
      isSystem = true
      Resource(H.HEAD)
    } else {
      parentRes.get
    }

    val r = Resource(H.COMPONENT_TYPE, pRes, comp.compName.value, Some(true), comp)

    if (!st.componentDeclTable.keySet.contains(r.toUri)) {
      st.componentDeclTable(r.toUri) = comp
      val ctp = if (isSystem) {
        st.symbol2Uri(comp.compName.value) = r.toUri
        stp.systemD = Some(comp)
        stp.systemUri = Some(r.toUri)
        new CompSTProducer(r.toUri, comp, stp)
      } else {
        new CompSTProducer(r.toUri, comp, stp, Some(pRes.toUri))
      }

      comp.withSM.foreach {
        withs => {
          //TODO: Will change in the presence of multiple files/models
          val withName = withs.value.last.value
          val smUri = st.stateMachineDeclTable.find(_._1.endsWith(withName))
          val tyUri = st.typeDeclTable.find(_._1.endsWith(withName))
          if (smUri.isDefined) {
            st.compSMTable(r.toUri) = smUri.get._1
            ctp.tables.stateMachine.add(smUri.get._1)
          } else if (tyUri.isDefined) {
            st.compTypeTable.getOrElseUpdate(r.toUri,
              msetEmpty[ResourceUri]).add(tyUri.get._1)
            ctp.tables.types.add(tyUri.get._1)
          } else {
            reporter.report(errorMessageGen(MiSSING_TYPE_OR_STATE_MACHINE,
              withs,
              m, withName))
          }
        }
      }

      val tt = stp.compTypeDecl(r.toUri).map(stp.typeTable)

      comp.ports.foreach(portMiner(m, _, r, ctp))

      comp.propagations.foreach(propagationMiner(m, _, r, tt, ctp))

      comp.security.foreach(securityMiner(m, _, r, tt, ctp))

      comp.flows.foreach(flowMiner(m, _, r, tt, ctp))

      comp.declass.foreach(declassMiner(m, _, r, tt, ctp))

      if (comp.behaviour.isDefined) {
        //TODO : resolve Fault, state and events from behavior and transistion,
        // so that nodes points to the right uri
        val ttUri = stp.compTypeDecl(r.toUri)
        val smUri = stp.compStateMachine(r.toUri)
        if (ttUri.nonEmpty) {
          val typeTable = ttUri.map(stp.typeTable)
          comp.behaviour.get.exprs.foreach { be =>
            //          println("failing xx:"+PrettyPrinter.print(be))
            exprMiner(m, be, r, typeTable, smUri, ctp)
          }
        }
      }

      if (comp.transitions.isDefined) {
        val ttUri = stp.compTypeDecl(r.toUri)
        val smUri = stp.compStateMachine(r.toUri)
        if (ttUri.nonEmpty) {
          val typeTable = ttUri.map(stp.typeTable)
          comp.transitions.get.exprs.foreach { it =>
            transMiner(m, it, r, typeTable, smUri, ctp)
          }
        }
      }

      comp.subComp.foreach { sc =>
        val sct = componentElemMiner(m, sc, Some(r))
        stp.tables.componentSymbolTable(sct.componentUri) = sct
        ctp.tables.subComponentsTable(sct.componentUri) = sct
        ctp.tables.subComponentsDecl(sct.componentUri) = sct.componentDecl
        ctp.tables.symbol2Uri(sct.componentDecl.compName.value) = sct.componentUri
      }

      comp.connections.foreach { conn =>
        val nt = connectionMiner(m, conn, r, ctp)
        val x = nt
        if (nt.isDefined) {
          ctp.tables.connectionSymbolTabel(nt.get.connectionUri) = nt.get
          ctp.tables.connectionTable(nt.get.connectionUri) = conn
          ctp.tables.symbol2Uri(nt.get.connection.connName.value) = nt.get.connectionUri
        }
      }
      if (isSystem) {
        stp.tables.componentSymbolTable(ctp.componentUri) = ctp
      }

      comp.deployment.foreach { depl =>
        val dep = deploymentMiner(m, depl, ctp)
        if (dep.isDefined) {
          ctp.tables.deploymentDeclTable(dep.get) = depl
        }
      }
      ctp
    } else {
      reporter.report(errorMessageGen(DUPLICATE_COMPONENT,
        comp,
        m, comp.compName.value))
      st.componentSymbolTable(r.toUri)
    }
  }

  def flowMiner(m: Model, flow: Flow, r: Resource, tt: ISet[TypeTable], ctp: CompSTProducer)
               (implicit reporter: AccumulatingTagReporter): Unit = {
    val fr = Resource(H.FLOW_TYPE, r, flow.id.value, some(true), flow)

    ctp.tables.symbol2Uri(flow.id.value) = fr.toUri
    if (!ctp.tables.flowTable.contains(fr.toUri)) {
      val from = flowCheck(m, flow, fr.toUri, r, tt, isFrom = true, ctp)
      val to = flowCheck(m, flow, fr.toUri, r, tt, isFrom = false, ctp)
      ctp.tables.flowTable(fr.toUri) = FlowTableData(fr.toUri, from._1, to._1, from._2, to._2)
    } else {
      reporter.report(errorMessageGen(DUPLICATE_FLOW_NAME,
        flow.id,
        m, flow.id.value))
    }
  }

  def declassMiner(m: Model, declass: Declass, r: Resource, tt: ISet[TypeTable], ctp: CompSTProducer)
                  (implicit reporter: AccumulatingTagReporter): Unit = {
    val flowUri = ctp.tables.symbol2Uri.get(declass.fid.value)
    if (flowUri.isDefined) {
      val fromD = declass.fromDomain.flatMap(it => st.symbol2Uri.get(it.value))
      val toD = st.symbol2Uri(declass.toDomain.value)
      ctp.tables.declass(flowUri.get) = (fromD, toD)
    } else {
      reporter.report(errorMessageGen(DUPLICATE_FLOW_NAME,
        declass.fid,
        m, ""))
    }
  }


  def flowCheck(m: Model,
                flow: Flow,
                fr: ResourceUri,
                r: Resource,
                tt: ISet[TypeTable],
                isFrom: Boolean,
                ctp: CompSTProducer)(
                 implicit reporter: AccumulatingTagReporter):
  (Option[ResourceUri], ISet[ResourceUri]) = {

    val port = if (isFrom) flow.from else flow.to
    val errors = if (isFrom) flow.fromE else flow.toE
    var res: (Option[ResourceUri], ISet[ResourceUri]) = (None, isetEmpty[ResourceUri])
    if (port.isDefined) {
      val fromP = ctp.ports.find(p => p.split(H.ID_SEPARATOR).last == port.get.value)
      if (fromP.isDefined) {
        Resource.useDefResolve(port.get, ctp.port(fromP.get).get)
        ctp.tables.flowPortRelation.getOrElseUpdate(fromP.get, msetEmpty[ResourceUri]) += fr
        ctp.tables.portFlowRelation.getOrElseUpdate(fr, msetEmpty[ResourceUri]) += fromP.get
        if (errors.nonEmpty) {
          errors.foreach {
            f => mineFault(m, f, r, tt)
          }
          res = (fromP, errors.flatMap(e => Resource.getResource(e)).map(_.toUri).toSet)
        } else {
          res = (fromP, isetEmpty[ResourceUri])
        }
      } else {
        reporter.report(errorMessageGen(MISSING_PORT_DECL,
          flow, m, flow.id.value.toString, r.uriPaths.tail.tail.mkString(".").toString)
        )
        res = (None, isetEmpty[ResourceUri])
      }
    }
    res
  }


  def transMiner(m: Model,
                 expr: TransExpr,
                 r: Resource,
                 tt: ISet[TypeTable],
                 smUri: Option[ResourceUri],
                 ctp: CompSTProducer)(
                  implicit reporter: AccumulatingTagReporter): Unit = {
    val tr = Resource(H.TRANSITION_TYPE, r, expr.id.value, Some(true), expr)

    ctp.tables.symbol2Uri(expr.id.value) = tr.toUri
    if (!ctp.tables.behaviorTable.contains(tr.toUri)) {
      ctp.tables.transitionTable(tr.toUri) = expr
      stateRefMiner(m, expr.lhs, r, smUri.get)
      stateRefMiner(m, expr.rhs, r, smUri.get)
      if (expr.propCond.isDefined && smUri.isDefined) {
        condMiner(m, expr.propCond.get, r, tt, smUri, ctp)
      }

    } else {
      reporter.report(errorMessageGen(DUPLICATE_FLOW_NAME,
        expr.id,
        m, expr.id.value))
    }
  }


  def exprMiner(m: Model,
                expr: BehaveExpr,
                r: Resource,
                tt: ISet[TypeTable],
                smUri: Option[ResourceUri],
                ctp : CompSTProducer)(
    implicit reporter: AccumulatingTagReporter): Unit = {

    val br = Resource(H.BEHAVIOR_TYPE, r, expr.id.value, Some(true), expr)

    ctp.tables.symbol2Uri(expr.id.value) = br.toUri
    if (!ctp.tables.behaviorTable.contains(br.toUri)) {

      ctp.tables.behaviorTable(br.toUri) = expr

      if (expr.lhs.isDefined) {
        condMiner(m, expr.lhs.get, r, tt, smUri, ctp)
      }

      if (expr.rhs.isDefined) {
        tupleMiner(m, expr.rhs.get, r, tt, ctp)
      }

      if (smUri.isDefined) {
        val smt = stp.smTable(smUri.get)
        expr.states.foreach { s =>
          val sUri = smt.states.find(_.endsWith(s.value))
          if (sUri.isDefined) {
            Resource.useDefResolve(s, smt.state(sUri.get))
          } else {
            reporter.report(errorMessageGen(MISSING_STATE_DECL, s, m, s.value))
          }
        }
      }



    } else {
      reporter.report(errorMessageGen(DUPLICATE_FLOW_NAME,
        expr.id,
        m, expr.id.value))
    }
  }

  def condMiner(m: Model,
                conditionTuple: ConditionTuple,
                r: Resource,
                tt: ISet[TypeTable],
                smtUri: Option[ResourceUri],
                ctp: CompSTProducer)(
                 implicit reporter: AccumulatingTagReporter): Unit = {
    conditionTuple match {
      case and: And => {
        condMiner(m, and.lhs, r, tt, smtUri, ctp)
        condMiner(m, and.rhs, r, tt, smtUri, ctp)
      }
      case or: Or => {
        condMiner(m, or.lhs, r, tt, smtUri, ctp)
        condMiner(m, or.rhs, r, tt, smtUri, ctp)
      }

      case ormore: OrMore => {
        ormore.conds.foreach(it => condMiner(m, it, r, tt, smtUri, ctp))
      }
      case orLess: OrLess => {
        orLess.conds.foreach(it => condMiner(m, it, r, tt, smtUri, ctp))
      }
      case all: All => {
        all.conds.foreach(it => condMiner(m, it, r, tt, smtUri, ctp))
      }
      case p: PrimaryCondition => {
        p match {
          case ef: EventRef => eventRefMiner(m, ef, r, smtUri)
          case t: Tuple => tupleMiner(m, t, r, tt, ctp)
        }
      }
    }
  }

  def eventRefMiner(m: Model, ef: EventRef, r: Resource, smtUri: Option[ResourceUri])(
    implicit reporter: AccumulatingTagReporter): Unit = {
    val smt = st.stateMachineTable.get(smtUri.get)
    if (smt.nonEmpty) {
      val eventUri = smt.get.events.find(it => H.uri2IdString(it) == ef.event.last.value)
      if (eventUri.isDefined) {
        Resource.useDefResolve(ef, smt.get.event(eventUri.get))
      } else {
        reporter.report(errorMessageGen(MISSING_EVENT_DECL, ef, m, ef.event.last.value, r.toUri))
      }
    } else {

    }
  }

  def stateRefMiner(m: Model, state: Node.Seq[Id], r: Resource, smUri: ResourceUri)(
    implicit reporter: AccumulatingTagReporter): Unit = {
    val smt = st.stateMachineTable.get(smUri)
    if (smt.nonEmpty) {
      val stateUri = smt.get.states.find(it => H.uri2IdString(it) == state.last.value)
      if (stateUri.isDefined) {
        Resource.useDefResolve(state.last, smt.get.state(stateUri.get))
      } else {
        reporter.report(errorMessageGen(MISSING_STATE_DECL, state.last, m, state.last.value, r.toUri))
      }
    } else {

    }
  }

  def tupleMiner(m: Model,
                 tup: Tuple,
                 r: Resource,
                 tt: ISet[TypeTable],
                 ctp: CompSTProducer)(
    implicit reporter: AccumulatingTagReporter): Unit = {
    tup.tokens.foreach {
      t =>
        val port = ctp.ports.find(_.endsWith(t._1.value))
        if (port.isDefined) {
          Resource.useDefResolve(t._1, ctp.port(port.get).get)
        } else {
          println("failed :" + t._1.value)
          reporter.report(errorMessageGen(MISSING_PORT_DECL,
            t._1,
            m, t._1.value, r.toUri))
        }
        t._2 match {
          case f: Fault => mineFault(m, f, r, tt)
          case fs: FaultSet => fs.value.foreach(mineFault(m, _, r, tt))
        }
    }
  }

  def mineFault(m: Model, f: Fault, r: Resource, tt: ISet[TypeTable])(
    implicit reporter: AccumulatingTagReporter): Unit = {
    val fif = f.enum.value.map(_.value)
    if (fif.size > 1) {
      val updated = fif.takeRight(2)
      val updatedtt = tt.find(t => H.uri2IdString(t.uri) == updated.head)
      if (updatedtt.isDefined) {
        val eUri = updatedtt.get.getUriFromSymbol(updated.last)
        if (eUri.isDefined) {
          Resource.useDefResolve(f, updatedtt.get.enumElement(eUri.get))
        } else {
          reporter.report(errorMessageGen(MISSING_TYPE_DECL,
            f,
            m, updated.last))
        }
      } else {
        reporter.report(errorMessageGen(MiSSING_TYPE_OR_STATE_MACHINE,
          f,
          m, updated.head))
      }
    } else {
      val elem = tt.flatMap(x => x.getUriFromSymbol(fif.last))
      //    val elem = tt.flatMap(x => x.enumElements.find(_.endsWith(fif)))
      if (elem.nonEmpty) {
        if (elem.size > 1) {
          reporter.report(errorMessageGen(TYPE_RESOLUTION_FAILURE,
            f,
            m, fif.last))
        } else {
          if (Resource.getParentUri(elem.head).isDefined) {
            Resource.useDefResolve(f, st.typeTable(Resource.getParentUri(elem.head).get).enumElement(elem.head))
          } else {
            //should never hit
            reporter.report(errorMessageGen(MISSING_TYPE_DECL,
              f,
              m, fif.last))
          }
        }
      } else {
        reporter.report(errorMessageGen(MISSING_TYPE_DECL,
          f,
          m, fif.last))
      }
    }
  }

  def propagationMiner(m: Model,
                       p: Propagation,
                       r: Resource,
                       tt: ISet[TypeTable],
                       ctp: CompSTProducer)(
                        implicit reporter: AccumulatingTagReporter): Unit = {

    val portUri = ctp.getUriFromSymbol(p.id.value)

    if (portUri.isEmpty) {
      reporter.report(errorMessageGen(MISSING_PORT_DECL,
        p,
        m, p.id.value, r.toUri))

    } else if (st.compTypeTable.get(r.toUri).isEmpty) {
      reporter.report(errorMessageGen(MISSING_TYPE_ASSOCIATION,
        p,
        m, ""))
    } else {
      val tempSet = msetEmpty[ResourceUri]
      Resource.useDefResolve(p, ctp.tables.portTable(portUri.get))
      p.errorTypes.foreach {
        et => {
          mineFault(m, et, r, tt)
          if (Resource.getResource(et).isDefined) {
            tempSet += Resource.getResource(et).get.toUri
          }
        }
          ctp.tables.propagationTable(portUri.get) = tempSet
      }
    }
  }

  def securityMiner(m: Model,
                    p: Security,
                    r: Resource,
                    tt: ISet[TypeTable],
                    ctp: CompSTProducer)(
                     implicit reporter: AccumulatingTagReporter): Unit = {

    val portUri = ctp.getUriFromSymbol(p.id.value)

    if (portUri.isEmpty) {
      reporter.report(errorMessageGen(MISSING_PORT_DECL,
        p,
        m, p.id.value, r.toUri))

    } else {
      val tempSet = msetEmpty[ResourceUri]
      Resource.useDefResolve(p, ctp.tables.portTable(portUri.get))
      val updatedTT = stp.typeDecls.map(stp.typeTable).find(_.getUriFromSymbol(p.domain.value).isDefined)
      if (updatedTT.isDefined) {
        val duri = updatedTT.get.getUriFromSymbol(p.domain.value).get
        Resource.useDefResolve(p.domain, updatedTT.get.latticeElement(duri))
      } else {
        reporter.report(errorMessageGen(MISSING_TYPE_DECL,
          p.domain,
          m, p.domain.value))
      }
      if (Resource.getResource(p.domain).isDefined) {
        ctp.tables.securityTable(portUri.get) = Resource.getResource(p.domain).get.toUri
      }


      //        .foreach {
      //        et => {
      //          mineFault(m, et, r, tt)
      //          if (Resource.getResource(et).isDefined) {
      //            tempSet += Resource.getResource(et).get.toUri
      //          }
      //        }
      //          ctp.tables.propagationTable(portUri.get) = tempSet

    }
  }

  def portMiner(m: Model, p: Port, r: Resource, ctp: CompSTProducer)(
    implicit reporter: AccumulatingTagReporter): Unit = {
    val pr = Resource(if (p.isIn) H.PORT_IN_TYPE else H.PORT_OUT_TYPE, r, p.id.value, Some(true), p)
    ctp.tables.symbol2Uri(p.id.value) = pr.toUri
    if (!ctp.tables.portTable.keySet.contains(pr.toUri)) {
      ctp.tables.portTable(pr.toUri) = p
    } else {
      reporter.report(errorMessageGen(DUPLICATE_PORT,
        p,
        m, p.id.value, r.toUri))
    }
  }

  //TODO: Rewrite this method
  def connectionMiner(m: Model,
                      connDecl: ConnectionDecl,
                      parentRes: Resource,
                      parentCT: ComponentTable)(
                       implicit reporter: AccumulatingTagReporter):
  Option[ConnSTProducer] = {
    val r = Resource(H.CONNECTION_TYPE, parentRes, connDecl.connName.value, Some(true), connDecl)

    st.symbol2Uri(connDecl.connName.value) = r.toUri


    if (!parentCT.connections.toSet.contains(r.toUri)) {

      //            st.connectionTable(r.toUri) = conn

      if (compPortCheck(m, connDecl.fromComp, connDecl.fromPort, parentCT) &&
        compPortCheck(m, connDecl.toComp, connDecl.toPort, parentCT)) {

        val ntp = new ConnSTProducer(r.toUri, connDecl, stp, parentRes.toUri)

        val inr = Resource(H.PORT_IN_VIRTUAL_TYPE,
          r,
          H.INPUT_CONN_PORT_ID,
          Some(true))

        val outr = Resource(H.PORT_OUT_VIRTUAL_TYPE,
          r,
          H.OUTPUT_CONN_PORT_ID,
          Some(true))

        ntp.tables.symbol2Uri(H.INPUT_CONN_PORT_ID) = inr.toUri
        ntp.tables.symbol2Uri(H.OUTPUT_CONN_PORT_ID) = outr.toUri
        ntp.tables.portTable += inr.toUri
        ntp.tables.portTable += outr.toUri

        //create virtual propagations
        val fcompUri = if (connDecl.fromComp.isDefined) {
          parentCT.getUriFromSymbol(connDecl.fromComp.get.value.last.value)
        } else {
          Some(parentRes.toUri)
        }
        val compSt = if (fcompUri.get == parentCT.componentUri) parentCT
        else st.componentSymbolTable(fcompUri.get)
        val fportUri = compSt.getUriFromSymbol(connDecl.fromPort.value)

        val errors = compSt.propagation(fportUri.get)
        ntp.tables.propagationTable(inr.toUri) = collection.mutable.Set(errors.toList: _*)
        ntp.tables.propagationTable(outr.toUri) = collection.mutable.Set(errors.toList: _*)

        //Handle flows

        connDecl.connFlow.foreach { flow =>
          val fr = Resource(H.FLOW_TYPE, r, flow.id.value, some(true), flow)
          ntp.tables.symbol2Uri(flow.id.value) = fr.toUri
          if (!ntp.tables.flowTable.contains(fr.toUri)) {
            flow.fromE.foreach(mineFault(m, _, r, st.typeTable.values.toSet))
            flow.toE.foreach(mineFault(m, _, r, st.typeTable.values.toSet))

            ntp.tables.flowTable(fr.toUri) = FlowTableData(fr.toUri,
              if (flow.fromE.nonEmpty) Some(inr.toUri) else None,
              if (flow.toE.nonEmpty) Some(outr.toUri) else None,
              flow.fromE.flatMap(fe => Resource.getResource(fe)).map(_.toUri).toSet,
              flow.toE.flatMap(fe => Resource.getResource(fe)).map(_.toUri).toSet)

            if (flow.fromE.nonEmpty) {
              ntp.tables.flowPortRelation.getOrElseUpdate(
                ntp.tables.portTable.filter(_.startsWith(H.PORT_IN_VIRTUAL_TYPE)).head,
                msetEmpty[ResourceUri]
              ) += fr.toUri
              ntp.tables.portFlowRelation.getOrElseUpdate(
                fr.toUri, msetEmpty[ResourceUri]
              ) += ntp.tables.portTable.filter(_.startsWith(H.PORT_IN_VIRTUAL_TYPE)).head

              flow.fromE.foreach(f => mineFault(m, f, r, st.typeTable.values.toSet))
            }
            if (flow.toE.nonEmpty) {
              ntp.tables.flowPortRelation.getOrElseUpdate(
                ntp.tables.portTable.filter(_.startsWith(H.PORT_OUT_VIRTUAL_TYPE)).head,
                msetEmpty[ResourceUri]
              ) += fr.toUri
              ntp.tables.portFlowRelation.getOrElseUpdate(
                fr.toUri, msetEmpty[ResourceUri]
              ) += ntp.tables.portTable.filter(_.startsWith(H.PORT_OUT_VIRTUAL_TYPE)).head
              flow.toE.foreach(f => mineFault(m, f, r, st.typeTable.values.toSet))
            }
          } else {
            reporter.report(errorMessageGen(DUPLICATE_FLOW_NAME,
              flow.id,
              m, flow.id.value))
          }
        }
        var i = 1
        //default flow
        errors.foreach { e =>

          buildAndAddDefaultConnectionFlows(r, Some(e), i.toString, inr, outr, ntp)
          i = i + 1
        }
        Some(ntp)
      } else {
        None
      }

    } else {
      reporter.report(errorMessageGen(DUPLICATE_CONNECTION,
        connDecl,
        m, connDecl.connName.value))
      None
    }
  }

  def buildAndAddDefaultConnectionFlows(
    r: Resource,
    error: Option[ResourceUri],
    idAppender: String,
    inr: Resource,
    outr: Resource,
    ntp: ConnSTProducer
  ) = {

    val dfr = Resource(H.FLOW_TYPE, r, H.VIRTUAL_CONN_FLOW_ID + idAppender, some(true))
    ntp.tables.symbol2Uri(H.VIRTUAL_CONN_FLOW_ID) = dfr.toUri

    ntp.tables.flowTable(dfr.toUri) = FlowTableData(
      dfr.toUri,
      Some(inr.toUri),
      Some(outr.toUri),
      if (error.isDefined) isetEmpty + error.get else isetEmpty,
      if (error.isDefined) isetEmpty + error.get else isetEmpty
    )

    if (ntp.tables.portTable.contains(inr.toUri)) {
      ntp.tables.flowPortRelation.getOrElseUpdate(inr.toUri, msetEmpty[ResourceUri]) += dfr.toUri

      ntp.tables.portFlowRelation.getOrElseUpdate(dfr.toUri, msetEmpty[ResourceUri]) += inr.toUri
    }
    if (ntp.tables.portTable.contains(outr.toUri)) {
      ntp.tables.flowPortRelation.getOrElseUpdate(outr.toUri, msetEmpty[ResourceUri]) += dfr.toUri

      ntp.tables.portFlowRelation.getOrElseUpdate(dfr.toUri, msetEmpty[ResourceUri]) += outr.toUri
    }
  }

  def deploymentMiner(m: Model,
                      deploy: DeploymentDecl,
                      cstp: CompSTProducer)(
                       implicit reporter: AccumulatingTagReporter): Option[(ResourceUri, ResourceUri)] = {
    // all comp/conn references in deployment must be part of the subcomp / conn
    // defined in the parent comp of deploy

    val fromUri = cstp.getUriFromSymbol(deploy.fromNode.value.last.value)
    val toUri = cstp.getUriFromSymbol(deploy.toNode.value.last.value)

    val fromPortUri = if (fromUri.isDefined && deploy.fromPort.isDefined) {
      stp.tables.componentSymbolTable(fromUri.get).getUriFromSymbol(deploy.fromPort.get.value)
    } else {
      None
    }

    val toPortUri = if (toUri.isDefined && deploy.toPort.isDefined) {
      stp.tables.componentSymbolTable(toUri.get).getUriFromSymbol(deploy.toPort.get.value)
    } else {
      None
    }

    if (fromPortUri.isDefined) {
      Resource.useDefResolve(deploy.fromNode, cstp.subComponent(fromUri.get))
      Resource.useDefResolve(deploy.fromPort.get, cstp.tables.subComponentsTable(fromUri.get).port(fromPortUri.get).get)
    } else if (fromUri.isDefined) {
      Resource.useDefResolve(deploy.fromNode,
        if (fromUri.get.startsWith(H.COMPONENT_TYPE)) cstp.subComponent(fromUri.get)
        else cstp.connection(fromUri.get))
    } else {
      reporter.report(errorMessageGen(MISSING_COMPONENT_OR_CONNECTION,
        deploy,
        m, deploy.fromNode.value.last.value))
    }

    if (toPortUri.isDefined) {
      Resource.useDefResolve(deploy.toNode, cstp.subComponent(toUri.get))
      Resource.useDefResolve(deploy.toPort.get, cstp.tables.subComponentsTable(toUri.get).port(toPortUri.get).get)
    } else if (toUri.isDefined) {
      Resource.useDefResolve(deploy.toNode,
        if (toUri.get.startsWith(H.COMPONENT_TYPE)) cstp.subComponent(toUri.get)
        else cstp.connection(toUri.get))
    } else {
      reporter.report(errorMessageGen(MISSING_COMPONENT_OR_CONNECTION,
        deploy,
        m, deploy.toNode.value.last.value))
    }

    val furi = if (fromPortUri.isDefined) {
      fromPortUri.get
    } else {
      //only one of the two can be connection in a deployment
      assert(toPortUri.nonEmpty, "Connection cannot be bound to a connection")
      addBindPortsToNodes(fromUri.get, cstp, m, toPortUri.get, toUri.get)
      fromUri.get
    }

    val turi = if (toPortUri.isDefined) {
      toPortUri.get
    } else {
      assert(fromPortUri.nonEmpty, "Connection cannot be bound to a connection")
      addBindPortsToNodes(toUri.get, cstp, m, fromPortUri.get, fromUri.get)
      toUri.get
    }

    Some((furi, turi))
    //    if (fromUri.isDefined && toUri.isDefined) {
    //      addBindPortsToNodes(fromUri.get, cstp)
    //      addBindPortsToNodes(toUri.get, cstp)
    //
    //    } else {
    //      None
    //    }
    //add ports
  }

  def addBindPortsToNodes(nodeUri: ResourceUri,
                          cstp: CompSTProducer,
                          m: Model,
                          otherPort: ResourceUri,
                          otherComp: ResourceUri)(
                           implicit reporter: AccumulatingTagReporter): Unit = {
    if (nodeUri.startsWith(H.COMPONENT_TYPE)) {
      reporter.report(errorMessageGen(MISSING_PORT_REF,
        st.componentSymbolTable(nodeUri).componentDecl,
        m))
      assert(false, "this should never be a component")
      //      val pt = stp.tables.componentSymbolTable(nodeUri).tables.portTable
      //      val port1 = Port(isIn = true, Id(H.INPUT_BIND_PORT_ID), None)
      //      pt(Resource(H.PORT_IN_BIND_TYPE,
      //        Resource.getDefResource(nodeUri).get,
      //        H.INPUT_BIND_PORT_ID, Some(true), port1).toUri) = port1
      //      val port2 = Port(isIn = false, Id(H.OUTPUT_BIND_PORT_ID), None)
      //      pt(Resource(H.PORT_OUT_BIND_TYPE,
      //        Resource.getDefResource(nodeUri).get,
      //        H.OUTPUT_BIND_PORT_ID, Some(true), port2).toUri) = port2
    } else {
      val tables = cstp.tables.connectionSymbolTabel(nodeUri).tables
      val pt = tables.portTable
      val bindInPort = Resource(H.PORT_IN_BIND_TYPE, Resource.getDefResource(nodeUri).get,
        H.INPUT_BIND_PORT_ID, Some(true))
      val bindOutPort = Resource(H.PORT_OUT_BIND_TYPE, Resource.getDefResource(nodeUri).get,
        H.OUTPUT_BIND_PORT_ID, Some(true))
      pt += bindInPort.toUri
      pt += bindOutPort.toUri
      var propagations = tables.propagationTable
      val connInPort = cstp.tables.connectionSymbolTabel(nodeUri).ports.filter(it =>
        H.uri2IdString(it) == H.INPUT_CONN_PORT_ID).head
      val connOutPort = cstp.tables.connectionSymbolTabel(nodeUri).ports.filter(it =>
        H.uri2IdString(it) == H.OUTPUT_CONN_PORT_ID).head
      val otherTable = cstp.tables.subComponentsTable.get(otherComp)

      //adding propagation and flows
      if (H.isInPort(otherPort)) {
        propagations += (bindOutPort.toUri -> (msetEmpty ++
          otherTable.get.propagation(otherPort)))
      } else {
        propagations += (bindInPort.toUri -> (msetEmpty ++
          otherTable.get.propagation(otherPort)))
      }

      var temp_i = 1
      if (Resource.getDefResource(nodeUri).isDefined) {
        val res = Resource.getDefResource(nodeUri).get
        if (H.isInPort(otherPort)) {
          propagations(connInPort).intersect(propagations(bindOutPort.toUri)).foreach { e =>
            buildAndAddDefaultConnectionFlows(
              res,
              Some(e),
              "_bind_out" + temp_i,
              Resource.getDefResource(connInPort).get,
              bindOutPort,
              cstp.tables.connectionSymbolTabel(nodeUri)
            )
            temp_i = temp_i + 1
          }

          buildAndAddDefaultConnectionFlows(
            res,
            None,
            "_bind_out" + temp_i,
            Resource.getDefResource(connInPort).get,
            bindOutPort,
            cstp.tables.connectionSymbolTabel(nodeUri)
          )

          temp_i = temp_i + 1

          propagations(connInPort).diff(propagations(bindOutPort.toUri)).foreach { e =>
            buildAndAddDefaultConnectionFlows(
              res,
              Some(e),
              "_bind" + temp_i,
              Resource.getDefResource(connInPort).get,
              Resource.getDefResource(connOutPort).get,
              cstp.tables.connectionSymbolTabel(nodeUri)
            )
            temp_i = temp_i + 1
          }

          buildAndAddDefaultConnectionFlows(
            res,
            None,
            "_bind" + temp_i,
            Resource.getDefResource(connInPort).get,
            Resource.getDefResource(connOutPort).get,
            cstp.tables.connectionSymbolTabel(nodeUri)
          )

          temp_i = temp_i + 1
        } else {
          propagations(bindInPort.toUri).foreach { e =>
            buildAndAddDefaultConnectionFlows(
              res,
              Some(e),
              "_bind_in" + temp_i,
              bindInPort,
              Resource.getDefResource(connOutPort).get,
              cstp.tables.connectionSymbolTabel(nodeUri)
            )
            temp_i = temp_i + 1
          }
          buildAndAddDefaultConnectionFlows(
            res,
            None,
            "_bind_in" + temp_i,
            bindInPort,
            Resource.getDefResource(connOutPort).get,
            cstp.tables.connectionSymbolTabel(nodeUri)
          )

          temp_i = temp_i + 1
        }
      }
    }
  }

  def compPortCheck(m: Model, comp: Option[Name],
                    port: Id,
                    parentCST: ComponentTable)(
                     implicit reporter: AccumulatingTagReporter): Boolean = {
    val fcompUri = if (comp.isDefined) {
      parentCST.getUriFromSymbol(comp.get.value.last.value)
    } else {
      Some(parentCST.componentUri)
    }
    if (fcompUri.isDefined) {
      val fcomp = if (fcompUri.get == parentCST.componentUri) parentCST
      else stp.componentTable(fcompUri.get)

      if (comp.isDefined) {
        Resource.useDefResolve(comp.get, fcomp.componentDecl)
      }

      val fportUri = fcomp.getUriFromSymbol(port.value)
      if (fportUri.isDefined) {
        Resource.useDefResolve(port, fcomp.port(fportUri.get).get)
        true
      } else {
        reporter.report(errorMessageGen(MISSING_PORT_DECL,
          port,
          m, fcomp.componentDecl.compName.value+"."+port.value))
        false
      }
    } else {
      //here comp is vacuously defined
      reporter.report(errorMessageGen(MISSING_COMPONENT, comp.get.value.last, m, (comp.get.value.map(_.value)).mkString("::"))
      )
      false
    }
  }



}

/*
TODO: 1. error out intersection of component name and connection name
 */