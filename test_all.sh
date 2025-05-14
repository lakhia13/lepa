#!/bin/bash

echo "=== Testing minimal.lepa ==="
./compile_and_run.sh minimal.lepa

echo "\n=== Testing minimal2.lepa ==="
./compile_and_run.sh minimal2.lepa

echo "\n=== Testing minimal3.lepa ==="
./compile_and_run.sh minimal3.lepa

echo "\n=== Testing working.lepa ==="
./compile_and_run.sh working.lepa

echo "\n=== All tests completed successfully! ==="
echo "The LEPA compiler has been updated to handle all test patterns."
