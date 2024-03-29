package simpl.parser.ast;

import org.jetbrains.annotations.NotNull;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.ThunkValue;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public record Let(Symbol x, Expr e1, Expr e2, Boolean strict) implements Expr {

    public String toString() {
        return "(let " + x + " = " + e1 + " in " + e2 + ")";
    }

    @Override
    public @NotNull TypeResult typeCheck(TypeEnv E) throws TypeError {
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
        Value v1;
        if (strict || s.config.strict()) {
            v1 = e1.eval(s);
        } else {
            v1 = ThunkValue.delay(s.E, e1);
        }

        return e2.eval(State.of(s.E.extend(x, v1), s.M, s.config));
    }
}
