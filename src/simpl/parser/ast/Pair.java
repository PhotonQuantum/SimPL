package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Pair extends BinaryExpr {

    public Pair(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(pair " + l + " " + r + ")";
    }

    @Override public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        return null;
    }

    @Override public Value eval(State s) throws RuntimeError {
        // TODO
        return null;
    }
}
