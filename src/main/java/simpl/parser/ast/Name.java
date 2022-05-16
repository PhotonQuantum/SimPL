package simpl.parser.ast;

import org.jetbrains.annotations.NotNull;
import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.TypeEnv;
import simpl.typing.TypeResult;

public class Name extends Expr {

    public final Symbol x;

    public Name(Symbol x) {
        this.x = x;
    }

    public String toString() {
        return "" + x;
    }

    @Override
    public TypeResult typeCheck(@NotNull TypeEnv E) {
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

        if (v instanceof RecValue recValue) {
            // E-Name1
            var recExpr = new Rec(x, recValue.e);
            return recExpr.eval(State.of(recValue.E, s.M, s.p));
        }

        // E-Name2
        return v;
    }
}
