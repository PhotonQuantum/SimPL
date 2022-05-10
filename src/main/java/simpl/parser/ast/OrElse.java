package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class OrElse extends BinaryExpr {

    public OrElse(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " orelse " + r + ")";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        // TODO
        return null;
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        if (l.eval(s) instanceof BoolValue lhs) {
            if (lhs.b) {
                // E-OrElse1
                return new BoolValue(true);
            }

            // E-OrElse2
            if (r.eval(s) instanceof BoolValue rhs) {
                return new BoolValue(rhs.b);
            }
            throw new RuntimeError(r + " is not a boolean");
        }
        throw new RuntimeError(l + " is not a boolean");
    }
}
