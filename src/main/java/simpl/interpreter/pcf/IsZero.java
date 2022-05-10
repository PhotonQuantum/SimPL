package simpl.interpreter.pcf;

import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.parser.Symbol;
import simpl.parser.ast.Eq;
import simpl.parser.ast.IntegerLiteral;
import simpl.parser.ast.Name;

public class IsZero extends FunValue {
    public static final FunValue INSTANCE = new IsZero();

    private IsZero() {
        super(Env.EMPTY, Symbol.of("x"), new Eq(new Name(Symbol.of("x")), new IntegerLiteral(0)));
    }
}
