#!/bin/bash

# Get the base directory (parent of scripts directory)
BASE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/.."
cd "$BASE_DIR"

# Compile the lexer test
javac -cp ".:java-cup-11b.jar:src" src/SimpleLexerTest.java

# Run the lexer test
java -cp ".:java-cup-11b.jar:src" SimpleLexerTest
