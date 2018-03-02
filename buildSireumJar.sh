#!/usr/bin/env bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo $DIR
$DIR/../bin/sbt-launch.sh test assembly
echo 'Sireum.jar generated in '$( cd "$( dirname "$DIR" )"/bin/ && pwd)