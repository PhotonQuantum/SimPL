package simpl.parser.ast;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.typing.TypeEnv;
import simpl.typing.TypeResult;

public record Name(Symbol x) implements Expr {

    @Contract(pure = true)
    public @NotNull String toString() {
        return "" + x;
    }

    @Override
    public @NotNull TypeResult typeCheck(@NotNull TypeEnv E) {
        /* τ = inst(Γ(x)) */
        var t = E.get(x).instantiate();

        /* W(Γ; x) = (̇⋅; τ) */
        return TypeResult.of(t);
    }

    @Override
    public Value eval(@NotNull State s) throws RuntimeError {
        @SuppressWarnings("SuspiciousNameCombination")
        var v = s.E.get(x);
        if (v == null) {
            throw new RuntimeError("Undefined variable: " + x);
        }

        if (v instanceof ThunkValue thunk) {
            v = thunk.force(s);
        }

        if (v instanceof RecValue recValue) {
            // E-Name1
            var recExpr = new Rec(x, recValue.e());
            return recExpr.eval(State.of(recValue.E(), s.M, s.config));
        }

        // E-Name2
        return v;
    }
}
