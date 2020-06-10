#!/usr/bin/env bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo $DIR
$DIR/../bin/sbt-launch.sh "project awas-js" fullOptJS::webpack
AWASWEBDIR=$DIR/jvm/src/main/resources/org/sireum/awas/AADLBridge/awas-web
rm -rf $AWASWEBDIR
mkdir $AWASWEBDIR
mkdir $AWASWEBDIR/min
cp $DIR/js/target/scala-2.12/classes/index.html $AWASWEBDIR/index.html
cp $DIR/js/target/scala-2.12/classes/risk-analysis-report.html $AWASWEBDIR/risk-analysis-report.html
cp -r $DIR/js/target/scala-2.12/classes/min/css $AWASWEBDIR/min/css
cp -r $DIR/js/target/scala-2.12/classes/min/images $AWASWEBDIR/min/images
cp -r $DIR/js/target/scala-2.12/classes/min/webfonts $AWASWEBDIR/min/webfonts
cp $DIR/js/target/scala-2.12/classes/min/awas-opt-bundle.js $AWASWEBDIR/min/awas-opt-bundle.js
cp $DIR/js/target/scala-2.12/classes/min/awas-opt-loader.js $AWASWEBDIR/min/awas-opt-loader.js
cp $DIR/js/target/scala-2.12/classes/min/awas-opt-entrypoint.js $AWASWEBDIR/min/awas-opt-entrypoint.js
cp $DIR/js/target/scala-2.12/classes/min/awas-jsdeps.min.js $AWASWEBDIR/min/awas-jsdeps.min.js

rm -rf $DIR/jvm/src/main/resources/org/sireum/awas/AADLBridge/awas-web.zip
cd $AWASWEBDIR/..
zip -9r $DIR/jvm/src/main/resources/org/sireum/awas/AADLBridge/awas-web.zip ./awas-web
rm -rf $AWASWEBDIR
$DIR/../bin/sbt-launch.sh "project awasJar" assembly
echo 'awas.jar generated at '$( cd "$( dirname "$DIR" )"/awasJar/target/scala-2.12/ && pwd)