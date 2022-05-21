package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.StreamType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class SCons extends BinaryExpr {
    public SCons(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " ;; " + r + ")";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        /*
            W(Γ; l) = (S1; τ1)
            W(S1∘Γ; r) = (S2; τ2)
         */
        var W1 = l.typeCheck(E);
        var W2 = r.typeCheck(W1.subst().applyOn(E));

        /* τ2 ~ list (S2 τ1) ~> S3 */
        var S3 = W2.ty().unify(StreamType.of(W2.subst().applyOn(W1.ty())));

        /* S = S3∘S2∘S1 */
        var S = S3.compose(W2.subst()).compose(W1.subst());

        /* W(Γ; l :: r) = (S; stream (S3∘S2 τ1)) */
        return TypeResult.of(S, StreamType.of(S3.compose(W2.subst()).applyOn(W1.ty())));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return new StreamValue(l.eval(s), ThunkValue.delay(s.E, r));
    }
}
