/*
Copyright (c) 2015, Robby, Kansas State University
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

package org.sireum.awas.ast

import org.sireum.util._

import scala.collection.immutable.ListMap

object PrettyPrinter {
  def apply(m: Model): String = {
    val sb = new StringBuilder
    new PrettyPrinter(sb).print(m)
    sb.toString().trim
  }

  def print(n : Node): String ={
    val sb = new StringBuilder
    Visitor.build({
      case n : Name =>
        new PrettyPrinter(sb).print(n)
        false
      case t : Tuple =>
        new PrettyPrinter(sb).print(t)
        false
      case o : One =>
        new PrettyPrinter(sb).print(o)
        false
      case p : Port =>
        new PrettyPrinter(sb).print(p,0)
        false
      case f: Flow =>
        new PrettyPrinter(sb).print(f, 0)
        false
    })(n)
    sb.toString().trim
  }

}

final class PrettyPrinter(sb: StringBuilder) {
  def print(m: Model, indent: Natural = 0): Unit = {
    implicit val _indent = indent

    if(m.types.nonEmpty) {
      sb.append("types")
      println()
      print(m.types.head, indent+1)
      for(tt <- m.types.tail) {
        println()
        print(tt, indent + 1)
      }
      println()
    }

    if(m.stateMachines.nonEmpty) {
      sb.append("behavior")
      println()
      print(m.stateMachines.head, indent+1)
      for(sm <- m.stateMachines.tail) {
        println()
        print(sm, indent + 1)
      }
      println()
    }

    if(m.constants.nonEmpty) {
      sb.append("constants")
      println()
      print(m.constants.head, indent + 1)
      for(ct <- m.constants.tail) {
        println()
        print(ct, indent +1)
      }
      println()
    }

    if (m.system.isDefined) {
      sb.append("system")
      print(m.system.get, indent + 1)
    }

    //    if(m.components.nonEmpty) {
    //      sb.append("components")
    //      println()
    //      print(m.components.head, indent + 1)
    //      for(ct <- m.components.tail) {
    //        println()
    //        print(ct, indent + 1)
    //      }
    //      println()
    //    }

    //    if(m.connections.nonEmpty) {
    //      sb.append("connections")
    //      println()
    //      print(m.connections.head, indent+1)
    //      for(ct <- m.connections.tail) {
    //        println()
    //        print(ct, indent+1)
    //      }
    //      println()
    //    }

    //    if (m.deployment.nonEmpty) {
    //      sb.append("deployment")
    //      println()
    //      print(m.deployment.head, indent + 1)
    //      for (dep <- m.deployment.tail) {
    //        println()
    //        print(dep, indent + 1)
    //      }
    //      println()
    //    }
  }

  def print(td: TypeDecl, indent: Natural) : Unit = {
    td match {
      case td:AliasDecl => print(td.asInstanceOf[AliasDecl], indent)
      case td:EnumDecl => print(td.asInstanceOf[EnumDecl], indent)
      case td:LatticeDecl => print(td.asInstanceOf[LatticeDecl], indent)
      case td:RecordDecl => print(td.asInstanceOf[RecordDecl], indent)
    }
  }

  def print(smd: StateMachineDecl, indent: Natural) : Unit ={
    printIndent(indent)
    print(smd.smName)
    sb.append(":")
    println()
    if(smd.states.nonEmpty) {
      printIndent(indent+1)
      sb.append("states = [")
      print(smd.states.head)
      for(st <- smd.states.tail) {
        sb.append(", ")
        print(st)
      }
      sb.append("]")
      println()
    }

    if(smd.events.nonEmpty) {
      printIndent(indent+1)
      sb.append("events = {")
      print(smd.events.head)
      for(ev <- smd.events.tail) {
        sb.append(", ")
        print(ev)
      }
      sb.append("}")
      println()
    }
  }

  def print(compd: ComponentDecl, indent: Natural) : Unit ={
    var localIndent = indent
    printIndent(localIndent)
    print(compd.compName)
    println()
    if (compd.withSM.nonEmpty) {
      localIndent = localIndent+1
      printIndent(localIndent)
      sb.append("with ")
      print(compd.withSM.head)
      for(wsmt <- compd.withSM.tail) {
        sb.append(", ")
        print(wsmt)
      }
      println()
    }


    if(compd.ports.nonEmpty) {
      printIndent(localIndent+1)
      sb.append("ports")
      println()
      print(compd.ports.head, localIndent+2)
      for(cp <- compd.ports.tail) {
        println()
        print(cp, localIndent+2)
      }
     println()
    }

    if(compd.propagations.nonEmpty) {
      printIndent(localIndent+1)
      sb.append("propagations")
      println()
      print(compd.propagations.head, localIndent+2)
      for(cpr <- compd.propagations.tail) {
        println()
        print(cpr, localIndent+2)
      }
      println()
    }

    if(compd.flows.nonEmpty) {
      printIndent(localIndent+1)
      sb.append("flows")
      println()
      print(compd.flows.head, localIndent+2)
      for(cf <- compd.flows.tail) {
        println()
        print(cf, localIndent+2)
      }
      println()
    }

    if(compd.transitions.isDefined) {
      printIndent(localIndent+1)
      sb.append("transitions")
      println()
      print(compd.transitions.get, localIndent+2)
      println()
    }

    if(compd.behaviour.isDefined) {
      printIndent(localIndent+1)
      sb.append("behavior")
      println()
      print(compd.behaviour.get, localIndent+2)
      println()
    }

    if (compd.subComp.nonEmpty) {
      sb.append("sub-components {")
      compd.subComp.foreach { sc =>
        println()
        print(sc, indent + 1)
      }
      sb.append("}")
      println()
    }

    if (compd.connections.nonEmpty) {
      sb.append("connections")
      println()
      print(compd.connections.head, indent + 1)
      for (ct <- compd.connections.tail) {
        println()
        print(ct, indent + 1)
      }
      println()
    }

    if (compd.deployment.nonEmpty) {
      sb.append("deployment")
      println()
      print(compd.deployment.head, indent + 1)
      for (dep <- compd.deployment.tail) {
        println()
        print(dep, indent + 1)
      }
      println()
    }

    if(compd.properties.nonEmpty) {
      printIndent(localIndent+1)
      sb.append("properties")
      println()
      print(compd.properties.head, localIndent + 2)
      for(cp <- compd.properties.tail) {
        println()
        print(cp, localIndent + 2)
      }
      println()
    }
  }

  def print(cd : ConnectionDecl, indent: Natural) : Unit = {
    printIndent(indent)
    print(cd.connName)
    sb.append(" : ")
    if (cd.fromComp.isDefined) {
      print(cd.fromComp.get)
      sb.append(".")
    }
    print(cd.fromPort)
    if (cd.isAccess) {
      sb.append(" <-> ")
    } else {
      sb.append(" -> ")
    }
    if (cd.toComp.isDefined) {
      print(cd.toComp.get)
      sb.append(".")
    }
    print(cd.toPort)
    println()

    if (cd.connFlow.nonEmpty) {
      printIndent(indent + 1)
      sb.append("flows")
      println()
      print(cd.connFlow.head, indent + 2)
      for (cp <- cd.connFlow.tail) {
        println()
        print(cp, indent + 2)
      }
      println()
    }
    if(cd.behaviour.isDefined) {
      printIndent(indent+1)
      sb.append("behavior")
      println()
      print(cd.behaviour.get, indent+2)
      println()
    }
    if(cd.properties.nonEmpty) {
      printIndent(indent+1)
      sb.append("properties")
      println()
      print(cd.properties.head, indent + 2)
      for(cp <- cd.properties.tail) {
        println()
        print(cp, indent + 2)
      }
      println()
    }
  }

  def print(dep: DeploymentDecl, indent: Natural): Unit = {
    printIndent(indent)
    print(dep.fromNode)
    if (dep.fromPort.isDefined) {
      sb.append(".")
      print(dep.fromPort.get)
    }
    sb.append(" -> ")
    print(dep.toNode)
    if (dep.toPort.isDefined) {
      sb.append(".")
      print(dep.toPort.get)
    }
    println()
  }

  def print(t: Transition, indent: Natural) : Unit = {
    printIndent(indent)
    print(t.exprs.head)
    for(tet <- t.exprs.tail) {
      println()
      printIndent(indent)
      print(tet)
    }
  }

  def print(te: TransExpr) : Unit = {
    printIdGroup(te.lhs)
    sb.append(" -[")
    te.propCond match {
      case Some(e) => print(e)
      case None => printIdGroup(te.trigger)
    }
    sb.append("]-> ")
    printIdGroup(te.rhs)
  }

  def printIdGroup(ids : Node.Seq[Id]): Unit = {
    if(ids.length == 1) {
       print(ids.head)
    } else {
      sb.append("(")
      print(ids.head)
      for(idst <- ids.tail) {
        sb.append(", ")
        print(idst)
      }
      sb.append(")")
    }
  }

  def print(b : Behaviour, indent: Natural) : Unit = {
    printIndent(indent)
    print(b.exprs.head)
    for(bet <- b.exprs.tail) {
      println()
      printIndent(indent)
      print(bet)
    }
  }

  def print(expr: Expression) : Unit = {
    print(expr.lhs)
    if(expr.states.nonEmpty) {
      sb.append(" -[")
      printIdGroup(expr.states)
      sb.append("]-> ")
    } else {
      sb.append(" -> ")
    }
    print(expr.rhs)
  }

  def print(ot : Option[Tuple]) : Unit = {
    ot match {
      case Some(t) => print(t)
      case None => sb.append("*")
    }
  }

  def print(t : Tuple) : Unit = {
    if (t.tokens.size > 1) {
      sb.append("(")
      print(t.tokens.head._1)
      sb.append(":")
      print(t.tokens.head._2)
      for(tt <- t.tokens.tail) {
        sb.append(", ")
        print(tt._1)
        sb.append(":")
        print(tt._2)
      }
      sb.append(")")
    } else {
      print(t.tokens.head._1)
      sb.append(":")
      print(t.tokens.head._2)
    }
  }

  def print(o : One) : Unit = {
    o match {
//      case o : NoFailure => sb.append("*")
//      case o : Wildcard => sb.append("_")
//      case o : Variable => print(o.id)
      case o : Fault =>
        print(o)
      case o : FaultSet =>
        sb.append("{")
        print(o.value.head)
        for(vt <- o.value.tail) {
          sb.append(", ")
          print(vt)
        }
        sb.append("}")
    }
  }

  def print(f : Fault) : Unit = {
    print(f.enum)
  }

  def print(ad : AliasDecl, indent: Natural) : Unit ={
    printIndent(indent)
    sb.append("alias ")
    print(ad.typeName)
    sb.append(" = ")
    print(ad.aliasName)
    println()
  }

  def print(ed: EnumDecl, indent: Natural):Unit = {
    printIndent(indent)
    sb.append("enum ")
    print(ed.name)
    if(ed.superEnums.nonEmpty) {
      sb.append(" extends ")
      print(ed.superEnums.head)
      for(edt<-ed.superEnums.tail) {
        sb.append(", ")
        print(edt)
      }
    }
    if(ed.elements.nonEmpty) {
      sb.append(" {")
      print(ed.elements.head)
      for(et <- ed.elements.tail) {
        sb.append(", ")
        print(et)
      }
      sb.append("}")
    }
    println()
  }

  def print(ld: LatticeDecl, indent: Natural) : Unit = {
    printIndent(indent)
    sb.append("lattice ")
    print(ld.name)
    if(ld.superLattice.nonEmpty) {
      sb.append(" extends ")
      print(ld.superLattice.head)
      for(ldt<-ld.superLattice.tail) {
        sb.append(", ")
        print(ldt)
      }
    }
    println()
  }

  def print(rd : RecordDecl, indent: Natural) : Unit ={
    printIndent(indent)
    sb.append("record ")
    print(rd.name)
    println()
    printIndent(indent+1)
    print(rd.fields.head)
    for(ft <- rd.fields.tail) {
      println()
      printIndent(indent + 1)
      print(ft)
    }
    println()
  }

  def print(f: FieldDecl) : Unit ={
    print(f.id)
    sb.append(" : ")
    print(f.fieldType)
  }

  def print(p : Port, indent: Natural) : Unit = {
    printIndent(indent)
    if(p.isIn) {sb.append("in ")} else {sb.append("out ")}
    print(p.id)
    p.name match {
      case None =>
      case _ =>
        sb.append(" : ")
        print(p.name.get)
    }
  }

  def print(p: Propagation, indent: Natural): Unit ={
    printIndent(indent)
    print(p.id)
    sb.append(" = {")
    print(p.errorTypes.head)
    for(pett<-p.errorTypes.tail) {
      sb.append(", ")
      print(pett)
    }
    sb.append("}")
  }

  def print(f : Flow, indent: Natural) : Unit ={
    printIndent(indent)
    print(f.id)
    sb.append(" : ")
    f.from match {
      case None => sb.append("*")
      case _ =>
        print(f.from.get)
        if (f.fromE.nonEmpty) {
          sb.append("{")
          print(f.fromE.head)
          for (fe <- f.fromE.tail) {
            sb.append(", ")
            print(fe)
          }
          sb.append("}")
        }
    }
    sb.append(" -> ")
    f.to match {
      case None => sb.append("*")
      case _ =>
        print(f.to.get)
        if (f.toE.nonEmpty) {
          sb.append("{")
          print(f.toE.head)
          for (te <- f.toE.tail) {
            sb.append(", ")
            print(te)
          }
          sb.append("}")
        }
    }
  }

  def print(f: CFlow, indent: Natural): Unit = {
    printIndent(indent)
    print(f.id)
    sb.append(" : ")
    if (f.fromE.isEmpty) {
      sb.append("*")
    }
    else {
      sb.append("{")
      print(f.fromE.head)
      for (fe <- f.fromE.tail) {
        sb.append(", ")
        print(fe)
      }
      sb.append("}")
    }
    sb.append(" -> ")
    if (f.toE.isEmpty) {
      sb.append("*")
    }
    else {
      sb.append("{")
      print(f.toE.head)
      for (te <- f.toE.tail) {
        sb.append(", ")
        print(te)
      }
      sb.append("}")
    }
  }

  def print(p : Property, indent: Natural) : Unit={
    printIndent(indent)
    print(p.id)
    sb.append(" : ")
    print(p.propType)
    p.value match {
      case None =>
      case _ =>
        sb.append(" = ")
        print(p.value.get)
    }
  }

  def print(cd: ConstantDecl, indent:Natural): Unit = {
    printIndent(indent)
    print(cd.name)
    sb.append(" : ")
    print(cd.constType)
    sb.append(" = ")
    print(cd.init)
  }

  def print(t : Type) : Unit = {
    t match {
      case t:CompoundType => print(t.asInstanceOf[CompoundType])
      case t:BasicType => print(t.asInstanceOf[BasicType])
    }
  }

  def print(ct: CompoundType):Unit = {
    ct match {
      case OptionTypeDecl(typeVal) =>
        sb.append("Option[")
        print(typeVal)
        sb.append("]")
      case SetTypeDecl(typeVal) =>
        sb.append("Set[")
        print(typeVal)
        sb.append("]")
      case SeqTypeDecl(typeVal) =>
        sb.append("Seq[")
        print(typeVal)
        sb.append("]")
      case MapTypeDecl(keyType, valueType) =>
        sb.append("Map[")
        print(keyType)
        sb.append(", ")
        print(valueType)
        sb.append("]")
    }
  }

  def print(bt: BasicType): Unit = {
    bt match {
      case BooleanTypeDecl() => sb.append("Boolean")
      case IntegerTypeDecl(value) =>
        sb.append("Integer")
        value match {
          case None =>
          case _ =>
            sb.append("(")
            print(value.get._1)
            sb.append(", ")
            print(value.get._2)
            sb.append(")")
        }
      case RealTypeDecl() => sb.append("Real")
      case StringTypeDecl() => sb.append("String")
      case ComponentTypeDecl() => sb.append("Component")
      case PortTypeDecl() => sb.append("Port")
      case NamedTypeDecl(value) => print(value)
    }
  }

  def print(itd: IntTypeDisc): Unit = {
    itd match {
      case IntLit(value) => sb.append(value)
      case NamedIntType(value) => print(value)
      case ArbitrartyIntType() => sb.append("_")
    }
  }

  def print(init : Init): Unit = {
    init match {
      case BooleanInit(value) => sb.append(value.toString)
      case IntegerInit(value) => sb.append(value.toString)
      case RealInit(value) => sb.append(value.toString)
      case StringInit(value) =>
        sb.append("\"")
        sb.append(value)
        sb.append("\"")
      case RecordInit(name, fields) =>
        print(name)
        sb.append("(")
        val sortedFields = ListMap(fields.toSeq:_*)
        print(sortedFields.head._1)
        sb.append(" = ")
        print(sortedFields.head._2)
        for(ft <- sortedFields.tail) {
          sb.append(", ")
          print(ft._1)
          sb.append(" = ")
          print(ft._2)
        }
        sb.append(")")
      case NameRefInit(name, ref) =>
        print(name)
        ref match {
          case Some(_) =>
            sb.append(" . ")
            print(ref.get)
          case None =>
        }
      case NoneInit(typeInit) =>
        sb.append("None[")
        print(typeInit)
        sb.append("]")
      case SomeInit(typeInit, value) =>
        sb.append("Some[")
        print(typeInit)
        sb.append("](")
        print(value)
        sb.append(")")
      case SetInit(typeInit, value) =>
        sb.append("Set[")
        print(typeInit)
        sb.append("](")
        val sortedSet = value
        print(sortedSet.head)
        for(sst <- sortedSet.tail) {
          sb.append(", ")
          print(sst)
        }
        sb.append(")")
      case SeqInit(typeInit, value) =>
        sb.append("Seq[")
        print(typeInit)
        sb.append("](")
        print(value.head)
        for(vt <- value.tail) {
          sb.append(", ")
          print(vt)
        }
        sb.append(")")
      case MapInit(keyType, valueType, value) =>
        sb.append("Map[")
        print(keyType)
        sb.append(", ")
        print(valueType)
        sb.append("](")
        val sortedValue = ListMap(value.toSeq:_*)
        print(sortedValue.head._1)
        sb.append(" -> ")
        print(sortedValue.head._2)
        for(svt <- sortedValue.tail) {
          sb.append(", ")
          print(svt._1)
          sb.append(" -> ")
          print(svt._2)
        }
        sb.append(")")
    }
  }

  def print(n : Name): Unit ={
    print(n.value.head)
    for (tn <- n.value.tail) {
      sb.append("::")
      print(tn)
    }
  }

  def print(i : Id): Unit ={
    sb.append(i.value)
  }

  @inline
  private def printIndent(indent: Natural): Unit = {
    var i = 0
    while (i < indent) {
      sb.append("  ")
      i += 1
    }
  }

  @inline
  private def println(): Unit = {
    sb.append('\n')
  }

  @inline
  private def peek(s: String)(index: Natural): NaturalSentinel =
    if (index < s.length) s.charAt(index) else naturalSentinel

}