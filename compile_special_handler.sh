#!/bin/bash

# Clean build directory
#rm -rf build
#mkdir -p build/ast build/parser build/runtime
#
## Compile all sources including the LepaPatternHandler
#apt update && apt install -y openjdk-11-jdk
#echo "Compiling Java sources..."
#javac -d build -cp .:java-cup-11b.jar:src:src/runtime src/LepaPatternHandler.java
#javac -d build -cp .:java-cup-11b.jar:src:src/runtime src/LepaMain.java

# Run tests with multiple files
echo "\n=== Testing minimal.lepa ==="
java -cp .:java-cup-11b.jar:build LepaMain minimal.lepa

echo "\n=== Testing minimal2.lepa ==="
java -cp .:java-cup-11b.jar:build LepaMain minimal2.lepa

echo "\n=== Testing working.lepa ==="
java -cp .:java-cup-11b.jar:build LepaMain working.lepa