#!/bin/bash

# Get the base directory (parent of scripts directory)
BASE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/.."
cd "$BASE_DIR"

echo "=== Testing minimal.lepa (assume-therefore pattern) ==="
java PatternHandler "sample_lepa/minimal.lepa"

echo "\n=== Testing minimal2.lepa (simple pattern) ==="
java PatternHandler "sample_lepa/minimal2.lepa"
