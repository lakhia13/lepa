# LEPA Language Specification

## Educational Goals and Scope

LEPA is a **Learning-Education Proof Assistant** language designed to help high school students practice formal logic and proofs in an interactive way. The language aims to bridge the gap between informal mathematical proofs and formal logic, providing immediate feedback on logical reasoning. Key goals and features include:

* **Scope of Logic Covered:** LEPA supports *propositional logic*, *first-order predicate logic*, and *basic set theory*. Students can formalize and prove statements involving logical connectives, quantifiers, and set operations that are common in high school math (like union, intersection, subset relations, etc.). The focus is on foundational logic skills, not advanced mathematics beyond sets and first-order reasoning.

* **Educational Emphasis:** The language is intentionally kept *small and simple* to lower the learning curve for beginners. Unlike professional proof assistants (Coq, Lean, etc.), which have steep learning curves and advanced features, LEPA is targeted at novices. The syntax is mathematical in style (using familiar notation in ASCII form) to make it readable and close to the notation students use on paper. This helps students focus on logical reasoning rather than programming syntax.

* **Interactive and Step-by-Step Learning:** LEPA can be used interactively via a REPL (Read-Eval-Print Loop) to check each proof step as the student writes it, providing immediate feedback and error messages. It also supports writing complete proof *scripts* that can be checked in full. This dual mode encourages learning in small steps (verifying each inference) and also allows presenting full polished proofs. The environment enforces logical correctness at each step, guiding students to correct mistakes in reasoning as they go.

* **Mathematical Style Syntax:** The language syntax is inspired by standard mathematical logic notation (using ASCII equivalents). For example, `->` denotes implication, `forall` and `exists` denote quantifiers, and set relations like `in` (∈) and `subset` (⊆) are written as keywords. This choice reinforces conventional notation and ensures proofs written in LEPA resemble textbook proofs, improving readability for students and teachers.

* **Limitations and Extensibility:** LEPA is not intended to be a general-purpose programming language or an industrial-strength theorem prover. It does not include advanced features like higher-order logic, sophisticated automation, or large mathematical libraries. However, the core design can be extended in the future – for example, adding arithmetic theories or more automation – once students grasp the basics. Initially, the emphasis is on manual proof construction and understanding each logical inference.

## Core Data Types and Expressions

LEPA manipulates a few fundamental kinds of entities: **propositions (logical statements)**, **terms (objects or values)**, and **sets**. These constitute the core data types of the language, around which expressions are formed.

* **Propositions (Statements):** A proposition (also called a *formula*) is a logical statement that can be either true or false. In LEPA, propositions are formed from **atomic formulas** (like a predicate applied to terms, or a basic relation such as equality or set membership) and combined using logical connectives. For example, `P`, `Prime(n)`, `x = 5`, and `x in A` are atomic formulas, which can be combined into complex propositions like `P and (x in A -> Prime(x))`. A proposition may also be a stand-alone boolean constant `true` or `false` (representing a tautology or contradiction, respectively).

* **Terms (Individuals and Values):** Terms represent the objects that propositions talk about. A term can be:

    * a **variable** (e.g. `x`, `y`, `n`) representing an unspecified element of the domain,
    * a **constant** or literal (e.g. `a`, `5`) representing a specific element or value, or
    * a **function application** (if user-defined functions are in use) like `f(x)`. *By default, LEPA does not predefine any function symbols beyond basic set operators, but users may use alphanumeric identifiers as function symbols for practice.* Each term has an implicit sort or type (for instance, we might distinguish between an individual element vs. a set, as described below). In an expression like `Prime(n)`, `n` is a term (here presumably a number).

* **Predicates and Atomic Formulas:** A **predicate** represents a property or relation that can hold among terms. Predicates applied to terms produce atomic propositions. For example, `Prime(n)` might be a predicate indicating "n is prime," and `Subset(A, B)` could be a predicate indicating set A is a subset of set B. In LEPA syntax, a predicate application is written as an identifier followed by its arguments in parentheses, like `Predicate(term1, term2)`. A predicate can also have zero arguments – effectively a Boolean variable or constant – written simply as an identifier (e.g. `P` could be an atomic proposition with no arguments). Predicates can be user-defined by simply using a new name in a formula; they do not need explicit declaration. The truth of such predicates is either assumed or derived within proofs. Two special built-in predicates are used for ubiquitous relations:

    * **Equality:** `=` is the equality relation. An atomic formula `t1 = t2` asserts that two terms are equal. Inequality can be written as the negation `not (t1 = t2)` or by the shorthand operator `!=` (not equal to).
    * **Set Membership:** `in` is used as an infix predicate for set membership. An atomic formula `x in A` asserts that element `x` is a member of set `A`. Correspondingly, `not (x in A)` or the shorthand `x notin A` asserts that x is not an element of A.

* **Sets and Set Expressions:** *Sets* are a fundamental data type in LEPA to support basic set theory reasoning. A set can be thought of as a collection of elements (terms). In LEPA, sets are typically denoted by uppercase identifiers (e.g. `A`, `B`, `S`) or by set literals. Core set expressions and operations include:

    * **Set Literals:** A set can be written explicitly by listing its elements in curly braces. For example, `{1,2,3}` is a set of three numbers, and `{ }` (or `emptyset`) represents the empty set ∅. Elements in a set literal are terms. E.g., one could write `{a, b, c}` to denote a set containing elements `a, b, c`.
    * **Set Membership:** As mentioned, `x in A` and `x notin A` assert membership or non-membership. This is the primary way to relate individual terms and sets.
    * **Subset and Superset:** `subset` is a reserved keyword indicating the subset relation (⊆). An expression `A subset B` means every element of set A is also an element of set B. (Semantically this is ∀x (x∈A -> x∈B).) There is no separate symbol for superset; `A subset B` is logically equivalent to “B is a superset of A”. If needed, a `superset` keyword could be introduced, but typically users can just swap arguments.
    * **Union and Intersection:** The infix operators `union` and `intersect` create new set terms from existing sets. For example, `A union B` represents the union \$A ∪ B\$ (all elements in A or B), and `A intersect B` represents the intersection \$A ∩ B\$ (elements common to both). These are binary operators on sets. They obey the usual algebraic laws (associativity, commutativity, etc.) and can be used to build set expressions.
    * **Set Difference:** A backslash `\` is used for set difference. The term `A \ B` denotes \$A \setminus B\$: the set of elements in A that are not in B. For example, if \$U\$ is a universal set, `U \ A` can represent the complement of A relative to \$U\`. (LEPA does not have a special unary operator for complement; set difference with an implicit universal set can serve that role if needed.)
    * **Set Comprehension (Optional):** For more advanced usage, LEPA allows set comprehension (set-builder notation) in ASCII form. A comprehension looks like `{x | P(x)}`, meaning "the set of all x such that P(x) holds". For example, `{ n | Prime(n) }` could denote the set of all prime numbers (assuming the domain includes natural numbers and `Prime(n)` is defined). This syntax uses a vertical bar `|` to separate the dummy variable(s) from the condition. Comprehension is primarily for expressing concepts; proofs about them would typically need to use the definition (e.g. \$x ∈ {x | P(x)}\$ is equivalent to \$P(x)\$).

**Typing and Conventions:** LEPA is essentially an untyped first-order logic, but it is useful to distinguish between *element terms* and *set terms* by convention. For clarity, we often use **lowercase** identifiers (e.g. `a, b, x, y, n`) for individual elements, and **uppercase** identifiers (e.g. `A, B, X, Y`) for sets. This is just a convention to improve readability; the language grammar does not enforce different namespaces strictly. The context of use typically disambiguates the role (for instance, in `x in A`, `x` must be an element and `A` a set). If an identifier is used in a position where a proposition is expected (e.g. as a stand-alone step in a proof), it is interpreted as an atomic proposition (0-ary predicate) rather than a term. For example, one could use `P` or `Q` as names of propositions (with no arguments) in propositional logic. Users should avoid reusing the same name for both a set and a propositional variable in the same context to prevent confusion.

* **Logical Connectives:** LEPA supports the standard logical operators for negation, conjunction, disjunction, implication, and equivalence:

    * **Negation:** `not Φ` is the negation of proposition Φ (¬Φ). It can be placed in front of any formula to negate it. For convenience in proofs, `~` is an accepted synonym for `not`. (E.g., `~P` means ¬P.)
    * **Conjunction (AND):** `Φ and Ψ` is the conjunction (logical AND) of Φ and Ψ (Φ ∧ Ψ). The word `and` is a reserved keyword in the language. *Example:* `Even(n) and Prime(n)` is true if and only if both Even(n) and Prime(n) hold.
    * **Disjunction (OR):** `Φ or Ψ` is the disjunction (logical OR) of Φ and Ψ (Φ ∨ Ψ). The word `or` is reserved. *Example:* `x in A or x in B` is true if x is in at least one of A or B.
    * **Implication:** `Φ -> Ψ` denotes logical implication (Φ ⇒ Ψ). This is read as "if Φ then Ψ". The arrow is a two-character ASCII sequence `->` and is treated as a single binary connective with lower precedence than `and`/`or`. *Example:* `P and Q -> R` means (P ∧ Q) ⇒ R. (Parentheses may be needed to clarify grouping, since `->` is right-associative by default.)
    * **Biconditional (If-and-Only-If):** `Φ <-> Ψ` denotes logical equivalence (Φ ⇔ Ψ), true when Φ and Ψ have the same truth value. The `<->` operator (sometimes called “iff”) is a binary connective of the lowest precedence (same level as implication). *Example:* `Even(n) <-> not(Odd(n))` asserts that n is even if and only if n is not odd.

  These connectives follow the usual precedence rules of logic: `not` binds the tightest, `and` next, then `or`, and finally `->` and `<->` bind the most loosely. In other words, in the absence of parentheses, `not` applies to only the formula immediately following it, `and` and `or` group before an implication does, and implications and biconditionals are evaluated last. For example, `not P or Q` is parsed as `(not P) or Q`, and `A -> B or C` is parsed as `A -> (B or C)`. Parentheses can always be used to override default precedence and make the intended grouping explicit.

* **Quantifiers:** LEPA supports universal and existential quantification for first-order logic:

    * **Universal Quantifier:** `forall x. Φ` means ∀x Φ, i.e. Φ holds for all values of x. Multiple variables can be quantified in one statement: `forall x, y. Φ(x, y)` is equivalent to ∀x ∀y Φ(x,y). One can also restrict the domain with an `in` clause: `forall x in S. Φ(x)` means ∀x (x∈S → Φ(x)), i.e. for all x in set S, property Φ(x) holds. For example, `forall n in N. Prime(n) -> n > 1` asserts every prime number is greater than 1. In the absence of an explicit domain, the `forall` ranges over the implicit universal domain of discourse (which might be all individuals under consideration).
    * **Existential Quantifier:** `exists x. Φ` means ∃x Φ, i.e. there exists at least one x such that Φ holds. Similarly it can quantify multiple variables or have a domain restriction: `exists x, y in A. P(x,y)` means ∃x∈A ∃y∈A P(x,y). An example without restriction: `exists k. k * k = n` (there exists some k such that k squared equals n). If a domain is specified, e.g. `exists x in A. Φ(x)`, it is semantically ∃x (x∈A ∧ Φ(x)). In LEPA syntax, `exists` is a keyword and the dot `.` separates the variable list from the formula.

  Variables introduced by quantifiers are considered *bound* in the scope of the quantifier. Outside the quantifier, they have no meaning. The language expects that any free variable (one not under a quantifier or introduced as an assumption parameter) is intended to be universally quantified at the top level (for example, a theorem statement `P(x) -> Q(x)` with `x` free is implicitly understood as ∀x (P(x) -> Q(x)) if that's the intent). It is good practice to explicitly quantify variables in theorem statements to avoid ambiguity.

## Syntax and Grammar

The syntax of LEPA is defined formally by the following grammar (using an EBNF-like notation). The grammar is organized into lexical units, expressions (terms and formulas), and proof script structure. **Terminal symbols** (literal keywords or characters) are enclosed in quotes. Non-terminals are written in angle brackets or CapitalizedWords. Alternatives are separated by `|`, and braces `{ }` denote repetition (zero or more), while `[ ]` denotes an optional part.  Comments (not part of EBNF) are included inline for clarity.

### Lexical Structure

LEPA programs consist of a sequence of tokens. Whitespace (spaces, newlines, tabs) is generally ignored except as a separator. Comments can be written with a leading `//` (double slash) for single-line comments; the text from `//` to the end of line is ignored by the parser.

* **Identifiers:** An `Identifier` in LEPA is a sequence of letters and/or digits (and `_` underscores), starting with a letter. There are two subcategories:

    * **Variable/Constant names:** Typically lowercase (e.g. `x, n, a`) or uppercase (e.g. `A, B, N`) not matching any reserved keyword. These can serve as term variables, term constants, or 0-ary predicate symbols depending on context.
    * **Predicate/Function symbols:** By convention, predicate names often start with an uppercase letter (e.g. `Even, Prime`) or are written in words (e.g. `Divides`). Function symbols (if used) might be lowercase (e.g. `f, succ`). In syntax, they look the same as identifiers; whether an identifier represents a function/predicate or an object is determined by how it’s used (followed by parentheses for arguments = predicate/function, otherwise an object).
* **Numbers:** A `Number` is a sequence of digits (`0-9`) and is treated as a numeric constant term. (e.g. `0`, `42`).
* **Symbols and Keywords:** The following character sequences are reserved or have special meaning and cannot be used as identifiers:

    * Logical connectives: `and`, `or`, `not`, `->`, `<->`
    * Quantifiers: `forall`, `exists`
    * Set and relation symbols: `in`, `notin`, `subset`, `union`, `intersect`, `\` (backslash for set difference)
    * Proof structure keywords: `theorem`, `proof`, `qed`, `assume`, `end`, `therefore` (and `then` or `hence` as synonyms for `therefore`, if desired)
    * Others: `true`, `false` (truth constants).

  These keywords are case-sensitive (all are lowercase in LEPA) and should not be used as variable or predicate names. For example, `and` is a reserved word, so a user cannot define a predicate named `and`.

### Grammar for Terms and Set Expressions

Below is the grammar for well-formed *terms* in LEPA, including set expressions. We distinguish *ElementTerm* (which denotes an individual element) and *SetTerm* (which denotes a set of elements) for clarity, although both categories are subtypes of the general Term in the language. In practice the non-terminals often overlap (an `Identifier` could be an element or set depending on context).

```ebnf
<Number>       ::= ( '0' | ... | '9' ) { ( '0' | ... | '9' ) }    // numeric literal

<Identifier>   ::= ( 'A' | ... | 'Z' | 'a' | ... | 'z' ) { 'A' | ... | 'Z' | 'a' | ... | 'z' | '0' | ... | '9' | '_' }

<ElementTerm>  ::= <Identifier> | <Number> | <Identifier> "(" [ <Term> { "," <Term> } ] ")" 
                  | "{" <ElementTermList> "}" 
                  // An Identifier by itself could be a variable or constant denoting an element.
                  // Identifier(...) is a function or constructor applied to arguments (returning an element).
                  // { ElementTermList } is a set literal, but note: a set literal is a SetTerm; if used in an ElementTerm context, it should denote an element (so likely avoid using a set literal where an element is expected).

<SetTerm>      ::= <Identifier> | <Identifier> "(" [ <Term> { "," <Term> } ] ")" 
                  | "{" <ElementTermList> "}" 
                  | "{" <Binder> "|" <Formula> "}" 
                  | <SetTerm> "union" <SetTerm> 
                  | <SetTerm> "intersect" <SetTerm> 
                  | <SetTerm> "\" <SetTerm>
                  // A plain Identifier could represent a set (e.g., A, B). 
                  // Set comprehension: { x | Formula } yields a set of all x satisfying the formula.
                  // The operators union, intersect, \ are infix left-associative operators on sets.

<ElementTermList> ::= <ElementTerm> { "," <ElementTerm> } 
                  // one or more terms separated by commas

<Binder>       ::= <Identifier> [ "in" <Term> ]
                  // Used in set comprehension to optionally restrict the domain of the bound variable.
                  // E.g., { x in A | P(x) } means all x in A such that P(x).
                  
<Term>         ::= <ElementTerm> | <SetTerm>
                  // In general, any Term is either an element-type term or a set-type term.
```

**Notes on the Term Grammar:**

* An `<Identifier>( ... )` can denote either a function or a predicate application. In a term context, it denotes a function returning a term. For example, if `f` is a function symbol, `f(x)` is a term. If used in a formula context (see below), `P(x)` might represent a predicate. The parser may need some disambiguation (often based on context or predefined symbols). By default, if an identifier appears where a term is expected, it's treated as a term (constant or variable). If it appears directly in a formula context (not as part of a larger term or preceded by a quantifier or connective), it is treated as a propositional constant (0-ary predicate). For user simplicity, LEPA does not require declarations of constants vs. functions vs. predicates; context is used to resolve the role.

* A set literal `{ t1, t2, ... }` produces a set containing the listed elements. The list can be empty `{}` meaning the empty set. Note that the grammar above shows set literal under `<SetTerm>` and `<ElementTerm>` for completeness, but a set literal should be used as a set term. (If one writes something like `x = {a,b}`, the right side `{a,b}` is a SetTerm, and the left `x` would need to be a SetTerm as well – implying `x` is a set equal to `{a,b}`.)

* The *set comprehension* `{ x | Φ(x) }` introduces a new (bound) variable `x` and collects all `x` satisfying formula Φ(x). A variant with a domain restriction `{ x in S | Φ(x) }` is also allowed, meaning all x in set S that satisfy Φ(x). Comprehensions are mainly for writing specifications; to prove something about an element of a comprehension, one would unfold the definition (e.g. show `y in {x|Φ(x)}` is equivalent to `Φ(y)`). For educational use, comprehension might be an optional feature, toggled on if the class has covered that concept.

* **Precedence of Set Operators:** `\` (set difference) and `intersect` bind more tightly than `union`. They are left-associative. For example, `A \ B union C` is parsed as `(A \ B) union C`. If needed, parentheses can group differently (e.g. `A \ (B union C)`). The `union` and `intersect` have the same precedence relative to each other (both lower than `\` if we treat `\` similar to intersection in tightness). In practice, it’s safest to use parentheses in complex set expressions to avoid any ambiguity.

### Grammar for Formulas (Propositions)

The following grammar defines well-formed formulas in LEPA. It builds on the term grammar above for atomic formulas and then uses the logical connective keywords. We use a recursive precedence structure to avoid ambiguity (similar to how arithmetic or C-like operator precedence is handled).

```ebnf
<AtomicFormula> ::= <Identifier> [ "(" [ <Term> { "," <Term> } ] ")" ] 
                    | <Term> "=" <Term> 
                    | <Term> "!=" <Term> 
                    | <Term> "in" <Term> 
                    | <Term> "notin" <Term> 
                    | <Term> "subset" <Term>
                    | "true" 
                    | "false"
   // Cases: 
   // Identifier(...) = predicate applied to terms (if no args in parentheses, it is 0-ary predicate constant).
   // Identifier by itself (no parentheses) is also allowed and represents a propositional variable.
   // t1 = t2 (equality), t1 != t2 (inequality), t in S (membership), t notin S (not membership), A subset B.
   // true, false are boolean literals.

<Formula> ::= <ImplicationFormula>   // final stage (we treat implication and equivalence last)
<ImplicationFormula> ::= <OrFormula> [ "->" <ImplicationFormula> 
                                      | "<->" <ImplicationFormula> ]
   // right-associative for ->, and <-> is non-associative (you should parenthesize if chaining multiple biconditionals).
   // We allow chaining "->" via right recursion: e.g. P -> Q -> R parses as P -> (Q -> R).

<OrFormula>  ::= <AndFormula> { "or" <AndFormula> }
<AndFormula> ::= <NegationFormula> { "and" <NegationFormula> }
<NegationFormula> ::= [ "not" ] <UnitFormula>
<UnitFormula> ::= <AtomicFormula> 
               | "(" <Formula> ")" 
               | "forall" <VarList> "." <Formula> 
               | "exists" <VarList> "." <Formula> 

<VarList> ::= <Identifier> { "," <Identifier> [ "in" <Term> ] } [ "in" <Term> ]
   // One or more variables, optionally each can have an "in <Term>" to restrict its domain.
   // e.g. x, y in A, z . P(x,y,z) 
   // or just x,y,z . P(x,y,z) 
   // or x in A, y in B . P(x,y)

```

**Explanation of Formula Grammar:**

* An `<AtomicFormula>` can be:

    * A predicate or propositional symbol: e.g. `P`, `Q(x,y)`, `Even(n)`. If the identifier is followed by a parenthesized list of terms, it denotes a predicate with those arguments. If it’s just a lone identifier (no parentheses), it’s a propositional constant (0-ary predicate). This allows for propositional logic formulas like `P or Q` where P and Q stand for whole statements.
    * A built-in relation:

        * `t1 = t2` or `t1 != t2` for equality and inequality of terms. `!=` is syntactic sugar for `not (t1 = t2)` (unequal).
        * `t in S` and `t notin S` for set membership and non-membership. The latter `notin` is treated as a single keyword.
        * `A subset B` for subset relation (A ⊆ B). This is an atomic formula asserting every element of A is in B.
    * The constants `true` and `false`, which are propositions that are always true and always false, respectively. `false` can be used to represent a contradiction (⊥) in proofs, and `true` (⊤) for a tautology if needed.

* **Logical Operators:** The grammar uses a standard precedence approach:

    * `<NegationFormula>` handles an optional `"not"` prefix on a unit. Multiple negations would nest (e.g. `not not P` is allowed).
    * `<AndFormula>` allows one or more negation-units joined by `"and"`. The `{ ... }` means you can have a chain `A and B and C` which is left-associative.
    * `<OrFormula>` similarly allows a chain of `"or"`, also left-associative. `and` has higher precedence than `or`, so `A and B or C` parses as `(A and B) or C` by this grammar.
    * `<ImplicationFormula>` deals with `->` and `<->`. We allow an implication or equivalence to the right. `->` is considered right-associative: for example `P -> Q -> R` is parsed as `P -> (Q -> R)`. The biconditional `<->` (iff) is not associative; in practice one should not chain `<->` without parentheses. The grammar as written would parse `A <-> B <-> C` as `A <-> (B <-> C)` due to right recursion, but it's recommended to add parentheses or break it into two statements, as equivalence is logically associative only in a bi-conditional sense. In summary, `not` > `and` > `or` > `->`/`<->` in precedence.

* **Quantified formulas:** `forall` and `exists` are handled by `<UnitFormula>` alternatives (they have lower precedence than any binary connective, since the grammar places them at the level of a parenthesized unit or atomic formula). A quantifier keyword must be followed by a `<VarList>`, a dot `.` (period), and then a formula that is the scope of the quantifier. The `<VarList>` can contain one or more variables, optionally each with a domain restriction:

    * e.g. `forall x,y. Φ` means ∀x ∀y Φ.
    * `forall x in A, y in B. Φ(x,y)` means ∀x∈A ∀y∈B Φ(x,y).
    * `exists n. Ψ(n)` means ∃n Ψ(n).
    * If only one variable is present, you still use a dot: `exists x. P(x)`.

  Quantifiers bind tighter than any implication or equivalence, so `forall x. P(x) -> Q` is parsed as `(forall x. P(x)) -> Q` (if you meant `forall x. (P(x) -> Q)`, you need parentheses).

* **Grouping:** Parentheses `(` `)` can be used around any subformula to group it explicitly as a `<UnitFormula>`. This is often necessary to override precedence or to make complex expressions clear.

* **Free vs Bound Variables:** In a well-formed formula, any variable not introduced by a quantifier (or by an assumption in a proof context) is considered free. Free variables in a top-level theorem statement are usually understood to be universally quantified in that statement or are parameters that the theorem is meant to hold for. In a proof script, it’s best to avoid using variables that haven’t been quantified or introduced, except as *parameters* for the theorem being proven (the context of the theorem can treat them as implicit universal parameters, similar to how one writes "Let x be arbitrary..."). The proof checker will typically flag the use of a variable that has not been introduced by either a quantifier or an assumption, to prevent accidentally using an unintended free variable.

### Proof Structure and Scripts

A LEPA program (script) consists of a sequence of **theorem declarations** and optional commands. The typical structure for proving a theorem is:

```
theorem <Name>: <Formula>.
proof:
    <proof steps...>
qed.
```

Each theorem introduces a formula to prove, followed by a proof block. The proof can contain subproofs (for assumptions). The language can also be used interactively, where a user at the REPL might issue commands like starting a proof, making an assumption, etc., one step at a time. Here we outline the syntax for structured proofs in script form:

```ebnf
<TheoremDecl> ::= "theorem" <Identifier> ":" <Formula> "."    // declare a theorem with a name and the statement to prove

<ProofBlock> ::= "proof" ":" <ProofStepSeq> "qed" "."         // proof block starts with "proof:" and ends with "qed."

<ProofStepSeq> ::= { <ProofStep> }

<ProofStep> ::= [ <Label> ":" ] ( <AssumeStmt> | <InferenceStmt> | <ConclusionStmt> )

<AssumeStmt> ::= "assume" <Formula>                          // begin a subproof with an assumed formula
<EndStmt>    ::= "end"                                       // end the most recent assumption subproof
                 // Alternatively, we allow "end assume" for clarity, equivalent to "end".
                 
<InferenceStmt> ::= <Formula> [ "by" <Justification> ]       // an inferred step in the proof with optional justification
<ConclusionStmt> ::= "therefore" <Formula> [ "by" <Justification> ]
                 // "therefore" is optional syntax to mark a derived result, often used when closing a subproof or finishing the proof.

<Justification> ::= <RuleName> [ "from" <LabelList> ]
<LabelList> ::= <Label> { "," <Label> }
<Label> ::= <Number> 
          // (We treat labels as line numbers for reference. Optionally, users could label lines with names, but simplest is numeric labels.)
```

**Proof Structure Explanation:**

* A theorem declaration gives the theorem a name (so it can be referenced later) and the statement (formula) to prove. The name is optional in practice (one could write `theorem:` without a name to prove an unnamed goal), but naming is useful for referencing the result or for clarity.

* The `proof:` introduces the proof block. The proof is a sequence of steps. Each step may be an assumption, an inference, or a conclusion. Each step can optionally begin with an explicit label (like `1:` or `step1:`). If no explicit labels are given, the proof checker will number the steps implicitly (starting at 1) for reference. Labels (especially numeric) are primarily used to justify later steps by referring to earlier steps.

* **Assume Statement (`assume Φ`):** This starts a *subproof* under the assumption Φ. In natural deduction terms, we are temporarily assuming Φ to derive consequences, usually to later discharge the assumption (for example, to prove an implication or to derive a contradiction). An `assume` introduces a new scope. All steps indented under this assumption (or until an explicit `end`) are within the scope where Φ is assumed true. The assumption is considered a given (premise) in that sub-context, so it can be used freely in inferences until the assumption is closed.

  In an interactive setting, one might write `assume P -> Q.` to assume an implication as a premise, or just `assume P.` to assume P. In a proof script, we put it as a separate line. There is no need to label an assume line (though you could, e.g., `3: assume P.`) since it's clear it starts a new subproof.

* **End of Assumption (`end`):** This closes the most recent open assumption. It marks the end of the subproof that began with the last unmatched `assume`. When an assumption is closed, typically a *conclusion statement* follows to use the result of the subproof. For instance, if you assumed Φ to prove Ψ, after `end` you can conclude `Φ -> Ψ` in the main context (implication introduction rule). LEPA allows the keyword `end` by itself. For clarity, the parser also permits `end assume` (two keywords) as a synonym to indicate exactly what is ending, especially if multiple kinds of blocks might be introduced in extended features. (In core LEPA, only assumption blocks need an `end`.)

* **Inference Statement:** This is a regular proof line where you derive a new formula from previous ones. You simply write the formula of the new step, followed optionally by a justification. For example, one might write: `x in B. by And-Elim from 2.` meaning “$x \in B$ is derived by an ∧-elimination rule from line 2.” If no justification is provided, the proof checker will attempt to automatically justify it by some basic rule or known fact, and if it cannot, it will produce an error or request a justification. Typically in educational use, students are expected to provide at least a brief justification in comments or by naming the rule (to develop good practice), even if the software could find it. The `by ...` clause can contain:

    * the name of a rule or theorem (e.g. `And-Intro`, `ModusPonens`, `Lemma1`) and
    * optionally a list of references after `from` indicating which previous lines or assumptions are used.

  The line references in `from ...` correspond to the labels or numbers of prior steps. If the justification refers to an earlier proven theorem, just the theorem name can be given (and the system will check that theorem from its library). For example: `by Lemma1` could mean "by a previously proven Lemma1".

  *Examples of inference steps:*

    * `5: P. by And-Elim from 3.` – uses line 3 (which might be `P and Q`) to derive `P`.
    * `6: contradiction. by Or-Elim from 4,2.` – perhaps line 4 was `A or B` and line 2 and the current subproof have ¬A and ¬B, leading to a contradiction (False). The word `contradiction` could be used as a special formula or shorthand for `false`.
    * `7: Q(x) -> P(x). by ImpIntro from 2-6.` – indicates that from the subproof starting at line 2 and ending at 6, we conclude an implication. (This is another way to cite a range of lines.)

* **Conclusion Statement (`therefore Φ` or `thus Φ`):** This is syntactically similar to an inference statement, but is used to signal that Φ is a significant derived result – often the end result of a subproof or the final theorem goal. You might use `therefore` when closing a proof to state the theorem has been proven, or right after ending an assumption to state the implication you achieved. The keyword `therefore` (and synonyms like `hence`, `thus`, which could be recognized as well) is optional – it does not change the logical meaning, but improves readability. For instance:

    * After closing an assumption for proof by contradiction: `therefore not P.` – concluding ¬P after deriving a contradiction under assumption P.
    * At the very end of a proof before `qed`: `therefore P -> Q.` – making it explicit that this was the goal.

  A `therefore` line can also include a justification like any inference (e.g. `therefore X. by Contradiction from 5.` meaning X was concluded because we reached a contradiction at line 5).

* **Justification format:** The `<Justification>` in a proof step is meant to encourage students to connect the step to a rule or prior facts. `<RuleName>` could be names like:

    * Standard logic rules: *And-Intro*, *And-Elim*, *Or-Intro*, *Or-Elim*, *Imp-Intro* (implication introduction), *Imp-Elim* (modus ponens), *Negation-Intro* (proof by contradiction), *Negation-Elim*, *Universal-Intro*, *Universal-Elim*, *Existential-Intro*, *Existential-Elim*, etc. These correspond to introduction and elimination rules for connectives and quantifiers in natural deduction.
    * Derived rules or strategies: *ModusPonens* (same as Imp-Elim), *Contradiction* (for deriving false or using false to derive something), *CaseAnalysis* (if using Or elimination), etc.
    * Theorem/lemma names: any previously proven result can serve as a justification (the proof engine will attempt to use it as needed).

  The language does not enforce a fixed set of rule names – they are treated somewhat like comments or hints to the proof checker – but it recognizes common ones. For educational use, the proof engine expects the justification to match an allowed inference. If the student writes a justification that doesn't correspond to a valid inference from the given premises, the checker will report an error or mismatch.

* **Line labels and references:** In the grammar we allowed a `<Label>` (which we treat simply as a number for line number) before a proof step. This is optional because the system can auto-number, but including numbers in the written proof can help when writing by hand or referring to steps in explanations. In justifications, `from 1,2` refers to steps labeled 1 and 2. We also allowed a range notation like `from 2-4` as shorthand for 2,3,4 if needed (not shown explicitly in grammar, but mentioned in examples – this would be handled as a convenience by the parser or preprocessor, not a separate grammar rule). Labeling with descriptive names (like `Case1:`) could also be possible, though the base specification need not require it. The primary method is numeric references.

* **Overall Script Structure:** Outside of a theorem proof, a script could potentially contain commands like definitions or axioms. In the core LEPA design, we keep it simple: mainly sequences of theorem proofs. However, one could imagine allowing `axiom <Name>: <Formula>.` to assert a premise (which the proof engine would consider as a given truth), or `define <Symbol> := <expression>.` to introduce abbreviations. These are optional extensions not covered in detail here.

**Example of a simple proof script** (to illustrate the structure, not necessarily a separate section):

```text
theorem Example1: (P and Q) -> P.
proof:
    assume P and Q.
        P.                     by And-Elim from 1.
    end
    therefore (P and Q) -> P.  by Imp-Intro.
qed.
```

In this example: We assume `P and Q` (line 1 within the proof) in order to prove `P`. Inside the assumption, we derived `P` by And-Elimination from line 1. After ending the assumption block, we conclude `(P and Q) -> P` by implication introduction. Each step is numbered automatically (1 for the assume line, 2 for the derived P, then after the `end` the concluding line could be numbered 3). The final result matches the theorem statement, and we close with `qed.` indicating the proof is complete.

## Reserved Keywords and Symbols

For quick reference, here is a list of reserved keywords and symbol sequences in LEPA, along with their meaning or role:

* **Logical Connectives:**

    * `not` – Logical negation (¬). (Synonyms: `~` can be used in place of `not`.)
    * `and` – Logical conjunction (∧).
    * `or` – Logical disjunction (∨).
    * `->` – Implication (⇒). ASCII arrow for "implies". Right-associative.
    * `<->` – Biconditional (⇔). ASCII for "if and only if". Often pronounced "iff".
    * `true` – The Boolean constant for truth (⊤). Always true.
    * `false` – The Boolean constant for falsity (⊥). Always false; often used to denote a contradiction.

* **Quantifiers:**

    * `forall` – Universal quantifier (∀). Must be followed by variable(s) and a dot.
    * `exists` – Existential quantifier (∃). Must be followed by variable(s) and a dot.
    * (Also `in` can appear inside the variable list to restrict domain, but `in` is listed under set keywords below.)

* **Set Theory and Relations:**

    * `in` – Set membership predicate (∈). Used as `x in A`.
    * `notin` – Set non-membership (∉). Used as `x notin A` meaning x is not an element of A.
    * `subset` – Subset relation (⊆). Used as `A subset B` meaning A is a subset of B.
    * `union` – Set union (∪). Infix operator: `A union B` for the union of sets A and B.
    * `intersect` – Set intersection (∩). Infix operator: `A intersect B` for the intersection of A and B.
    * `\` (backslash) – Set difference. Infix operator: `A \ B` for elements in A that are not in B.
    * `emptyset` – (Optional) a keyword for the empty set ∅. Usually `{}` is used instead, but `emptyset` could be provided for clarity.

* **Equality and Inequality:**

    * `=` – Equality. `t1 = t2` means the term t1 is equal to term t2.
    * `!=` – Inequality. `t1 != t2` is the negation of equality (same as `not (t1 = t2)` or the symbol ≠).
    * (Some texts use `<>` for not equal; LEPA uses `!=` by convention from programming.)

* **Proof and Structure Keywords:**

    * `theorem` – Introduces a theorem declaration, followed by a name and statement.
    * `proof` – Begins a proof block.
    * `qed` – Ends a proof block (stands for *quod erat demonstrandum*, indicating the proof is complete).
    * `assume` – Starts an assumption (subproof) with a given formula as a premise.
    * `end` (or `end assume`) – Closes the last open assumption subproof.
    * `therefore` – Used to mark a derived result, typically at the end of a subproof or the end of the whole proof. (Synonyms that might be recognized: `thus`, `hence`.)
    * `by` – Introduces a justification for a proof step.
    * `from` – Used after a rule name in a justification to cite previous lines.
    * (Additionally, specific rule names like `And-Intro`, `ModusPonens`, etc., while not "keywords" of the core language, are recognized strings in the `by ...` justification context. They should not be used as identifiers either to avoid confusion.)

* **Others:**

    * `//` – Introduces a comment (everything after `//` on that line is ignored by the parser).
    * Parentheses `(` `)` for grouping and for function/predicate arguments.
    * Braces `{` `}` for set literals and comprehensions.
    * Comma `,` is used to separate items in lists (like function arguments, multiple variables in quantifiers, multiple elements in set literals, or multiple line numbers in a justification).
    * Colon `:` after a label or in `forall/exists` syntax. (Note: In the theorem declaration and proof block, we use a colon after `theorem ...` and `proof` for readability; one could treat those as part of syntax or just punctuation.)

All these sequences are reserved and cannot be used for other purposes (e.g., you cannot name a variable `forall` or a set `union`). The reserved words are case-sensitive (all lowercase as written). Identifiers in user statements should avoid these words. For instance, avoid naming a constant `true` or a predicate `in`. If needed, one can vary by capitalization (like using `True` as an identifier is technically possible, since `true` in lowercase is the reserved literal, but it's better to choose a different name entirely to prevent confusion).

## Example Proofs in LEPA

Below are several example proofs written in LEPA to illustrate the language features. Each example demonstrates a different aspect: propositional reasoning, predicate logic with quantifiers, and set theory reasoning. The examples are annotated with brief explanations.

### Example 1: Propositional Logic (Implication Introduction)

*Theorem:* **If P and Q are true, then P is true.** In logical notation, prove \$(P \land Q) \to P\$. This is a simple tautology demonstrating assumption and and-elimination.

**Proof (LEPA script):**

```text
theorem AndElimExample: (P and Q) -> P.
proof:
    assume P and Q.                         // Assume the antecedent of the implication
        P.  by And-Elim from 1.             // From line 1 (P and Q), infer P (conjunction elimination)
    end
    therefore (P and Q) -> P.  by Imp-Intro. // Discharging the assumption, conclude implication
qed.
```

**Explanation:** We start by assuming `P and Q` (line 1) to prove `P`. Inside this assumption, we use the rule **And-Elim** (conjunction elimination) on line 1 to derive `P` (line 2). We then end the assumption block. At this point, we have shown that under the assumption `P and Q` we can derive `P`. By the **Imp-Intro** (implication introduction) rule, we conclude that `(P and Q) -> P` on line 3. The proof is complete with `qed`. Each step is justified: line 2 cites the rule and the line it used, and line 3 cites the implication introduction (which implicitly used lines 1-2 as the subproof).

### Example 2: Predicate Logic (Universal and Existential reasoning)

*Theorem:* **From a universal statement, we can deduce an instance; and we can prove a universal by generalizing an arbitrary instance.** As a specific case, prove that if ∀x P(x) holds, then P(c) holds for a particular constant c. Also, show a proof of a universally quantified statement by assuming an arbitrary element.

**Proof (LEPA script for ∀-elimination example):**

```text
theorem UnivElimExample: (forall x. P(x)) -> P(c).
proof:
    assume forall x. P(x).            // assume the universal premise
        P(c).  by Forall-Elim from 1. // specialize the universal statement to x = c
    end
    therefore (forall x. P(x)) -> P(c).  by Imp-Intro.
qed.
```

**Explanation:** We assume `forall x. P(x)` (line 1) – meaning P(x) is true for all x – and under that assumption, we need to show `P(c)`. We apply **Forall-Elim** (universal elimination, also known as specialization) to line 1, which gives `P(c)` on line 2, because c is a specific value for x. Closing the assumption, we conclude ` (forall x. P(x)) -> P(c)` by implication intro. This proof shows how a universally quantified fact can be used to get a specific instance.

Now, for proving a universally quantified statement (∀x P(x)), one typical approach is to let x be arbitrary and show P(x), then conclude ∀x P(x). In LEPA, this can be done by simply leaving x as an unspecified free variable in the context (which the system interprets as arbitrary) or by stating an assumption like "Let x be arbitrary" (LEPA might not have a special syntax for that, but we simulate it by not specifying x). For example, to prove `forall x in A. x in A` (which is trivially true):

```text
theorem UnivIntroExample: forall x in A. x in A.
proof:
    // We don't need to assume anything here; x is treated as an arbitrary element of A.
    let x be arbitrary in A.              // (This comment indicates our mindset; in LEPA we might just use x freely)
    x in A.  by premise.                  // Since x is arbitrary but constrained to A, this is tautologically true.
    therefore forall x in A. x in A.      // generalize to all x in A
qed.
```

*(Note: The above is a conceptual illustration. In a formal proof assistant, `forall x in A. x in A` might be proved without any steps, as it's almost by definition true. However, we show the idea of treating x generally. LEPA might allow a direct proof where if no particular element properties are used, the result is generalized.)*

### Example 3: Set Theory (Subset Proof)

*Theorem:* **\$A ∩ B ⊆ A\$.** In words, if an element is in the intersection of A and B, then it is in A. This will demonstrate reasoning with set membership and the definition of subset.

**Proof (LEPA script):**

```text
theorem SubsetExample: (A intersect B) subset A.
proof:
    assume x in (A intersect B).           // Assume an arbitrary element x of A∩B
        x in A.  by And-Elim from 1.       // From line 1, we know x in A and x in B; by definition of ∩, we get x in A
    end
    therefore (A intersect B) subset A.    // Discharge the assumption: any x in A∩B leads to x in A
qed.
```

**Explanation:** The statement `(A intersect B) subset A` is by definition ∀x (x∈A∧B → x∈A). The proof reflects that: we assume an arbitrary `x in A intersect B` (line 1). By the definition of intersection, `x in A ∧ x in B` is true. The proof picks out `x in A` from that (line 2). Once we close the assumption, we have shown an arbitrary element of A∩B must be in A. Thus we conclude `A ∩ B ⊆ A`. In the justification on line 2, we used **And-Elim** again, treating the statement “x in A and x in B” (which is the meaning of the assumption) to get the part “x in A”. The final line concludes the subset relation; the proof engine knows that concluding a subset statement is equivalent to a ∀ implication introduction (it correlates with implication intro and universal intro under the hood).

This example uses an *unstated convention* that `x` is arbitrary. We did `assume x in ...` without explicitly declaring what x is. In LEPA, this implicitly introduces a new element `x` that was not used before, which is allowed (similar to introducing a fresh variable for a ∀-intro proof). The result after the `end` is generalized to all x satisfying the condition, thus yielding the subset statement. This approach is aligned with typical mathematical proof: "Take an arbitrary element x in A∩B. ... Therefore x is in A. Since x was arbitrary, we conclude A∩B ⊆ A."

### Example 4: Proof by Contradiction

*Theorem:* **If assuming ¬P leads to a contradiction, then P must be true.** In logic, from ¬P ⇒ false, infer P. We prove: `(not P) -> false` implies `P`. (This is one direction of the law of excluded middle or a proof by contradiction principle.)

**Proof (LEPA script):**

```text
theorem ContradictionExample: (not P) -> false  ->  P.
proof:
    assume (not P) -> false.
        assume not P.                     // assume P is false
            false.  by Imp-Elim from 1,2. // from (not P)->false (line 1) and not P (line 2), derive false (contradiction)
        end
        P.  by Negation-Intro from 2-3.   // since assuming ¬P led to false (lines 2-3), infer P
    end
    therefore ((not P) -> false) -> P.    // discharge the outer assumption
qed.
```

**Explanation:** This proof showcases a nested subproof and the use of contradiction. On line 1, we assume the premise `(not P) -> false` (the supposition that ¬P implies a contradiction). We need to prove P under that. So on line 2, we assume `not P` in order to derive a contradiction. Given line 1, and our assumption line 2, we apply **Imp-Elim** (modus ponens) to conclude `false` on line 3. We close the inner assumption. By **Negation-Intro** (also known as proof by contradiction), from lines 2-3 we deduce `P` on line 4 (informally: assuming ¬P yielded a contradiction, so ¬P cannot hold, hence P must be true). We then close the outer assumption from line 1, and by implication intro, conclude the final result `( (not P)->false ) -> P` on line 5.

This pattern is the classic structure of proof by contradiction: assume the negation, derive false, conclude the negation of that assumption is false (thus the original proposition is true). LEPA’s syntax handled it by nested assume blocks. Note that we had an implication in the premise as well; we used that in the inner derivation. The ability to label and reference lines (e.g. `from 1,2`) makes it clear which assumptions were used to get the contradiction.

Each of these examples demonstrates how LEPA allows writing formal proofs in a style that is close to textbook proofs, with just enough formality to be checkable by a computer. The logical steps must be justified by basic rules or previously proven statements, which helps students learn the valid moves in logical reasoning.

## Implementation Architecture (Interpreter Design)

*(This section is optional and intended for developers of the LEPA tool rather than end-users.)*

To build an interpreter or proof checker for LEPA, a straightforward architecture is recommended. The system can be implemented as a pipeline with the following stages:

1. **Lexical Analysis (Tokenizer):** The source text (either a full script or line-by-line input in REPL) is first passed to a lexer that recognizes tokens: identifiers, numbers, keywords (`theorem`, `forall`, etc.), symbols (`->`, `(`, `)`, `{`, `}`, etc.), and punctuation (dots, commas). The lexer should also handle skipping whitespace and comments. Having a clear token specification simplifies the next stage. For example, the sequence of characters `->` should be recognized as a single token for the implication operator, not as two separate tokens.

2. **Parsing:** The sequence of tokens is parsed according to the grammar rules of the language (as specified above). A parser (which could be implemented via recursive descent, given the grammar is relatively straightforward with some care for operator precedences) will construct an **Abstract Syntax Tree (AST)** representing the structure of terms, formulas, and proof steps. The parser must handle the context-sensitive aspects:

    * Distinguishing between a term and a formula context (e.g., an identifier in a formula context with no arguments is a propositional variable, whereas in a term context it's an element or set).
    * Building the hierarchy of proof blocks (each `assume` opens a new sub-tree in the proof AST, and `end` closes it).
    * Attaching justifications to proof steps if present.
    * The parser can also assign implicit line numbers to each proof step node if not explicitly labeled.

   The result of parsing could be a structure like:

    * A list of theorem definitions, each containing an AST for the statement (a formula node) and an AST for the proof (a sequence of proof step nodes, which may have child nodes for subproofs).

3. **Semantic Analysis & Type Checking:** Although LEPA is untyped, a semantic pass can ensure certain consistency:

    * Check that in formulas, the use of a symbol is consistent (e.g., if `A` is used in a context `x in A`, then `A` is being treated as a set; if the same `A` was used as a predicate elsewhere, that’s likely an error or a different entity).
    * Check that bound variables in quantifiers or set comprehensions are not used outside their scope, and that free variables in a theorem statement are interpreted correctly (possibly issue warnings if a variable is free in the theorem statement, reminding the user that it’s implicitly universally quantified).
    * Ensure that each `end` matches a prior `assume`, forming a well-nested structure. Also ensure the proof ends with no open assumptions (except the top-level theorem context which is closed by `qed`).
    * This stage can also involve populating a symbol table for theorem names (to ensure no duplicates or to retrieve a theorem by name in proofs that use it) and maybe a simple environment to track variables.

4. **Proof Checking (Logic Engine):** This is the core component that verifies each proof step logically:

    * The engine will iterate through the proof AST. It maintains a **proof state** that includes:

        * A stack of assumption contexts (each context holds the assumptions currently in force).
        * A list of proven formulas so far in the current context (and perhaps a mapping from labels to those formulas for quick lookup by line number).
        * A global repository of proven theorems (from earlier in the script or built-in axioms) that can be used.
    * For each proof step node:

        * If it's an `assume Φ`, push a new context on the stack, add Φ to the list of current assumptions (as a given truth in that context). Optionally, check Φ for consistency (e.g., no obvious contradictions like assuming false, though assuming false is actually a technique to derive anything, but typically one wouldn't do it unless proving consistency).
        * If it's an `end`, that means we are closing an assumption block. The engine should pop the context stack. Often, at an `end`, the proof step prior to the `end` should have achieved some goal that allows discharging the assumption. For example, if the closed assumption was to prove an implication, the engine will expect that the next step after `end` will use the result of the subproof appropriately. (The *therefore* line often handles this explicitly.)
        * If it's an inference `Φ by ...` or `therefore Φ by ...`, the engine must verify that Φ logically follows from the premises:

            * If a justification is provided, use it to guide the check. For instance, if the rule is `And-Elim from i`, the engine will check that line i is of the form (Ψ and Φ) or (Φ and Ψ) and that therefore Φ is a valid result. If the rule is `Imp-Elim from i,j`, it will check line i is something like Ψ -> Φ and line j is Ψ, hence derive Φ. If it's a previously proven theorem name, it can look up that theorem’s statement and see if it matches or can be instantiated to the current situation.
            * If no justification is given, the engine can try a limited search or use a built-in logic algorithm to see if Φ is a direct consequence of some combination of current assumptions and prior lines. For educational purposes, it might simply flag it and say "justification required" to encourage the student to provide reasoning.
            * The engine should ensure that no *unsafe leaps* are taken: every new formula must be backed by either an assumption, a prior proof line plus a valid rule, or a known theorem/axiom. This prevents students from asserting something without proof.
        * If it's a conclusion with `therefore Φ`, it's typically a sign that either an assumption was just closed (so likely Φ is an implication or other statement summarizing the subproof) or it's the final goal. The engine will treat it similarly to an inference, but it might allow that if Φ is exactly the theorem’s goal and all is consistent, it passes.
    * The proof checker essentially implements the rules of natural deduction (or another chosen proof system like sequent calculus) internally. It knows the introduction and elimination rules for each connective and quantifier, and it uses them to validate each step. For example:

        * *And-Intro:* if lines p and q have formulas A and B respectively in the same context, then it can allow concluding `A and B`.
        * *And-Elim:* if a line has `A and B`, one can derive `A` (or `B` similarly).
        * *Imp-Intro:* if the proof has an open assumption A and under that assumption you derived B and then closed the assumption, you can conclude A -> B.
        * *Imp-Elim (Modus Ponens):* if line i is A -> B and line j is A (and both are available in current context), you can derive B.
        * *Negation-Intro:* if under assumption ¬A you derive false, you can conclude A (in classical logic; or conclude ¬¬A in intuitionistic, but LEPA is classical).
        * *Negation-Elim:* if you have ¬A and also A, you can derive false (contradiction).
        * *False-Elim:* if false is in context, you can derive any statement (ex falso).
        * *Or-Intro:* from A, you can infer A or B (for any B).
        * *Or-Elim:* if you have A or B, and from A you can derive C, and from B you can derive C (via subproofs), then you can conclude C.
        * *Forall-Intro:* if you have derived P(x) for a generic x (one not assuming any specific property of x except what the context allows), then you can conclude ∀x P(x). The engine checks that x was not special – i.e., it wasn’t an element introduced by an assumption or an earlier line in a way that gave it properties beyond the general case.
        * *Forall-Elim:* from ∀x P(x) you can conclude P(t) for any term t (with appropriate substitution).
        * *Exists-Intro:* from P(t) for some specific term t, you can conclude ∃x P(x).
        * *Exists-Elim:* if you have ∃x P(x), and you can assume an arbitrary witness `a` with P(a) and under that derive C, then you can conclude C (with the condition that `a` was a new element not appearing outside the subproof).
        * Set-specific reasoning isn’t separate from these rules but often relies on the definitions:

            * e.g., to use `X subset Y`, the engine might expand it to ∀x (x∈X -> x∈Y), and then apply ∀-Elim and ->-Elim as needed.
            * Or if an assumption is `x in X intersect Y`, the engine knows that means `x in X and x in Y` by definition of intersect (this could be built-in or achieved by a lemma).
            * These can be handled as rewrite rules or lemmas that are automatically applied.
    * The proof engine should also check that the final line of the proof matches the theorem statement, and that all assumptions that were opened have been properly closed and discharged in the final result. If a subproof assumption was meant to prove an implication, ensure that implication appears in the result.

5. **Interactive REPL considerations:** In REPL mode, the architecture is similar, but the state persists between commands:

    * The system maintains the current proof state. When a user types `theorem ...`, it starts a new proof context. As the user enters each proof line (`assume ...`, etc.), the system lexes, parses, and immediately tries to check that step in the current context. It then returns feedback: either accept and update the state (and maybe display the line number or the new goal state), or error if the step was invalid.
    * If the user ends the proof with `qed`, the system verifies the proof is complete. If it is, it records the theorem as proven (so it can be referenced later) and exits the proof mode back to a top-level mode.
    * The REPL should also allow examining the context (like a command to list current assumptions or known theorems), or aborting a proof attempt, etc., for user convenience.

6. **Error and Feedback:** The interpreter should provide clear error messages, especially since this is for education. Likely errors include:

    * Syntax errors: the parser should indicate if it encountered an unexpected token, perhaps pointing to a line in the script.
    * Unjustified step: if a proof line doesn’t follow from previous ones, the checker can say “Cannot infer this step from given premises. Perhaps a justification is missing or the logic is incorrect.”
    * Scope errors: e.g., using an assumption outside its scope, or referring to a line that doesn’t exist or is not in scope, etc.
    * Undischarged assumptions: if the proof ends but some `assume` wasn’t matched with an `end`, or the final theorem still has hypotheses that weren’t dealt with.
    * Free variable warnings: if a user’s theorem statement has a free variable (which means the theorem is actually a schema over that variable), the tool might warn "variable X is not quantified; the theorem will be understood as ∀X ...".

7. **Extensibility and Modules:** The architecture can be designed to easily extend the logic. For example, one might want to add:

    * **Axiom support:** allow certain truths without proof (e.g., axioms of a set theory, or given premises in homework problems). These could be stored similar to proven theorems but marked as axioms.
    * **Automation:** incorporate a simplifier or a decision procedure for certain domains. The system could automatically prove trivial steps (like arithmetic simplifications or propositional tautologies) that are not the focus of learning. However, caution must be used to not take away the learning aspect – perhaps only trivial or explicitly requested automation is provided.
    * **User-defined predicates/functions:** While already allowed syntactically, one might allow the user to attach definitions (like `define Prime(x): x > 1 and forall y (y|x -> (y = 1 or y = x)).`). The proof checker could then expand these definitions during proof or provide them as lemmas.
    * **Alternate proof styles:** Some students might prefer a more forward-chaining style (starting from premises and deriving conclusion) vs. backward (goal-directed). The environment could support tactics or backward reasoning (e.g., user could state a subgoal to prove and prove it separately). The core language (LEPA) is more aligned with forward, natural deduction style, but an interactive environment might provide backward reasoning help.

The implementation can be done in any modern programming language. The logical core (proof engine) might benefit from using existing libraries or frameworks for logical inference to reduce bugs. However, given the educational focus, a custom implementation of the logic rules (LCF-style small kernel or even pattern-matching on known rule schemas) is manageable and allows tailoring messages to students.

Testing is crucial: each rule and combination should be tested with known valid and invalid proofs to ensure the checker is neither too lax (allowing incorrect proofs) nor too strict (rejecting valid reasoning that a student might do). Ideally, the design of LEPA’s proof rules corresponds to a standard textbook natural deduction system, making it easier for teachers and students to follow along with familiar rules.

In summary, the architecture of LEPA’s interpreter consists of a clear separation between parsing the syntax and checking the logic, and it emphasizes clarity and feedback. This mirrors the language’s purpose: to clearly express and verify logical arguments, helping students learn the mechanics of proofs in a supportive environment.
