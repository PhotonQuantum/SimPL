package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Loop extends Expr {

    public final Expr e1;
    public final Expr e2;

    public Loop(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(while " + e1 + " do " + e2 + ")";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        // TODO
        return null;
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        if (e1.eval(s) instanceof BoolValue predicate) {
            return (predicate.b) ? new Seq(e2, this).eval(s) : UnitValue.INSTANCE;
        }
        throw new RuntimeError(e1 + " is not a boolean");
    }
}
