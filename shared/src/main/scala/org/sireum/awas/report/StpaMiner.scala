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

//package org.sireum.awas.report

import org.sireum.awas.ast.{Id, Property, RecordInit, StringInit}
import org.sireum.awas.report.StpaProperty
import org.sireum.awas.slang.Aadl2Awas
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

class StpaMiner {

  def builder(): Option[STPAProperties] = {
    if (SymbolTable.getTable.isDefined) {
      val st = SymbolTable.getTable.get

      None
    } else {
      None
    }
  }

  def buildSystemProp(st: SymbolTable): Option[SystemInfo] = {
    var compSysInfo = imapEmpty[ResourceUri, ISet[Property]]
    val sysProp =
      st.componentTable(st.system).componentDecl.properties.find(p => p.id.value == StpaProperty.SYSTEM_PROP)
    if (sysProp.isDefined) {
      val sysVal = sysProp.get.value.get.asInstanceOf[RecordInit]
      val sysName = sysVal.fields.get(Id(StpaProperty.SYSTEM_PROP_NAME)).map(_.asInstanceOf[StringInit].value)
      val sysDesc = sysVal.fields.get(Id(StpaProperty.SYSTEM_PROP_DESC)).map(_.asInstanceOf[StringInit].value)
//      val sysCtrl = sysVal.fields.get(Id(StpaProperty.SYSTEM_PROP_CONTROL_STRUCT)).map(_.asInstanceOf[])
      SystemInfo(sysName.get, sysDesc, ilistEmpty)
//      sysProp.get.id
    }
    None
  }

  def buildAccidentLevels(st: SymbolTable): IMap[String, AccidentLevel] = {
    val sysHaz =
      st.componentTable(st.system).componentDecl.properties.filter(p => p.id.value == StpaProperty.SYSTEM_HAZARD)
    val accLvl = sysHaz.flatMap { s =>
      val contrib = s.value.get.asInstanceOf[RecordInit].fields.get(Id(StpaProperty.SYSTEM_HAZARD_CONTRIB))
      if (contrib.isDefined) {
        val accident = contrib.get.asInstanceOf[RecordInit].fields.get(Id(StpaProperty.SYSTEM_HAZARD_ACCIDENT))
        if (accident.isDefined) {
          val al = accident.get.asInstanceOf[RecordInit].fields.get(Id(StpaProperty.ACCIDENT_SEVERITY))
          if (al.isDefined) {
            Some(al.get.asInstanceOf[RecordInit])
          } else None
        } else None
      } else None
    }
    val sorted = accLvl.sortBy(
      al =>
        al.fields(Id(StpaProperty.ACCIDENT_LEVEL_LEVEL))
          .asInstanceOf[RecordInit]
          .fields(Id(Aadl2Awas.UNIT_PROP_VAL))
          .asInstanceOf[StringInit]
          .value
          .toInt
    )
    if (sorted.nonEmpty) {
      sorted.map { al =>
        val id = al.fields(Id(StpaProperty.ACCIDENT_LEVEL_ID)).asInstanceOf[StringInit].value
        val sec = al.fields.get(Id(StpaProperty.ACCIDENT_LEVEL_SEC)).map(_.asInstanceOf[StringInit].value)
        val level = al.fields
          .get(Id(StpaProperty.ACCIDENT_LEVEL_LEVEL))
          .flatMap(
            _.asInstanceOf[RecordInit].fields
              .get(Id(Aadl2Awas.UNIT_PROP_VAL))
              .map(_.asInstanceOf[StringInit].value.toInt)
          )
        val desc = al.fields.get(Id(StpaProperty.ACCIDENT_LEVEL_DESC)).map(_.asInstanceOf[StringInit].value)

        (if (sec.isDefined) id + "_" + sec.get else id, AccidentLevel(id, sec, level, desc))
      }.toMap

    } else {
      imapEmpty
    }
  }

  def buildAccidents(st: SymbolTable): IList[Accident] = {
    val sysHaz =
      st.componentTable(st.system).componentDecl.properties.filter(p => p.id.value == StpaProperty.SYSTEM_HAZARD)
    val acc = sysHaz.flatMap { s =>
      if (s.value.isDefined) {
        val contrib = s.value.get.asInstanceOf[RecordInit].fields.get(Id(StpaProperty.SYSTEM_HAZARD_CONTRIB))
        if (contrib.isDefined) {
          val acc = contrib.get.asInstanceOf[RecordInit].fields.get(Id(StpaProperty.SYSTEM_HAZARD_ACCIDENT))
          if (acc.isDefined) {
            Some(acc.get.asInstanceOf[RecordInit])
          } else None
        } else None
      } else None
    }
    if (acc.nonEmpty) {
      acc.map { a =>
        val id = a.fields(Id(StpaProperty.ACCIDENT_ID)).asInstanceOf[StringInit].value
        val desc = a.fields.get(Id(StpaProperty.ACCIDENT_DESC)).map(_.asInstanceOf[StringInit].value)
        val alId = a.fields
          .get(Id(StpaProperty.ACCIDENT_SEVERITY))
          .flatMap(
            _.asInstanceOf[RecordInit].fields
              .get(Id(StpaProperty.ACCIDENT_LEVEL_ID))
              .map(_.asInstanceOf[StringInit].value)
          )
        val alsec = a.fields
          .get(Id(StpaProperty.ACCIDENT_SEVERITY))
          .flatMap(
            _.asInstanceOf[RecordInit].fields
              .get(Id(StpaProperty.ACCIDENT_LEVEL_SEC))
              .map(_.asInstanceOf[StringInit].value)
          )

        val al = if (alId.isDefined) Some(alId.get + (if (alsec.isDefined) "_" + alsec.get else "")) else None
        Accident(id, desc, al)
      }.toList
    } else {
      ilistEmpty
    }
  }

  def buildSystemConstraint(st: SymbolTable): IList[SafetyConstraint] = {
    val sysHaz =
      st.componentTable(st.system).componentDecl.properties.filter(p => p.id.value == StpaProperty.SYSTEM_HAZARD)
    val constraint = sysHaz.flatMap { s =>
      if (s.value.isDefined) {
        s.value.get
          .asInstanceOf[RecordInit]
          .fields
          .get(Id(StpaProperty.SYSTEM_HAZARD_CONSTRAINT))
          .map(_.asInstanceOf[RecordInit])
      } else None
    }
    constraint.flatMap { sc =>
      val scid = sc.fields.get(Id(StpaProperty.SAFETY_CONSTRAINT_ID)).map(_.asInstanceOf[StringInit].value)
      val scconstraint =
        sc.fields.get(Id(StpaProperty.SAFETY_CONSTRAINT_CONSTRAINT)).map(_.asInstanceOf[StringInit].value)
      if (scid.isDefined) Some(SafetyConstraint(scid.get, scconstraint)) else None
    }.toList
  }

//  def buildEnvCond(st : SymbolTable)

}

sealed trait StpaNode {
  private var sourceComp: Option[ResourceUri] = None

  def setSourceComp(uri: ResourceUri) = {
    sourceComp = Some(uri)
  }

  def getSourceComp: Option[ResourceUri] = sourceComp
}

object Role extends Enumeration {
  type Role = Value
  val Sensor, Actuator, Controller, ControlledProcess, External, Others = Value
}

object Location extends Enumeration {
  type Location = Value
  val InternalCause, ExternalCause = Value
}

case class STPAProperties(
  systemInfo: SystemInfo,
  accidentLevels: IList[AccidentLevel],
  accidents: IList[Accident],
  constraints: IList[SafetyConstraint],
  envCond: IList[EnvironmentCond],
  systemHazards: IList[SystemHazard]
) extends StpaNode

case class AccidentLevel(id: String, sec: Option[String], level: Option[Int], desc: Option[String]) extends StpaNode

case class Accident(id: String, desc: Option[String], severity: Option[String]) extends StpaNode

case class SystemHazard(
  id: String,
  systemName: String,
  desc: String,
  constraints: IList[SafetyConstraint],
  controlStruct: ControlStructure,
  contribution: IList[(Accident, EnvironmentCond)],
  errors: MMap[ResourceUri, MSet[ResourceUri]],
  hazards: MMap[ResourceUri, MSet[Hazard]]
) extends StpaNode

case class EnvironmentCond(id: String, desc: String) extends StpaNode

case class SafetyConstraint(id: String, constraint: Option[String]) extends StpaNode

case class Hazard(
  id: String,
  desc: String,
  constraint: IList[SafetyConstraint],
  causes: IList[Cause],
  appliedTo: MMap[ResourceUri, MSet[ResourceUri]]
) extends StpaNode

case class Cause(id: String, desc: String, location: Location.Location, hazards: IList[Hazard]) extends StpaNode

case class ComponentInfo(uri: ResourceUri, desc: String, role: Role.Role) extends StpaNode

case class ControlStructure(id: String, desc: String, nodes: MMap[ResourceUri, ComponentInfo]) extends StpaNode

case class SystemInfo(name: String, desc: Option[String], ctrlStruct: ISeq[ControlStructure]) extends StpaNode
