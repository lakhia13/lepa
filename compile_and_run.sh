#!/bin/bash

# This script generates the lexer and parser, compiles the sources, and runs the LEPA compiler.

# Step 1: Generate the lexer using JFlex
echo "Generating Lexer..."
jflex src/parser/LepaLexer.flex

if [ $? -ne 0 ]; then
  echo "JFlex generation failed."
  exit 1
fi

# Step 2: Generate the parser using CUP
# Assumes that java-cup.jar is in the project root directory
# Adjust the path to java-cup.jar as necessary
echo "Generating Parser..."
java -jar java-cup-11b.jar -parser LepaParser -symbols sym src/parser/LepaParser.cup

if [ $? -ne 0 ]; then
  echo "CUP parser generation failed."
  exit 1
fi

# Step 3: Compile all Java sources
# Collect all java files found under the src directory
echo "Compiling Java sources..."
find src -name "*.java" > sources.txt

# Adjust classpath if needed; including java-cup.jar in classpath
javac -cp .:java-cup-11b.jar @sources.txt

if [ $? -ne 0 ]; then
  echo "Compilation failed."
  exit 1
fi

# Step 4: Run the main class
# Provide a sample LEPA source file (e.g., sample.lepa) as an argument
# Ensure that the sample.lepa file exists in the project root or adjust the path accordingly
echo "Running LEPA compiler..."
java -cp .:java-cup-11b.jar lepa.LepaMain sample.lepa
sss