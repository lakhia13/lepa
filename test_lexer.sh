#!/bin/bash

# Compile the lexer test
javac -cp ".:java-cup-11b.jar:src" src/SimpleLexerTest.java

# Run the lexer test
java -cp ".:java-cup-11b.jar:src" SimpleLexerTest
