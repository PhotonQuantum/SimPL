package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.*;

public class Assign extends BinaryExpr {

    public Assign(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return l + " := " + r;
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        /*
            W(Γ; l) ⊢ (S1; τ1)
            W(S1∘Γ; r) ⊢ (S2; τ2)
         */
        var W1 = l.typeCheck(E);
        var W2 = r.typeCheck(W1.subst().applyOn(E));

        /* S2 τ1 ~ ref τ2 ~> S3 */
        var S3 = W2.subst().applyOn(W1.ty()).unify(RefType.of(W2.ty()));

        /* S = S3∘S2∘S1 */
        var S = S3.compose(W2.subst()).compose(W1.subst());

        /* W(Γ; l := r) = (S; ()) */
        return TypeResult.of(S, UnitType.INSTANCE);
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
