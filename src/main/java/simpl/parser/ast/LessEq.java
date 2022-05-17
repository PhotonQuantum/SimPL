package simpl.parser.ast;

import simpl.interpreter.*;

public class LessEq extends RelExpr {

    public LessEq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " <= " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        if (l.eval(s) instanceof IntValue lhs) {
            if (r.eval(s) instanceof IntValue rhs) {
                return new BoolValue(lhs.n() <= rhs.n());
            }
            throw new RuntimeError(r + " is not an integer");
        }
        throw new RuntimeError(l + " is not an integer");
    }
}
