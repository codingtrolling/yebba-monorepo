#!/bin/sh
echo "--- YEBBA BUILD STARTING ---"
if [ ! -f "gradle/wrapper/gradle-wrapper.jar" ]; then
    echo "ERROR: gradle-wrapper.jar NOT FOUND in gradle/wrapper/"
    exit 1
fi
echo "Starting Gradle Wrapper..."
java -jar gradle/wrapper/gradle-wrapper.jar "$@"
