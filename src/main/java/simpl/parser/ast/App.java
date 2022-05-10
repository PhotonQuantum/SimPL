package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class App extends BinaryExpr {

    public App(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " " + r + ")";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        // TODO
        return null;
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // E-App
        if (l.eval(s) instanceof FunValue lhs) {
            // Call by value
            var rhs = r.eval(s); // Can't be inlined to next line because of side effects.
            var E = Env.of(lhs.E, lhs.x, rhs);
            return lhs.e.eval(State.of(E, s.M, s.p));
            // TODO Call by name
        }
        throw new RuntimeError(l + " is not a function");
    }
}
