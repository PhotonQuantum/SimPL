package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.UnitType;

public class Seq extends BinaryExpr {

    public Seq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " ; " + r + ")";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        /*
            W(Γ; l) = (S1; τ1)
            W(S1∘Γ; r) = (S2; τ2)
         */
        var W1 = l.typeCheck(E);
        var W2 = r.typeCheck(W1.subst().applyOn(E));

        /* S2 τ1 ~ unit ~> S3 */
        var S3 = W2.subst().applyOn(W1.ty()).unify(UnitType.INSTANCE);

        /* S = S3∘S2∘S1 */
        var S = S3.compose(W2.subst()).compose(W1.subst());

        /* W(Γ; (l ; r)) = (S; S3 τ2) */
        return TypeResult.of(S, S3.applyOn(W2.ty()));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // E-Seq
        l.eval(s);
        return r.eval(s);
    }
}
