package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class Mod extends ArithExpr {

    public Mod(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " % " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // E-Mod
        if (l.eval(s) instanceof IntValue lhs) {
            if (r.eval(s) instanceof IntValue rhs) {
                if (rhs.n() == 0) {
                    throw new RuntimeError("Division by zero");
                }
                return new IntValue(lhs.n() % rhs.n());
            }
            throw new RuntimeError(r + " is not an integer");
        }
        throw new RuntimeError(l + " is not an integer");
    }
}
