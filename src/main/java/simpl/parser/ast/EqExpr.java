package simpl.parser.ast;

import simpl.typing.BoolType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public abstract class EqExpr extends BinaryExpr {

    public EqExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        /*
            W(Γ; l) ⊢ (S1; τ1)
            W(S1∘Γ; r) ⊢ (S2; τ2)
         */
        var W1 = l.typeCheck(E);
        var W2 = r.typeCheck(W1.subst().applyOn(E));

        /* S2 τ1 ~ τ2 ~> S3 */
        var S3 = W2.subst().applyOn(W1.ty()).unify(W2.ty());

        /* eqType (S3∘S2 τ1) ∧ eqType (S3 τ2) */
        var leftTy = S3.compose(W2.subst()).applyOn(W1.ty());
        var rightTy = S3.applyOn(W2.ty());
        if (!leftTy.isEqualityType()) {
            throw new TypeError(leftTy + " is not an equality type");
        }
        if (!rightTy.isEqualityType()) {
            throw new TypeError(rightTy + " is not an equality type");
        }

        /* S = S3∘S2∘S1 */
        var S = S3.compose(W2.subst()).compose(W1.subst());

        /* W(Γ; l = r) = (S; bool) */
        return TypeResult.of(S, BoolType.INSTANCE);
    }
}
