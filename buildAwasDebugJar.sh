#!/usr/bin/env bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo $DIR
$DIR/../bin/sbt-launch.sh "project awas-js" fullOptJS
AWASWEBDIR=$DIR/jvm/src/main/resources/org/sireum/awas/AADLBridge/awas-web
rm -rf $AWASWEBDIR
mkdir $AWASWEBDIR
mkdir $AWASWEBDIR/min
cp $DIR/js/target/scala-2.12/classes/index.html $AWASWEBDIR/index.html
cp -r $DIR/js/target/scala-2.12/classes/min/css $AWASWEBDIR/min/css
cp -r $DIR/js/target/scala-2.12/classes/min/images $AWASWEBDIR/min/images
cp -r $DIR/js/target/scala-2.12/classes/min/webfonts $AWASWEBDIR/min/webfonts
cp $DIR/js/target/scala-2.12/classes/min/awas-opt.js $AWASWEBDIR/min/awas-opt.js
cp $DIR/js/target/scala-2.12/classes/min/awas-jsdeps.min.js $AWASWEBDIR/min/awas-jsdeps.min.js

rm -rf $DIR/jvm/src/main/resources/org/sireum/awas/AADLBridge/awas-web.zip
cd $AWASWEBDIR/..
zip -9r $DIR/jvm/src/main/resources/org/sireum/awas/AADLBridge/awas-web.zip ./awas-web
rm -rf $AWASWEBDIR
$DIR/../bin/sbt-launch.sh "project awasJar" assembly
echo 'awas.jar generated at '$( cd "$( dirname "$DIR" )"/awasJar/target/scala-2.12/ && pwd)