package simpl.parser.ast;

import org.jetbrains.annotations.NotNull;
import simpl.interpreter.FunValue;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.*;

public class Fn extends Expr {

    public final Symbol x;
    public final Expr e;

    public Fn(Symbol x, Expr e) {
        this.x = x;
        this.e = e;
    }

    public String toString() {
        return "(fn " + x + "." + e + ")";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        /* W(Γ; x:α; e) ⊢ (S; τ) */
        var a = new TypeVar(true);
        var W = e.typeCheck(TypeEnv.of(E, x, a));

        /* W(Γ; (λ x e)) = (S; (S α) → τ) */
        return TypeResult.of(W.subst(), ArrowType.of(W.subst().applyOn(a), W.ty()));
    }

    @Override
    public Value eval(@NotNull State s) {
        return new FunValue(s.E, x, e);
    }
}
