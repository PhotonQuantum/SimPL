package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.*;

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
        /*
            W(Γ; e1) = (S1; τ1)
            W(S1∘Γ; e2) = (S2; τ2)
         */
        var W1 = e1.typeCheck(E);
        var W2 = e2.typeCheck(W1.subst().applyOn(E));

        /*
            S2 τ1 ~ bool ~> S3
            S3 τ2 ~ unit ~> S4
         */
        var S3 = W2.subst().applyOn(W1.ty()).unify(BoolType.INSTANCE);
        var S4 = S3.applyOn(W2.ty()).unify(UnitType.INSTANCE);

        /* S = S4∘S3∘S2∘S1 */
        var S = S4.compose(S3).compose(W2.subst()).compose(W1.subst());

        /* W(Γ; while e1 do e2) = (S; ()) */
        return TypeResult.of(S, UnitType.INSTANCE);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        if (e1.eval(s) instanceof BoolValue predicate) {
            return (predicate.b()) ? new Seq(e2, this).eval(s) : UnitValue.INSTANCE;
        }
        throw new RuntimeError(e1 + " is not a boolean");
    }
}
