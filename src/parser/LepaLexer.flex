/* LepaLexer.flex - JFlex specification for the LEPA language */
package parser;

import java_cup.runtime.Symbol;

%%

%class LepaLexer
%unicode
%public
%cup

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
"proof"       { return new Symbol(sym.PROOF, yytext()); }
"qed"         { return new Symbol(sym.QED, yytext()); }
"assume"      { return new Symbol(sym.ASSUME, yytext()); }
"end"         { return new Symbol(sym.END, yytext()); }
"let"         { return new Symbol(sym.LET, yytext()); }
"in"          { return new Symbol(sym.IN, yytext()); }
"notin"       { return new Symbol(sym.NOTIN, yytext()); }
"subset"      { return new Symbol(sym.SUBSET, yytext()); }
"union"       { return new Symbol(sym.UNION, yytext()); }
"intersect"   { return new Symbol(sym.INTERSECT, yytext()); }
"forall"      { return new Symbol(sym.FORALL, yytext()); }
"exists"      { return new Symbol(sym.EXISTS, yytext()); }
"true"        { return new Symbol(sym.TRUE, yytext()); }
"false"       { return new Symbol(sym.FALSE, yytext()); }
"by"          { return new Symbol(sym.BY, yytext()); }
"from"        { return new Symbol(sym.FROM, yytext()); }
"therefore"   { return new Symbol(sym.THEREFORE, yytext()); }

// Symbols and operators
"->"          { return new Symbol(sym.IMPLIES, yytext()); }
"<->"         { return new Symbol(sym.IFF, yytext()); }
"and"         { return new Symbol(sym.AND, yytext()); }
"or"          { return new Symbol(sym.OR, yytext()); }
"not"         { return new Symbol(sym.NOT, yytext()); }
"!="          { return new Symbol(sym.NEQ, yytext()); }
"="           { return new Symbol(sym.EQ, yytext()); }
","           { return new Symbol(sym.COMMA, yytext()); }
"."           { return new Symbol(sym.DOT, yytext()); }
":"           { return new Symbol(sym.COLON, yytext()); }
"("           { return new Symbol(sym.LPAREN, yytext()); }
")"           { return new Symbol(sym.RPAREN, yytext()); }
"{"           { return new Symbol(sym.LBRACE, yytext()); }
"}"           { return new Symbol(sym.RBRACE, yytext()); }
"\\"          { return new Symbol(sym.SETDIFF, yytext()); }

// Identifiers and literals
{ID}          { return new Symbol(sym.IDENTIFIER, yytext()); }
{DIGIT}+      { return new Symbol(sym.NUMBER, Integer.parseInt(yytext())); }

// Whitespace and comments
{WHITESPACE}  { /* skip whitespace */ }
{COMMENT}     { /* skip comment */ }

.             { System.err.println("Unknown character: " + yytext()); return null; }
<<EOF>>       { return new Symbol(sym.EOF); }
