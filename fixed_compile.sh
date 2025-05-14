#!/bin/bash

# Use existing parser files instead of trying to regenerate them
echo "Compiling Java sources..."

# Create build directory
mkdir -p build/ast build/parser build/runtime

# Copy parser files if they are missing
if [ ! -f "src/parser/LepaParser.java" ]; then
    echo "Parser files missing, cannot continue"
    exit 1
fi

# Collect all java files
find src -name "*.java" > sources.txt

# Compile all at once
javac -d build -cp .:java-cup-11b.jar @sources.txt

# Run one file as a test
echo "\n=== Testing minimal.lepa ==="
java -cp .:java-cup-11b.jar:build LepaMain minimal.lepa

echo "\n=== Testing minimal2.lepa ==="
java -cp .:java-cup-11b.jar:build LepaMain minimal2.lepa
