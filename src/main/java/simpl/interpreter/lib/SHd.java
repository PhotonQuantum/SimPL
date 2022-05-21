package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.parser.ast.Name;
import simpl.typing.TypeEnv;
import simpl.typing.TypeResult;

public class SHd extends FunValue {
    public static final FunValue INSTANCE = new SHd();

    private SHd() {
        super(Env.EMPTY, Symbol.of("x"), new Expr() {
            @Override
            public TypeResult typeCheck(TypeEnv E) {
                // No need to tyck because its type is hard-coded at TypeEnv.DEFAULT.
                return null;
            }

            @Override
            public Value eval(State s) throws RuntimeError {
                var x = new Name(Symbol.of("x")).eval(s);
                if (x instanceof StreamValue v) {
                    return v.head();
                }
                throw new RuntimeError(x + " is not a stream");
            }
        });
    }
}
