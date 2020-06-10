package org.sireum.awas.report

object ISO14971Property {
  val ISO14971 = "ISO14971_80001"

  val SYSTEM_PROP = ISO14971 + "::SystemInfo"
  val SYSTEM_PROP_NAME = "Name"
  val SYSTEM_PROP_DESC = "Description"
  val SYSTEM_PROP_INTENDED = "IntendedUse"

  val HARM = ISO14971 + "::Harm"
  val HARM_ID = "ID"
  val HARM_DESC = "Description"
  val HARM_SEVERITY = "Severity"

  val CAUSES = ISO14971 + "::Causes"
  val CAUSE = ISO14971 + "::Cause"
  val CAUSE_ID = "ID"
  val CAUSE_DESC = "Description"
  val CAUSE_BASIS = "Basis"
  val CAUSE_NOOPC = "NumberOfOccurrencesPerCause"
  val CAUSE_PROBABILITY = "Probability"

  val HAZARDS = ISO14971 + "::Hazards"
  val HAZARD = ISO14971 + "::Hazard"
  val HAZARD_ID = "ID"
  val HAZARD_DESC = "Description"
  val HAZARD_CAUSES = "Causes"

  val HAZ_SITUATIONS = ISO14971 + "::Hazardous_Situations"
  val HAZ_SITUATION = ISO14971 + "::Hazardous_Situation"
  val HAZ_SITUATION_ID = "ID"
  val HAZ_SITUATION_DESC = "Description"
  val HAZ_SITUATION_HAZARD = "Hazard"
  val HAZ_SITUATION_SEVERITY = "Severity"
  val HAZ_SITUATION_PATHS = "Paths_to_harm"
  val HAZ_SITUATION_HARM = "Harm"
  val HAZ_SITUATION_CF = "Contributing_Factors"
  val HAZ_SITUATION_PROB_TRAN = "Probability_of_Transition"
  val HAZ_SITUATION_RISK = "Risk"
  val HAZ_SITUATION_PROB = "Probability"

  val CONTRIB_FACTOR = ISO14971 + "::Contributing_Factor"
  val CONTRIB_ID = "ID"
  val CONTRIB_DESC = "Description"

  val RISK_CONTROL = ISO14971 + "::Risk_Control"
  val RISK_CONTROL_ID = "ID"
  val RISK_CONTROL_DESC = "Description"
  val RISK_CONTROL_PROB = "Effective_Probability"
}
