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

package org.sireum.awas.query

import org.parboiled2._
import org.sireum.awas.query.FilterID.FilterID
import org.sireum.message.{FlatPos, Reporter}
import shapeless.HNil

import scala.language.implicitConversions
import scala.util.{Failure, Success}

class QueryParser(val input : ParserInput) extends Parser {

  def WS = rule { quiet(zeroOrMore(WhitespaceChar | Comment | Newline)) }

  implicit private[this] def wspStr(s: String): Rule0 = rule {
    str(s) ~ WS
  }
  implicit private[this] def wspChar(s: Char): Rule0 = rule {
    ch(s) ~ WS
  }

  def modelFile = rule {model ~ EOI}

  def model : Rule1[Model] = rule {oneOrMore(queryStmt) ~> ((x : Seq[QueryStmt] )=> Model(x.toVector))}

  def queryStmt : Rule1[QueryStmt] = rule { (WS ~ ID ~WS ~ "=" ~ WS ~
    expr ~ WS) ~> ((x, y) => QueryStmt(x, y))}

  def expr : Rule1[QueryExpr] = rule {
    (&(!Keywords) ~ pexpr ~ WS ~ optional(exprFactor)) |
    ((atomic("reach") ~ reachExprs) ~ WS ~ optional(exprFactor))
  }

  def exprFactor: Rule[shapeless.::[QueryExpr, HNil], shapeless.::[QueryExpr, HNil]] = rule {
    (capture("-") ~ expr) ~> (BinaryExpr) | //~> ((x:QueryExpr, y:QueryExpr) => drop[QueryExpr]()) //|
    (capture(atomic("union")) ~ expr) ~> (BinaryExpr) |
    (capture(atomic("intersect")) ~ expr) ~> (BinaryExpr) |
    (ch(':') ~ filter ~ WS) ~> ((x : QueryExpr, y : FilterID)
    =>  FilterExpr(x, y) ) ~ optional(exprFactor)
  }

  def withExpr : Rule1[WithExpr] = rule {
    capture(atomic("some")) ~ ch('(') ~ expr ~ ch (')') ~> SimpleWith |
    capture(atomic("all")) ~ ch('(') ~ expr ~ ch (')') ~> SimpleWith |
    capture(atomic("none")) ~ ch('(') ~ expr ~ ch (')') ~> SimpleWith |
    regExpr
  }

  def reachExprs : Rule1[ReachExpr] = rule {
    ( (atomic("forward") ~ expr) ~> (ForwardExpr)) |
    ( (atomic("backward") ~ expr ) ~> (BackwardExpr)) |
    ( (atomic("from") ~ expr ~ atomic("to") ~ expr)  ~> (ChopExpr)) |
    ( (atomic("simple") ~ atomic("paths") ~ atomic("from") ~ expr ~ atomic("to") ~ expr ~ optional(atomic("with") ~ withExpr)) ~> (SimplePathExpr)) |
    ( (atomic("paths") ~ atomic("from") ~ expr ~ atomic("to") ~ expr ~ optional(atomic("with") ~ withExpr)) ~> (PathExpr))
  }

  def regExpr : Rule1[RegExExpr] = rule {
    primaryExpr ~ optional(regExprFactor)
  }

  def regExprFactor: Rule[shapeless.::[RegExExpr, HNil], shapeless.::[RegExExpr, HNil]] = rule {
    capture("*") ~> ((x: RegExExpr, y:String) => UnaryRegEx("*", x)) |
    capture("+") ~> ((x: RegExExpr, y:String) => UnaryRegEx("+", x)) |
    (capture("?") ~> ((x: RegExExpr, y:String) => UnaryRegEx("?", x))) |
     ((ch(',') ~ regExpr) ~> ((x: RegExExpr, y: RegExExpr) => BinaryRegEx(x, ",", y))) //|
     // ((ch('|') ~ regExpr) ~> ((x: RegExExpr, y: RegExExpr) => BinaryRegEx(x, "|", y)))
  }

  def primaryExpr  : Rule1[PrimaryRegEx] = rule {
    ((nodeNameError ~ WS) ~> ((x : NodeNameError) => IdRegEx(x))) |
    ('(' ~ regExpr ~ ')' ~> ((x : RegExExpr) => ParenRegEx(x))) |
    (ch('_') ~ push(Anything()))
  }

  def filter : Rule1[FilterID] = rule {
    capture(atomic("node"))~WS ~> ((x: String) => FilterID.NODE) |
    capture(atomic("port-error"))~WS ~> ((x: String) =>FilterID.PORTERROR) | //PEG | op ordered choice
    capture(atomic("port"))~WS ~> ((x: String) =>FilterID.PORT) |
    capture(atomic("in-port"))~WS ~> ((x: String) =>FilterID.IN) |
    capture(atomic("out-port"))~WS ~> ((x: String) =>FilterID.OUT) |
    capture(atomic("error"))~WS ~> ((x: String) =>FilterID.ERROR) |
      capture(atomic("source")) ~ WS ~> ((x: String) => FilterID.SOURCE) |
      capture(atomic("sink")) ~ WS ~> ((x: String) => FilterID.SINK)
  }

  def pexpr : Rule1[PrimaryExpr] = rule {
    nodeNameError ~ WS ~> ((x : NodeNameError) => x)|
    (ch('(') ~ expr ~ ch(')')) ~> ((x : QueryExpr) => Paren(x))|
    (ch('{') ~ oneOrMore(nodeNameError).separatedBy(ch(',') ~ WS) ~ ch('}')) ~> ((x : Seq[NodeNameError]) => NodeSet(x.toVector)) |
    (capture('*') ~> ((x) => NodeEmpty())) |
    (WS~ '\'' ~ ID ~WS) ~> ((x : Id) => QueryName(x))
  }

  def nodeNameError : Rule1[NodeNameError] = rule {
    (nodeName ~WS~ optional(errors) ~> ((x : NodeName, y : Option[Vector[Vector[Id]]]) =>
      (if(y.isDefined) NodeNameError(x, y.get) else NodeNameError(x, QueryNode.emptySeq[Vector[Id]]))))
  }

  def test : Rule1[String]  = rule {
    optional(ID) ~> ((x : Option[Id]) => if(x.isDefined) x.get.value else "")
  }

  def errors = rule {
    ch('{') ~ oneOrMore(errorId).separatedBy(ch(',') ~ WS) ~ ch('}') ~> ((x) => x.toVector)
  }

  def errorId = rule {
    ((oneOrMore(ID).separatedBy(ch('.') ~ WS)) ~> ((x) => x.toVector))
  }

  def nodeName: Rule[HNil, shapeless.::[NodeName, HNil]] = rule {
    oneOrMore(ID).separatedBy(ch('.') ~ WS) ~> ((x : Seq[Id]) => NodeName(x.toVector))
  }

  def ID : Rule1[Id] = rule {
    ((!(Keywords ~ (WhitespaceChar | Newline | "//" | "/*"))
    ~ capture(CharPredicate.Alpha ~ zeroOrMore(CharPredicate.AlphaNum | '_')))
    ~> ((x : String) => Id(x)))
  }

  def COMMENT = rule {
    "*/" ~ capture(zeroOrMore(!"*/" ~ ANY))
  }

  def MultilineComment: Rule0 = rule { "/*" ~ zeroOrMore(MultilineComment | !"*/" ~ ANY) ~ "*/" }
  def Comment: Rule0 = rule {
    MultilineComment |
      "//" ~ zeroOrMore(!Newline ~ ANY) ~ (Newline | EOI)
  }

  def WhitespaceChar = rule { "\u0020" | "\u0009" }
  def Newline = rule { "\r\n" | "\n" }

  def LINE_COMMENT = rule {
    "//" ~ capture(zeroOrMore(!anyOf("\r\n") ~ ANY))
  }

  def Keywords = rule {
    "reach" | "backward" | "forward" | "from" | "to" | "paths" | "simple" |
    "union" | "intersect" | "some" | "all" | "none" | "with" |
    "node" | "port" | "port-error" | "flow" | "flow-source" |
    "flow-sink" | "flow-path" | "port-in" | "port-out" | "error"
  }
}

object QueryParser {

  def apply(input: String,
            reporter: Reporter): Option[Model] = {
    val queryParser = new QueryParser(input)

    queryParser.modelFile.run() match {
      case Failure(qp) => {
        val parseError = qp.asInstanceOf[ParseError]
        val error = queryParser.formatError(parseError)
        //        reporter.error(org.sireum.Option.none[Position](),
        //          org.sireum.String("Parse Error"), org.sireum.String(error))
        val pos = FlatPos(org.sireum.Option.none[org.sireum.String],
          org.sireum.U32(1), org.sireum.U32(1),
          org.sireum.U32(parseError.position.line),
          org.sireum.U32(parseError.position.column), org.sireum.U32(0), org.sireum.U32(0))

        reporter.error(org.sireum.Option.some(pos), org.sireum.String("Parse Error"), org.sireum.String(error))

        //reporter.error(parseError.position.line, parseError.position.column, parseError.position.index, error)
        None
      }
      case Success(qp) => {
        Some(qp)
      }

    }

  }

//  def main(args: Array[String]): Unit = {
//    val input = Seq(
//      "none_constraint_test1 = reach simple paths from Capnography.ETCO2{Error.ETCO2Early} to Patient.Vein{Error.TooMuchAnalgesic}"
////      "PatientVein = Patient.Vein",
////      "Cap_to_Pump = reach from Capnography.ETCO2 to PCA.infuse",
////      "Cap_to_Pump_paths = reach paths from Capnography.ETCO2 to PCA.infuse",
////      "none_constraint_test1 = reach paths from Capnography to PCA with none(Device_Network)",
////      "all_hazardous_situation_overdose = reach backward Patient.Vein{Error.TooMuchAnalgesic}",
////      "HS1 = reach backward administer.out{PCA_Errors.InCorrectDrugAdministration}",
////      "Cap_to_pump_hazard_2 = reach from Capnography.ETCO2{Error.ETCO2Early}\n                             to PCA.infuse{Error.TooMuchAnalgesic}",
////      "PatientVein = Patient.Vein\n\nInfusion_paths = reach backward Infuse_Drug",
////      "test = reach to Patient",
////      "\nPatientVein = Patient.Vein\n\nInfusion_paths = reach backward Infuse_Drug\n\nPulseOx_SpO2_influences_infusion = reach forward PulseOx.SpO2\n\nall_hazardous_situation_overdose = reach backward Patient.Vein{Error.TooMuchAnalgesic}\n\nCap_to_Pump = reach from Capnography.ETCO2 to PCA.infuse\n\nCap_to_Pump_paths = reach paths from Capnography.ETCO2 to PCA.infuse\n\nCap_to_pump_hazard_1 = reach from Capnography.RespiratoryRate{Error.RespirationRateHigh}\n                             to PCA.infuse{Error.TooMuchAnalgesic}\n\nCap_to_pump_hazard_1_path = reach paths from Capnography.RespiratoryRate{Error.RespirationRateHigh, Error.RespirationRateEarly}\n                                        to PCA.infuse{Error.TooMuchAnalgesic}\n\nCap_to_pump_hazard_2 = reach from Capnography.ETCO2{Error.ETCO2Early}\n                             to PCA.infuse{Error.TooMuchAnalgesic}\n\n//PulseOx_to_pump_check =  PulseOx.SpO2{Error.NoSpO2} -> * //dont know how to handle the incomming error, when no flows are propagation defined\n\nControl_Loop = reach paths from Patient.BloodSat to Patient.Vein\n\nFlow_HeartBeat = reach forward Patient.Heart_Beat\n\nbase_case = reach paths from Capnography to PCA\n\nall_constraint_test1 = reach paths from Capnography to PCA with all({Device_Network, Application})\n\nsome_constraint_test1 = reach paths from Capnography to PCA with some({Report_SpO2, RR_Report})\n\nnone_constraint_test1 = reach paths from Capnography to PCA with none(Device_Network)\n\nbase_case_ports = reach paths from Capnography.ETCO2 to PCA.infuse\n\nnone_constraint_ports = reach paths from Capnography.ETCO2 to PCA.infuse with none(Device_Network)\n\nsome_constraint_ports = reach paths from Capnography.ETCO2 to PCA.infuse with some({Application.RR, Application.Pulse})\n\nall_constraint_ports = reach paths from Capnography.ETCO2 to PCA.infuse with all({RR_Report.out, Pulse_Report_EKG.out})\n\n//test_regex_test1 = reach paths from Capnography to PCA with ((Device_Network, EKG_Report)*, _*, PCA | (Application, Issue_Ticket))\n\n//test_regex_"
//    )
//
//    input.foreach { query =>
//      new QueryParser(query).modelFile.run() match {
//        case Failure(x) => println(new QueryParser(query).formatError(x.asInstanceOf[ParseError]))
//        case Success(y) => {
//          println("success :" + QueryPPrinter(y))
//        }
//      }
//    }
//  }
}