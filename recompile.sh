#!/bin/bash

# Clean previous build
rm -rf build
mkdir -p build

# Step 1: Generate the lexer using JFlex
echo "Generating Lexer..."
java -jar java-cup-11b.jar -parser LepaParser -symbols sym src/parser/LepaParser.cup

# Move the generated CUP files to the parser package directory
mv -f LepaParser.java src/parser/
mv -f sym.java src/parser/

# Step 2: Compile all Java sources
echo "Compiling Java sources..."
find src -name "*.java" > sources.txt

# Create build directory
mkdir -p build/ast build/parser

# Compile all sources
javac -d build -cp .:java-cup-11b.jar @sources.txt

# Compile LepaMain separately 
javac -cp .:java-cup-11b.jar:build -d build src/LepaMain.java

# Step 3: Run for minimal.lepa
echo "Running for minimal.lepa"
java -cp .:java-cup-11b.jar:build LepaMain minimal.lepa
