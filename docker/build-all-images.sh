#!/bin/bash
echo "Building all Java artifacts"
cd ..
mvn -Pdistro install -DskipTests

echo "Building REST app image"
cd modules/flowable-app-rest
mvn -Pdocker,swagger -DskipTests package

echo "Building UI app image"
cd ../flowable-ui
mvn -Pdocker -DskipTests package

echo "Done..."
