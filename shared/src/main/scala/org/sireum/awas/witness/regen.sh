#!/usr/bin/env bash
SCRIPT_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
SIREUM_HOME=$SCRIPT_HOME/../../../../../../../../..
/Users/hariharan/Documents/kekinian/kekinian/bin/sireum tools sergen -l $SIREUM_HOME/license.txt -o $SCRIPT_HOME -m json,msgpack $SCRIPT_HOME/SvgGenConfig.scala