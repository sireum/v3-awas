#TYPES#

#TYPE TABLE#

#STATE MACHINES#

#STATE MACHINE TABLE#
#COMPONENTS#
component:$$example/awas-lang/isolette_model.awas#Air
component:$$example/awas-lang/isolette_model.awas#HeatSource
component:$$example/awas-lang/isolette_model.awas#Infant
component:$$example/awas-lang/isolette_model.awas#Nurse
component:$$example/awas-lang/isolette_model.awas#OperatorInterface
component:$$example/awas-lang/isolette_model.awas#TempSensor
component:$$example/awas-lang/isolette_model.awas#Thermostat
#COMPONENT TABLE#
component:$$example/awas-lang/isolette_model.awas#Air:
#PORTS#
port-in:$$example/awas-lang/isolette_model.awas$Air#from_heater
port-in:$$example/awas-lang/isolette_model.awas$Air#from_infant
port-out:$$example/awas-lang/isolette_model.awas$Air#to_infant
port-out:$$example/awas-lang/isolette_model.awas$Air#to_sensor

#PROPAGATION
port-out:$$example/awas-lang/isolette_model.awas$Air#to_sensor:
#ERRORS#
port-in:$$example/awas-lang/isolette_model.awas$Air#from_infant:
#ERRORS#
port-out:$$example/awas-lang/isolette_model.awas$Air#to_infant:
#ERRORS#
port-in:$$example/awas-lang/isolette_model.awas$Air#from_heater:
#ERRORS#
component:$$example/awas-lang/isolette_model.awas#HeatSource:
#PORTS#
port-in:$$example/awas-lang/isolette_model.awas$HeatSource#on_heater
port-out:$$example/awas-lang/isolette_model.awas$HeatSource#heater_output

#PROPAGATION
port-in:$$example/awas-lang/isolette_model.awas$HeatSource#on_heater:
#ERRORS#
port-out:$$example/awas-lang/isolette_model.awas$HeatSource#heater_output:
#ERRORS#
component:$$example/awas-lang/isolette_model.awas#Infant:
#PORTS#
port-in:$$example/awas-lang/isolette_model.awas$Infant#heat_in
port-out:$$example/awas-lang/isolette_model.awas$Infant#heat_out

#PROPAGATION
port-in:$$example/awas-lang/isolette_model.awas$Infant#heat_in:
#ERRORS#
port-out:$$example/awas-lang/isolette_model.awas$Infant#heat_out:
#ERRORS#
component:$$example/awas-lang/isolette_model.awas#Nurse:
#PORTS#
port-in:$$example/awas-lang/isolette_model.awas$Nurse#monitor
port-out:$$example/awas-lang/isolette_model.awas$Nurse#set_params

#PROPAGATION
port-out:$$example/awas-lang/isolette_model.awas$Nurse#set_params:
#ERRORS#
port-in:$$example/awas-lang/isolette_model.awas$Nurse#monitor:
#ERRORS#
component:$$example/awas-lang/isolette_model.awas#OperatorInterface:
#PORTS#
port-in:$$example/awas-lang/isolette_model.awas$OperatorInterface#feedback_in
port-in:$$example/awas-lang/isolette_model.awas$OperatorInterface#settings_in
port-out:$$example/awas-lang/isolette_model.awas$OperatorInterface#feedback_out
port-out:$$example/awas-lang/isolette_model.awas$OperatorInterface#settings_out

#PROPAGATION
port-in:$$example/awas-lang/isolette_model.awas$OperatorInterface#settings_in:
#ERRORS#
port-out:$$example/awas-lang/isolette_model.awas$OperatorInterface#feedback_out:
#ERRORS#
port-out:$$example/awas-lang/isolette_model.awas$OperatorInterface#settings_out:
#ERRORS#
port-in:$$example/awas-lang/isolette_model.awas$OperatorInterface#feedback_in:
#ERRORS#
component:$$example/awas-lang/isolette_model.awas#TempSensor:
#PORTS#
port-in:$$example/awas-lang/isolette_model.awas$TempSensor#air_heat
port-out:$$example/awas-lang/isolette_model.awas$TempSensor#air_temp

#PROPAGATION
port-in:$$example/awas-lang/isolette_model.awas$TempSensor#air_heat:
#ERRORS#
port-out:$$example/awas-lang/isolette_model.awas$TempSensor#air_temp:
#ERRORS#
component:$$example/awas-lang/isolette_model.awas#Thermostat:
#PORTS#
port-in:$$example/awas-lang/isolette_model.awas$Thermostat#sensed_temp
port-in:$$example/awas-lang/isolette_model.awas$Thermostat#settings
port-out:$$example/awas-lang/isolette_model.awas$Thermostat#feedback
port-out:$$example/awas-lang/isolette_model.awas$Thermostat#heater_ctrl

#PROPAGATION
port-out:$$example/awas-lang/isolette_model.awas$Thermostat#feedback:
#ERRORS#
port-in:$$example/awas-lang/isolette_model.awas$Thermostat#settings:
#ERRORS#
port-in:$$example/awas-lang/isolette_model.awas$Thermostat#sensed_temp:
#ERRORS#
port-out:$$example/awas-lang/isolette_model.awas$Thermostat#heater_ctrl:
#ERRORS#
#CONNECTIONS#
connection:$$example/awas-lang/isolette_model.awas#h1
connection:$$example/awas-lang/isolette_model.awas#h2
connection:$$example/awas-lang/isolette_model.awas#i1
connection:$$example/awas-lang/isolette_model.awas#i2
connection:$$example/awas-lang/isolette_model.awas#o1
connection:$$example/awas-lang/isolette_model.awas#o2
connection:$$example/awas-lang/isolette_model.awas#o3
connection:$$example/awas-lang/isolette_model.awas#o4
connection:$$example/awas-lang/isolette_model.awas#t1
connection:$$example/awas-lang/isolette_model.awas#t2
