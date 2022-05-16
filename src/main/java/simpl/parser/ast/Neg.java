package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.IntType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Neg extends UnaryExpr {

    public Neg(Expr e) {
        super(e);
    }

    public String toString() {
        return "~" + e;
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        /* W(Γ; e) = (S; τ) */
        var W = e.typeCheck(E);

        /* τ ~ int ~> S' */
        var S_ = W.ty().unify(IntType.INSTANCE);

        /* W(Γ; ~e) = (S'∘S; int) */
        return TypeResult.of(S_.compose(W.subst()), IntType.INSTANCE);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // E-Neg
        if (e.eval(s) instanceof IntValue intValue) {
            return new IntValue(-intValue.n);
        }
        throw new RuntimeError(e + " is not an integer");
    }
}
