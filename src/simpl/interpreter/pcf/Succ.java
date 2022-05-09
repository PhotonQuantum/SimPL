package simpl.interpreter.pcf;

import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.parser.Symbol;
import simpl.parser.ast.Add;
import simpl.parser.ast.IntegerLiteral;
import simpl.parser.ast.Name;

public class Succ extends FunValue {
    public static final FunValue INSTANCE = new Succ();

    private Succ() {
        super(Env.EMPTY, Symbol.of("x"), new Add(new Name(Symbol.of("x")), new IntegerLiteral(1)));
    }
}
