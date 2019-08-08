package org.sireum.awas.report

object StpaProperty {

  val SYSTEM_PROP = "STPA::SystemInfo"
  val SYSTEM_PROP_NAME = "Name"
  val SYSTEM_PROP_Description = "Description"
  val SYSTEM_PROP_CONTROL_Loop = "Control_loops"

  val SYSTEM_HAZARD = "STPA::SystemHazards"
  val SYSTEM_HAZARD_ID = "ID"
  val SYSTEM_HAZARD_DESC = "Description"
  val SYSTEM_HAZARD_CONSTRAINT = "ViolatingConstraints"
  val SYSTEM_HAZARD_CTRL = "ControlLoop"
  val SYSTEM_HAZARD_CONTRIB = "Contribution"
  val SYSTEM_HAZARD_ACCIDENT = "accident"
  val SYSTEM_HAZARD_ENV = "environment_condition"

  val ACCIDENT = "STPA::Accident"
  val ACCIDENT_ID = "ID"
  val ACCIDENT_DESC = "Description"
  val ACCIDENT_SEVERITY = "Severity"

  val ACCIDENT_LEVEL = "STPA::AccidentLevel"
  val ACCIDENT_LEVEL_ID = "ID"
  val ACCIDENT_LEVEL_SEC = "Section"
  val ACCIDENT_LEVEL_LEVEL = "Level"
  val ACCIDENT_LEVEL_DESC = "Description"

  val SAFETY_CONSTRAINT_ID = "ID"
  val SAFETY_CONSTRAINT_CONSTRAINT = "Constraint"

  val ENV_COND_ID = "ID"
  val ENV_COND_DESC = "Description"
}
