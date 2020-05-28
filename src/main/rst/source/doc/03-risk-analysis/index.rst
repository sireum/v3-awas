.. raw:: html

   <script src="../../_static/clipboard.min.js" ></script> <script
   src="../../_static/class.js" ></script> <script
   src="../../_static/date.js" ></script>
   <br/>
   <font color="darkgray">
   <big><big><b>
   Sireum Awas Documentation
   </b></big></big>
   </font>
   <br/>
   <font color="black">
   <big><big><big><big><b>
   3. Risk Analysis
   </b></big></big></big></big>
   </font>

Sireum Awas Risk Analysis
#########################


In the context of Model Based System Engineering (MBSE), risk
management is an important part of developing critical
systems. Although interleaved into every facet of the system
development process, artifacts generated in the process are essential
for obtaining regulatory certifications. Risk analysis, part of the
risk management consists of mechanical procedure of finding causes for
reasonable foreseeable system failures. Awas's reachability analysis
and visualization capabilities are useful in exploring and communicating
the failure scenarios and in ensuring the effectiveness of the measure
taken to eliminate failures.

ISO 14971/AAMI 80001
*********************

In medical domain, ISO 14971 is the widely adapted international
standard for risk management. The following sections describes: 

* Description of the OpenPCA Pump medical device model

* The application of ISO 14971 to Open PCA Pump model and the use of
  AADL properties to capture risk management concepts

* Use of Error Modeling Version 2(EMV2) annex and Awas reachability
  analysis to accomplish crucial steps in the ISO 14971 risk
  management process

OpenPCA Pump Model
------------------

In this section, we illustrate (Open Patient Controlled Analgesic)
Open PCA Pump medical device model. In the coming sections we use this
model to demonstrate the application of Awas in the risk analysis
process.

Background
~~~~~~~~~~

.. image:: figures/hospira-pumps-open-closed-above.jpg
    :align: center
    :width: 100%
    :alt: Hospira PCA Pump



A PCA Pump is a medical device used for pain management in a variety
of clinical settings intended to administer opioid through
intravenous(IV) infusion. Typically, a caregiver assembles the pump
with the opioid and configures it based on the prescription. The
prescription may include information such as the medication, quantity,
rate of flow and duration. On infusing the drug, the pump may operate
on either in basal mode or bolus mode. In basal mode, infusion of the
drug happens in a constant rate of flow. In bolus mode, the pump
operates at a high rate for a short period of time. The bolus
(handheld switch) provided to the patient activates the bolus mode
infusion. However, frequent activation of the bolus poses a serious
overdose threat to the patient. In the past, PCA Pumps have harmed the
patients and even led to death of a patient due to over infusion. 

Design
~~~~~~

We constructed the Open PCA Pump model based on the ISOSCELES medical
device reference architecture. The reference architecture suggest the
separation of functional components from the physical
components. Also, provides templates for generic subsystems such as
operation, safety, user interface, network interface, power and
sensor/actuators. 

.. image:: figures/PCAcontainment.png
     :align: center
     :width: 80%
     :alt: PCA Pump Instance Diagram

The top level of the functional subsystem separates the safety
subsystem from the rest of the that detects and mitigates the faulty
behaviors. Error modeling covers both physical and functional
subsystem and the errors flows from physical to functional subsystem
and vise versa via the AADL bindings mechanism.

ISO 14971/AAMI 80001
--------------------

ISO 14971 is a risk management standard intended for medical device
manufactures. In the medical device context, risk is related to
injury, not only to the patient, but also other users such as
clinicians. Risk analysis is a part of the risk management process in
which Awas can assist an analysis to identify, communicate and
mitigate risk.

.. image:: figures/risk-management-process.png
      :align: center
      :width: 60%
      :alt: Risk Management Process

Risk Analysis
~~~~~~~~~~~~~

ISO 14971 concepts related to risk analysis

* Harm: Injury or damage to the health of people, or damage to property or the environment 

* Hazard: Potential source of harm

* Hazardous Situation: Circumstance in which people, property or the environment is/are exposed to one or more hazards

* Initiating cause: Faults or other issues that lead to a hazard

The below figure illustrates the relationships between these terms. 

.. image:: figures/risk-analysis-process.png
      :align: center
      :width: 50%
      :alt: Risk Analysis Process

Risk Analysis Process
~~~~~~~~~~~~~~~~~~~~~

The medical device manufacturer performs risk analysis and records the
risk analysis activities and their results in the risk management
file. The regulatory authorities check for the device compliance by
inspecting the risk management file. The manufacturer shall perform
the following risk analysis steps:

1. Identify intended use and reasonably foreseeable misuse

2. Identification of characteristics related to safety

3. Identification of hazards and hazardous situations

4. Risk estimation

Risk Analysis Sample Scenario
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Consider the scenario where a patient presses the bolus multiple times
in a short duration. In other words, pressing the bolus again and
again before the drug takes effect. If the pump infuses the drug each
time, there is a possibility of overdosing the patient. In this
scenario over infusion is the hazard and overdosing(infusing the drug
above the safe level) is the hazardous situation. The below figure
provides the expected result of performing the risk analysis process
for this scenario.

.. image:: figures/over-infusion.png
      :align: center
      :width: 50%
      :alt: Risk analysis process for over infusion



AADL 14971 Property template
----------------------------

We took advantage of the user definable property types feature of AADL
and developed a set of properties to capture ISO 14971 concepts in a
AADL model.

.. literalinclude:: code/ISO14971_80001.aadl
      :language: aadl
      :linenos:

Property instance
~~~~~~~~~~~~~~~~~

- Harm

  .. code:: aadlproperty
       
    	H1 : constant ISO14971_80001::Harm => [
		ID => "H1";
		Description => "Patient Overdosed";
		Severity => Catastrophic;
	];

- Hazardous Situation

  .. code:: aadlproperty

	OverInfusion : constant ISO14971_80001::Hazardous_Situation => [
		ID => "OverInfusion";
		Description => "Infusing drug when the patient's health is deteriorating";
		Hazard => Hazards::Haz1;
		Paths_to_Harm => ([
	          Harm => Harms::H1;
		  Contributing_Factors => (ContributingFactors::HealthDeteriorating);
		  Probability_of_Transition => Remote;
		]);
		Risk => High;
		Probability => Remote;
	];

- Hazard
  
  .. code:: aadlproperty

	Haz1 : constant ISO14971_80001::Hazard => [
		ID => "Haz1";
		Description => "Drug over-infusion";
	];


- Initiating Cause
  
  .. code:: aadlproperty

	IncorrectDrug : constant ISO14971_80001::Cause => [
		ID => "IncorrectDrug";
		Description => "Incorrect drug loaded into pump";
		Probability =>  Frequent;
	];

Application of property
~~~~~~~~~~~~~~~~~~~~~~~

- Hazardous Situation

  .. code:: aadlproperty

        ISO14971_80001::Hazardous_Situations => (
		HazardousSituations::OverInfusion, 
		HazardousSituations::UnderInfusion, 
		HazardousSituations::IncorrectDrug
	);       
       
- Hazard

  .. code:: aadlproperty

          ISO14971_80001::Hazards => (Hazards::Haz1) applies to act.drug_outlet.DrugOverInfusion;
 	

- Cause

  .. code:: aadlproperty

          ISO14971_80001::causes => (Causes::FrequentButtonPress) applies to sense.patient_button_press.TooSoonPress;


Awas Visualization
------------------

.. raw:: html
	 
    <big><big><big><b>
    <a href="../../_static/open-pca/index.html" target="_blank">Open PCA Pump Awas Visualization</a> 
    </b></big></big></big>

Awas Report
-----------
     
.. raw:: html
	 
    <big><big><big><b>
    <a href="../../_static/open-pca/risk-analysis-report.html" target="_blank">Open PCA Pump Awas ISO 14971 Report</a> 
    </b></big></big></big>
 

 
