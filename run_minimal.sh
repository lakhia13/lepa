#!/bin/bash

echo "=== Testing minimal.lepa (assume-therefore pattern) ==="
java PatternHandler minimal.lepa

echo "\n=== Testing minimal2.lepa (simple pattern) ==="
java PatternHandler minimal2.lepa
