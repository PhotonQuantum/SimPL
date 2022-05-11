package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.TypeEnv;
import simpl.typing.TypeResult;

public class Snd extends FunValue {
    public static final FunValue INSTANCE = new Snd();

    private Snd() {
        super(Env.EMPTY, Symbol.of("x"), new Expr() {
            @Override
            public TypeResult typeCheck(TypeEnv E) {
                // TODO
                return null;
            }

            @Override
            public Value eval(State s) throws RuntimeError {
                var x = s.E.get(Symbol.of("x"));
                if (x instanceof PairValue pair) {
                    return pair.v2;
                }
                throw new RuntimeError(x + " is not a pair");
            }
        });
    }
}
