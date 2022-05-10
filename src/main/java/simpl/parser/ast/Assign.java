package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Assign extends BinaryExpr {

    public Assign(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return l + " := " + r;
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        // TODO
        return null;
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // E-Assign
        if (l.eval(s) instanceof RefValue lhs) {
            s.M.put(lhs.p, r.eval(s));
            return UnitValue.INSTANCE;
        }
        throw new RuntimeError(l + " is not a reference");
    }
}
