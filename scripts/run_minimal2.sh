#!/bin/bash

# Get the base directory (parent of scripts directory)
BASE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/.."
cd "$BASE_DIR"

echo "Running LEPA compiler with minimal2.lepa..."
java -cp .:java-cup-11b.jar:build LepaMain sample_lepa/minimal2.lepa
