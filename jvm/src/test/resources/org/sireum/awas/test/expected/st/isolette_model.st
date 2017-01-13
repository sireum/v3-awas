#TYPES#

#TYPE TABLE#

#STATE MACHINES#

#STATE MACHINE TABLE#
#COMPONENTS#
component://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas#Air
component://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas#HeatSource
component://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas#Infant
component://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas#Nurse
component://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas#OperatorInterface
component://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas#TempSensor
component://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas#Thermostat
#COMPONENT TABLE#
component://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas#Air:
#PORTS#
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/Air#from_heater
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/Air#from_infant
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/Air#to_infant
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/Air#to_sensor

component://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas#HeatSource:
#PORTS#
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/HeatSource#heater_output
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/HeatSource#on_heater

component://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas#Infant:
#PORTS#
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/Infant#heat_in
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/Infant#heat_out

component://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas#Nurse:
#PORTS#
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/Nurse#monitor
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/Nurse#set_params

component://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas#OperatorInterface:
#PORTS#
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/OperatorInterface#feedback_in
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/OperatorInterface#feedback_out
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/OperatorInterface#settings_in
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/OperatorInterface#settings_out

component://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas#TempSensor:
#PORTS#
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/TempSensor#air_heat
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/TempSensor#air_temp

component://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas#Thermostat:
#PORTS#
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/Thermostat#feedback
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/Thermostat#heater_ctrl
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/Thermostat#sensed_temp
port://file:/Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/target/scala-2.12/test-classes/org/sireum/awas/test/example/awas-lang/isolette_model.awas/Thermostat#settings

