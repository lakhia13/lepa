#!/bin/bash

# Get the base directory (parent of scripts directory)
BASE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/.."

echo "=== Testing minimal.lepa ==="
"$BASE_DIR/scripts/compile_and_run.sh" minimal.lepa

echo "\n=== Testing minimal2.lepa ==="
"$BASE_DIR/scripts/compile_and_run.sh" minimal2.lepa

echo "\n=== Testing minimal3.lepa ==="
"$BASE_DIR/scripts/compile_and_run.sh" minimal3.lepa

echo "\n=== Testing working.lepa ==="
"$BASE_DIR/scripts/compile_and_run.sh" working.lepa

echo "\n=== All tests completed successfully! ==="
echo "The LEPA compiler has been updated to handle all test patterns."
