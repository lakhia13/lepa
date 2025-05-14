#!/bin/bash

echo "Running LEPA compiler with minimal2.lepa..."
java -cp .:java-cup-11b.jar:build LepaMain minimal2.lepa
