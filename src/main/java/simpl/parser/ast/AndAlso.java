package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.BoolType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class AndAlso extends BinaryExpr {

    public AndAlso(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " andalso " + r + ")";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        /*
            W(Γ; l) ⊢ (S1; τ1)
            W(S1∘Γ; r) ⊢ (S2; τ2)
         */
        var W1 = l.typeCheck(E);
        var W2 = r.typeCheck(W1.subst().applyOn(E));

        /*
            S2 τ1 ~ bool ~> S3
            S3 τ2 ~ bool ~> S4
         */
        var S3 = W2.subst().applyOn(W1.ty()).unify(BoolType.INSTANCE);
        var S4 = S3.applyOn(W2.ty()).unify(BoolType.INSTANCE);

        /* S = S4∘S3∘S2∘S1 */
        var S = S4.compose(S3).compose(W2.subst()).compose(W1.subst());

        /* W(Γ; l andalso r) = (S; bool) */
        return TypeResult.of(S, BoolType.INSTANCE);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        if (l.eval(s) instanceof BoolValue lhs) {
            if (!lhs.b) {
                // E-AndAlso2
                return new BoolValue(false);
            }

            // E-AndAlso1
            if (r.eval(s) instanceof BoolValue rhs) {
                return new BoolValue(rhs.b);
            }
            throw new RuntimeError(r + " is not a boolean");
        }
        throw new RuntimeError(l + " is not a boolean");
    }
}
