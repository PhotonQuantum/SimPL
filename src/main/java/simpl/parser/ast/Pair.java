package simpl.parser.ast;

import simpl.interpreter.PairValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.PairType;
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

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        /*
            W(Γ; l) ⊢ (S1; τ1)
            W(S1∘Γ; r) ⊢ (S2; τ2)
         */
        var W1 = l.typeCheck(E);
        var W2 = r.typeCheck(W1.subst().applyOn(E));

        /* W(Γ; (l, r)) = (S2∘S1∘Γ; (S2 τ1) × τ2) */
        return TypeResult.of(W2.subst().compose(W1.subst()), PairType.of(W2.subst().applyOn(W1.ty()), W2.ty()));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // E-Pair
        return new PairValue(l.eval(s), r.eval(s));
    }
}
