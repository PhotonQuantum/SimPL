all: Parser.java Symbols.java Lexer.java

Parser.java: simpl.grm
	java -jar java-cup-11a.jar -parser Parser -symbols Symbols -interface < simpl.grm

Symbols.java: simpl.grm
	java -jar java-cup-11a.jar -parser Parser -symbols Symbols -interface < simpl.grm

Lexer.java: simpl.lex
	java -cp JFlex.jar JFlex.Main simpl.lex

clean:
	rm -f Parser.java Symbols.java Lexer.java
