/* LepaLexer.flex - JFlex specification for the LEPA language */
package lepa.parser;

import java_cup.runtime.Symbol;

%%

%class LepaLexer
%unicode
%public
%cup

%{
  // User code can be added here if needed
%}

DIGIT = [0-9]
LETTER = [a-zA-Z]
ID = ({LETTER}|_)( {LETTER}|{DIGIT}|_ )*

%%

"theorem" { return new Symbol(sym.THEOREM, yytext()); }
"proof"   { return new Symbol(sym.PROOF, yytext()); }
"qed"     { return new Symbol(sym.QED, yytext()); }
"assume"  { return new Symbol(sym.ASSUME, yytext()); }
"let"     { return new Symbol(sym.LET, yytext()); }
"in"      { return new Symbol(sym.IN, yytext()); }
":"       { return new Symbol(sym.SEMI, yytext());}
{ID}      { return new Symbol(sym.IDENTIFIER, yytext()); }
{DIGIT}+ { return new Symbol(sym.NUMBER, Integer.parseInt(yytext())); }
[ \t\n\r]+  { /* skip whitespace */ }
.         { System.err.println("Unknown character: " + yytext()); }
<<EOF>>   { return new Symbol(sym.EOF, "EOF"); }
