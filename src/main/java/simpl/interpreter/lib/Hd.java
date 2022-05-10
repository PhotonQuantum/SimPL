package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Hd extends FunValue {
    public static final FunValue INSTANCE = new Hd();

    private Hd() {
        super(Env.EMPTY, Symbol.of("x"), new Expr() {
            @Override
            public TypeResult typeCheck(TypeEnv E) throws TypeError {
                // TODO
                return null;
            }

            @Override
            public Value eval(State s) throws RuntimeError {
                var x = s.E.get(Symbol.of("x"));
                if (x instanceof ConsValue l) {
                    return l.v1;
                }
                throw new RuntimeError(x + " is not a cons");
            }
        });
    }
}
