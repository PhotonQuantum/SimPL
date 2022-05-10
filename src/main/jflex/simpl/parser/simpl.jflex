package simpl.parser;

import java_cup.runtime.Symbol;

%%

%class Lexer
%unicode
%line
%column
%cup
%implements Symbols

%{
    private int commentCount = 0;
    
    private Symbol token(int tag) {
        return new Symbol(tag, yyline, yycolumn);
    }
    
    private Symbol token(int tag, Object value) {
        return new Symbol(tag, yyline, yycolumn, value);
    }
%}

%eofval{
    if (yystate() == YYCOMMENT) {
        throw new SyntaxError("Comment mismatch, need *) at EOF", yyline, yycolumn);
    }
    return token(EOF, null);
%eofval}

LineTerm = \n|\r|\r\n
Identifier = [_a-z][_a-zA-Z0-9']*
DecInteger = [0-9]+
Whitespace = {LineTerm}|[ \t\f]

%state YYCOMMENT

%%

<YYINITIAL> {
    "(*" { commentCount = 1; yybegin(YYCOMMENT); }
    "*)" { throw new SyntaxError("Comment mismatch, extra *) found", yyline, yycolumn); }
    
    "nil"     { return token(NIL); }
    "ref"     { return token(REF); }
    "fn"      { return token(FN); }
    "rec"     { return token(REC); }
    "let"     { return token(LET); }
    "in"      { return token(IN); }
    "end"     { return token(END); }
    "if"      { return token(IF); }
    "then"    { return token(THEN); }
    "else"    { return token(ELSE); }
    "while"   { return token(WHILE); }
    "do"      { return token(DO); }
    "true"    { return token(TRUE); }
    "false"   { return token(FALSE); }
    "not"     { return token(NOT); }
    "andalso" { return token(ANDALSO); }
    "orelse"  { return token(ORELSE); }
    
    "+"  { return token(ADD); }
    "-"  { return token(SUB); }
    "*"  { return token(MUL); }
    "/"  { return token(DIV); }
    "%"  { return token(MOD); }
    "~"  { return token(NEG); }
    
    "="  { return token(EQ); }
    "<>" { return token(NE); }
    "<"  { return token(LT); }
    "<=" { return token(LE); }
    ">"  { return token(GT); }
    ">=" { return token(GE); }
    
    "::" { return token(CONS); }
    "()" { return token(UNIT); }
    "=>" { return token(ARROW); }
    
    ":=" { return token(ASSIGN); }
    "!"  { return token(DEREF); }
    
    ","  { return token(COMMA); }
    ";"  { return token(SEMI); }
    "("  { return token(LPAREN); }
    ")"  { return token(RPAREN); }
    
    {Identifier} { return token(ID, yytext()); }
    {DecInteger} { return token(NUM, Integer.valueOf(yytext())); }
    {Whitespace} { /* skip */ }
    
    [^] { throw new SyntaxError("Illegal character " + yytext(), yyline, yycolumn); }
}

<YYCOMMENT> {
    "(*" { commentCount++; }
    "*)" { commentCount--; if (commentCount == 0) yybegin(YYINITIAL); }
    [^]  {}
}
