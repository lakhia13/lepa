/* LepaLexer.flex - JFlex specification for the LEPA language */
package parser;

import java_cup.runtime.Symbol;

%%

%class LepaLexer
%unicode
%public
%cup
%ignorecase

%{
  // Additional user code (if needed)
%}

DIGIT    = [0-9]
LETTER   = [a-zA-Z]
ID       = ({LETTER}|_)( {LETTER}|{DIGIT}|_ )*
WHITESPACE = [ \t\n\r]+
COMMENT = "//".*

%%

// Keywords
"theorem"     { return new Symbol(sym.THEOREM, yytext()); }
"proof"       { System.out.println("PROOF token found: " + yytext()); return new Symbol(sym.PROOF, yytext()); }
"qed"         { System.out.println("QED token found: " + yytext()); return new Symbol(sym.QED, yytext()); }
"QED"         { System.out.println("QED token found (uppercase): " + yytext()); return new Symbol(sym.QED, yytext()); }
"assume"      { System.out.println("ASSUME token found: " + yytext() + " at position " + yychar); return new Symbol(sym.ASSUME, yytext()); }
"end"         { return new Symbol(sym.END, yytext()); }
"let"         { return new Symbol(sym.LET, yytext()); }
"in"          { return new Symbol(sym.IN, yytext()); }
"notin"       { return new Symbol(sym.NOTIN, yytext()); }
"subset"      { return new Symbol(sym.SUBSET, yytext()); }
"union"       { return new Symbol(sym.UNION, yytext()); }
"intersect"   { return new Symbol(sym.INTERSECT, yytext()); }
"forall"      { return new Symbol(sym.FORALL, yytext()); }
"exists"      { return new Symbol(sym.EXISTS, yytext()); }
"true"        { System.out.println("TRUE token found: " + yytext()); return new Symbol(sym.TRUE, yytext()); }
"false"       { return new Symbol(sym.FALSE, yytext()); }
"by"          { System.out.println("BY token found: " + yytext()); return new Symbol(sym.BY, yytext()); }
"from"        { return new Symbol(sym.FROM, yytext()); }
"therefore"   { System.out.println("THEREFORE token found: " + yytext()); return new Symbol(sym.THEREFORE, yytext()); }

// Symbols and operators
// Added debug print
"->"          { return new Symbol(sym.IMPLIES, yytext()); }
"<->"         { return new Symbol(sym.IFF, yytext()); }
"and"         { return new Symbol(sym.AND, yytext()); }
"or"          { return new Symbol(sym.OR, yytext()); }
"not"         { return new Symbol(sym.NOT, yytext()); }
"!="          { return new Symbol(sym.NEQ, yytext()); }
"="           { return new Symbol(sym.EQ, yytext()); }
","           { return new Symbol(sym.COMMA, yytext()); }
"."           { System.out.println("DOT token found: " + yytext()); return new Symbol(sym.DOT, yytext()); }
":"           { System.out.println("COLON token found: " + yytext()); return new Symbol(sym.COLON, yytext()); }
"("           { return new Symbol(sym.LPAREN, yytext()); }
")"           { return new Symbol(sym.RPAREN, yytext()); }
"{"           { return new Symbol(sym.LBRACE, yytext()); }
"}"           { return new Symbol(sym.RBRACE, yytext()); }
"\\"          { return new Symbol(sym.SETDIFF, yytext()); }

// Identifiers and literals
{ID}          { System.out.println("IDENTIFIER found: " + yytext()); return new Symbol(sym.IDENTIFIER, yytext()); }
{DIGIT}+      { return new Symbol(sym.NUMBER, Integer.parseInt(yytext())); }

// Whitespace and comments
{WHITESPACE}  { /* skip whitespace */ }
{COMMENT}     { /* skip comment */ }

.             { System.err.println("Unknown character: " + yytext()); return null; }
<<EOF>>       { return new Symbol(sym.EOF); }
