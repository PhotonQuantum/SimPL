package simpl.interpreter.pcf;

import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.parser.Symbol;
import simpl.parser.ast.IntegerLiteral;
import simpl.parser.ast.Name;
import simpl.parser.ast.Sub;

public class Pred extends FunValue {
    public static final FunValue INSTANCE = new Pred();

    private Pred() {
        super(Env.EMPTY, Symbol.of("x"), new Sub(new Name(Symbol.of("x")), new IntegerLiteral(1)));
    }
}
