#TYPES#

#TYPE TABLE#

#STATE MACHINES#

#STATE MACHINE TABLE#
#COMPONENTS#
component:$AWAS$$example/awas-lang/isolette_model.awas#Air
component:$AWAS$$example/awas-lang/isolette_model.awas#HeatSource
component:$AWAS$$example/awas-lang/isolette_model.awas#Infant
component:$AWAS$$example/awas-lang/isolette_model.awas#Nurse
component:$AWAS$$example/awas-lang/isolette_model.awas#OperatorInterface
component:$AWAS$$example/awas-lang/isolette_model.awas#TempSensor
component:$AWAS$$example/awas-lang/isolette_model.awas#Thermostat
#COMPONENT TABLE#
component:$AWAS$$example/awas-lang/isolette_model.awas#Air:
#PORTS#
port-in:$AWAS$$example/awas-lang/isolette_model.awas$Air#from_heater
port-in:$AWAS$$example/awas-lang/isolette_model.awas$Air#from_infant
port-out:$AWAS$$example/awas-lang/isolette_model.awas$Air#to_infant
port-out:$AWAS$$example/awas-lang/isolette_model.awas$Air#to_sensor

#PROPAGATION
port-in:$AWAS$$example/awas-lang/isolette_model.awas$Air#from_heater:
#ERRORS#
port-out:$AWAS$$example/awas-lang/isolette_model.awas$Air#to_infant:
#ERRORS#
port-out:$AWAS$$example/awas-lang/isolette_model.awas$Air#to_sensor:
#ERRORS#
port-in:$AWAS$$example/awas-lang/isolette_model.awas$Air#from_infant:
#ERRORS#
component:$AWAS$$example/awas-lang/isolette_model.awas#HeatSource:
#PORTS#
port-in:$AWAS$$example/awas-lang/isolette_model.awas$HeatSource#on_heater
port-out:$AWAS$$example/awas-lang/isolette_model.awas$HeatSource#heater_output

#PROPAGATION
port-in:$AWAS$$example/awas-lang/isolette_model.awas$HeatSource#on_heater:
#ERRORS#
port-out:$AWAS$$example/awas-lang/isolette_model.awas$HeatSource#heater_output:
#ERRORS#
component:$AWAS$$example/awas-lang/isolette_model.awas#Infant:
#PORTS#
port-in:$AWAS$$example/awas-lang/isolette_model.awas$Infant#heat_in
port-out:$AWAS$$example/awas-lang/isolette_model.awas$Infant#heat_out

#PROPAGATION
port-in:$AWAS$$example/awas-lang/isolette_model.awas$Infant#heat_in:
#ERRORS#
port-out:$AWAS$$example/awas-lang/isolette_model.awas$Infant#heat_out:
#ERRORS#
component:$AWAS$$example/awas-lang/isolette_model.awas#Nurse:
#PORTS#
port-in:$AWAS$$example/awas-lang/isolette_model.awas$Nurse#monitor
port-out:$AWAS$$example/awas-lang/isolette_model.awas$Nurse#set_params

#PROPAGATION
port-in:$AWAS$$example/awas-lang/isolette_model.awas$Nurse#monitor:
#ERRORS#
port-out:$AWAS$$example/awas-lang/isolette_model.awas$Nurse#set_params:
#ERRORS#
component:$AWAS$$example/awas-lang/isolette_model.awas#OperatorInterface:
#PORTS#
port-in:$AWAS$$example/awas-lang/isolette_model.awas$OperatorInterface#feedback_in
port-in:$AWAS$$example/awas-lang/isolette_model.awas$OperatorInterface#settings_in
port-out:$AWAS$$example/awas-lang/isolette_model.awas$OperatorInterface#feedback_out
port-out:$AWAS$$example/awas-lang/isolette_model.awas$OperatorInterface#settings_out

#PROPAGATION
port-in:$AWAS$$example/awas-lang/isolette_model.awas$OperatorInterface#feedback_in:
#ERRORS#
port-out:$AWAS$$example/awas-lang/isolette_model.awas$OperatorInterface#feedback_out:
#ERRORS#
port-out:$AWAS$$example/awas-lang/isolette_model.awas$OperatorInterface#settings_out:
#ERRORS#
port-in:$AWAS$$example/awas-lang/isolette_model.awas$OperatorInterface#settings_in:
#ERRORS#
component:$AWAS$$example/awas-lang/isolette_model.awas#TempSensor:
#PORTS#
port-in:$AWAS$$example/awas-lang/isolette_model.awas$TempSensor#air_heat
port-out:$AWAS$$example/awas-lang/isolette_model.awas$TempSensor#air_temp

#PROPAGATION
port-in:$AWAS$$example/awas-lang/isolette_model.awas$TempSensor#air_heat:
#ERRORS#
port-out:$AWAS$$example/awas-lang/isolette_model.awas$TempSensor#air_temp:
#ERRORS#
component:$AWAS$$example/awas-lang/isolette_model.awas#Thermostat:
#PORTS#
port-in:$AWAS$$example/awas-lang/isolette_model.awas$Thermostat#sensed_temp
port-in:$AWAS$$example/awas-lang/isolette_model.awas$Thermostat#settings
port-out:$AWAS$$example/awas-lang/isolette_model.awas$Thermostat#feedback
port-out:$AWAS$$example/awas-lang/isolette_model.awas$Thermostat#heater_ctrl

#PROPAGATION
port-in:$AWAS$$example/awas-lang/isolette_model.awas$Thermostat#settings:
#ERRORS#
port-in:$AWAS$$example/awas-lang/isolette_model.awas$Thermostat#sensed_temp:
#ERRORS#
port-out:$AWAS$$example/awas-lang/isolette_model.awas$Thermostat#heater_ctrl:
#ERRORS#
port-out:$AWAS$$example/awas-lang/isolette_model.awas$Thermostat#feedback:
#ERRORS#
#CONNECTIONS#
connection:$AWAS$$example/awas-lang/isolette_model.awas#h1
connection:$AWAS$$example/awas-lang/isolette_model.awas#h2
connection:$AWAS$$example/awas-lang/isolette_model.awas#i1
connection:$AWAS$$example/awas-lang/isolette_model.awas#i2
connection:$AWAS$$example/awas-lang/isolette_model.awas#o1
connection:$AWAS$$example/awas-lang/isolette_model.awas#o2
connection:$AWAS$$example/awas-lang/isolette_model.awas#o3
connection:$AWAS$$example/awas-lang/isolette_model.awas#o4
connection:$AWAS$$example/awas-lang/isolette_model.awas#t1
connection:$AWAS$$example/awas-lang/isolette_model.awas#t2
