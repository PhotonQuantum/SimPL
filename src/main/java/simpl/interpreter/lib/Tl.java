package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.parser.ast.Name;
import simpl.typing.TypeEnv;
import simpl.typing.TypeResult;

public class Tl extends FunValue {
    public static final FunValue INSTANCE = new Tl();

    private Tl() {
        super(Env.EMPTY, Symbol.of("x"), new Expr() {
            @Override
            public TypeResult typeCheck(TypeEnv E) {
                // No need to tyck because its type is hard-coded at TypeEnv.DEFAULT.
                return null;
            }

            @Override
            public Value eval(State s) throws RuntimeError {
                var x = new Name(Symbol.of("x")).eval(s);
                if (x instanceof ConsValue l) {
                    return l.v2;
                }
                throw new RuntimeError(x + " is not a cons");
            }
        });
    }
}
