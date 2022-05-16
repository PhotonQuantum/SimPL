package simpl.parser.ast;

import simpl.typing.IntType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public abstract class ArithExpr extends BinaryExpr {

    public ArithExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        /*
            W(Γ; l) = (S1; τ1)
            W(S1∘Γ; r) = (S2; τ2)
         */
        var W1 = l.typeCheck(E);
        var W2 = r.typeCheck(W1.subst().applyOn(E));

        /*
            S2 τ1 ~ int ~> S3
            S3 τ2 ~ int ~> S4
         */
        var S3 = W2.subst().applyOn(W1.ty()).unify(IntType.INSTANCE);
        var S4 = S3.applyOn(W2.ty()).unify(IntType.INSTANCE);

        /* S = S4∘S3∘S2∘S1 */
        var S = S4.compose(S3).compose(W2.subst()).compose(W1.subst());

        /* W(Γ; l + r) = (S; int) */
        return TypeResult.of(S, IntType.INSTANCE);
    }
}
