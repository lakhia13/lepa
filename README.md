# LEPA (A toy proof assistant)

A compiler and proof assistant for the LEPA language, designed to help high school students learn and practice formal logic and mathematical proofs in an interactive way.

## Overview

LEPA is an educational programming language that bridges the gap between informal mathematical proofs and formal logic. It compiles LEPA source code into executable Java programs that verify logical proofs and theorems.

### Key Features

- **Propositional and First-Order Logic**: Support for logical connectives, quantifiers, and basic set theory
- **Mathematical Syntax**: ASCII-based syntax that resembles standard mathematical notation
- **Proof Verification**: Step-by-step validation of logical reasoning
- **Set Theory Support**: Built-in operations for unions, intersections, subsets, and set membership
- **Educational Focus**: Simple, beginner-friendly syntax designed for learning

## Language Capabilities

### Logical Constructs

- **Connectives**: `and`, `or`, `not`, `->` (implication), `<->` (biconditional)
- **Quantifiers**: `forall` (universal), `exists` (existential)
- **Predicates**: User-defined predicates and relations
- **Set Operations**: `union`, `intersect`, `\` (difference), `subset`, `in`

### Syntax Example

```lepa
theorem T: true. 
proof: 
  assume true. 
  therefore true. 
qed.
```

## Project Structure

```
lepa/
├── src/                      # Main source code
│   ├── LepaMain.java        # Compiler entry point
│   ├── LepaPatternHandler.java  # Special pattern handling
│   ├── ast/                 # Abstract Syntax Tree classes
│   │   ├── Program.java
│   │   ├── TheoremDecl.java
│   │   ├── ProofStep.java
│   │   ├── Formula.java
│   │   ├── BinaryOperation.java
│   │   ├── UnaryOperation.java
│   │   ├── Quantifier.java
│   │   ├── Identifier.java
│   │   ├── BooleanLiteral.java
│   │   ├── NumberLiteral.java
│   │   ├── SetLiteral.java
│   │   └── FunctionCall.java
│   ├── parser/              # Lexer and parser
│   │   ├── LepaLexer.java
│   │   ├── LepaParser.java
│   │   └── sym.java
│   └── runtime/             # Runtime support
│       ├── LepaRuntime.java
│       └── LepaFunctions.java
├── sample_lepa/             # Example LEPA programs
│   ├── minimal.lepa
│   ├── minimal2.lepa
│   ├── minimal3.lepa
│   ├── working.lepa
│   ├── excluded_middle.lepa
│   └── simple.lepa
├── scripts/                 # Build and utility scripts
├── PatternHandler.java      # Standalone pattern handler
├── run_minimal.sh          # Test script
├── java-cup-11b.jar        # Parser generator library
└── bugfix.md               # Development notes
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Java CUP (included as `java-cup-11b.jar`)

### Building the Compiler

1. Compile all source files:
```bash
javac -cp ".:java-cup-11b.jar" @sources.txt
```

2. The compiler will generate class files in the appropriate directories.

### Running LEPA Programs

#### Using the Main Compiler

```bash
java -cp ".:java-cup-11b.jar:build" LepaMain <source-file.lepa>
```

This will:
1. Parse the LEPA source file
2. Generate Java code (`LepaProgram.java`)
3. Compile the generated Java code
4. Execute the proof verification

#### Using the Pattern Handler

For testing specific patterns:

```bash
javac PatternHandler.java
java PatternHandler <source-file.lepa>
```

Or use the provided test script:

```bash
chmod +x run_minimal.sh
./run_minimal.sh
```

### Example Usage

Given a LEPA file `theorem.lepa`:
```lepa
theorem T: true. proof: assume true. therefore true. qed.
```

Run it with:
```bash
java -cp ".:java-cup-11b.jar:build" LepaMain theorem.lepa
```

Output:
```
Parsing LEPA source file: theorem.lepa
Parsing completed successfully.
Generating Java code: LepaProgram.java
Compiling generated Java code...
Running the compiled program...
LEPA Program Execution
Verifying theorem: T
Result: true
```

## Sample Programs

The `sample_lepa/` directory contains various example programs:

- **minimal.lepa**: Basic assume-therefore pattern
- **minimal2.lepa**: Simple proof pattern
- **working.lepa**: More complex proof examples
- **excluded_middle.lepa**: Law of excluded middle demonstration
- **simple.lepa**: Introductory examples

## Compiler Architecture

### Components

1. **Lexer** (`parser/LepaLexer.java`): Tokenizes LEPA source code
2. **Parser** (`parser/LepaParser.java`): Builds Abstract Syntax Tree (AST)
3. **AST** (`ast/` directory): Represents program structure
4. **Code Generator**: Transforms AST into executable Java code
5. **Runtime** (`runtime/`): Provides support functions for proof verification

### Special Pattern Handling

The compiler includes a pattern handler (`LepaPatternHandler.java`) that provides specialized parsing for common proof patterns, improving error recovery and usability for beginners.

## Development

### Building from Source

```bash
# Compile all sources
javac -cp ".:java-cup-11b.jar" -d build @sources.txt

# Run tests
./run_minimal.sh
```

## Limitations

LEPA is an educational tool and has intentional limitations:

- Not a general-purpose programming language
- No advanced automation or tactics
- Limited to first-order logic and basic set theory
- No higher-order logic or type theory features
- Not intended for industrial-strength theorem proving


