package simpl.parser.ast;

import org.jetbrains.annotations.NotNull;
import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Let extends Expr {

    public final Symbol x;
    public final Expr e1;
    public final Expr e2;

    public Let(Symbol x, Expr e1, Expr e2) {
        this.x = x;
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(let " + x + " = " + e1 + " in " + e2 + ")";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        /* W(Γ; e1) = (S1; τ1) */
        var W1 = e1.typeCheck(E);

        /* σ = gen(τ1, S1 Γ) */
        var s = W1.ty().generalizeWith(W1.subst().applyOn(E));

        /* W(S1 Γ, x:σ; e2) = (S2; τ2) */
        var W2 = e2.typeCheck(TypeEnv.of(W1.subst().applyOn(E), x, s));

        /* W(Γ; let x = e1 in e2) = (S2∘S1; τ2) */
        return TypeResult.of(W2.subst().compose(W1.subst()), W2.ty());
    }

    @Override
    public Value eval(@NotNull State s) throws RuntimeError {
        // E-Let
        // TODO Call by value
        // var v1 = e1.eval(s);
        // Call by name
        var v1 = ThunkValue.delay(s.E, e1);
        var E = Env.of(s.E, x, v1);
        return e2.eval(State.of(E, s.M, s.p, s.config));
    }
}
