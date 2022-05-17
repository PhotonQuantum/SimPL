package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.BoolType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Cond extends Expr {

    public final Expr e1;
    public final Expr e2;
    public final Expr e3;

    public Cond(Expr e1, Expr e2, Expr e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    public String toString() {
        return "(if " + e1 + " then " + e2 + " else " + e3 + ")";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        /*
            W(Γ; e1) = (S1; τ1)
            W(S1 Γ; e2) = (S2; τ2)
            W(S2∘S1 Γ; e3) = (S3; τ3)
        */
        var W1 = e1.typeCheck(E);
        var W2 = e2.typeCheck(W1.subst().applyOn(E));
        var W3 = e3.typeCheck(W2.subst().compose(W1.subst()).applyOn(E));

        /*
            S3∘S2 τ1 ~ bool ~> S4
            S4∘S3 τ2 ~ S4 τ3 ~> S5
         */
        var S4 = W3.subst().compose(W2.subst()).applyOn(W1.ty()).unify(BoolType.INSTANCE);
        var S5 = S4.compose(W3.subst()).applyOn(W2.ty()).unify(S4.applyOn(W3.ty()));

        /* S = S5∘S4∘S3∘S2∘S1 */
        var S = S5.compose(S4).compose(W3.subst()).compose(W2.subst()).compose(W1.subst());

        /* W(Γ; if e1 then e2 else e3) = (S; S5∘S4 τ3) */
        return TypeResult.of(S, S5.compose(S4).applyOn(W3.ty()));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // E-Cond1 & E-Cond2
        if (e1.eval(s) instanceof BoolValue predicate) {
            return predicate.b() ? e2.eval(s) : e3.eval(s);
        }
        throw new RuntimeError(e1 + " is not a boolean");
    }
}
