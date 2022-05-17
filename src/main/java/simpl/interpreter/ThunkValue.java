package simpl.interpreter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import simpl.parser.ast.Expr;

public class ThunkValue implements Value {

    public final Env E;
    public final Expr e;
    public Value cachedValue = null;

    private ThunkValue(Env E, Expr e) {
        this.E = E;
        this.e = e;
    }

    @Contract("_, _ -> new")
    public static @NotNull ThunkValue delay(Env E, Expr e) {
        return new ThunkValue(E, e);
    }

    public Value force(State s) throws RuntimeError {
        if (cachedValue == null) {
            cachedValue = e.eval(State.of(E, s.M, s.p, s.config.join(new Config(null, false, null, null))));
        }
        return cachedValue;
    }

    @Override
    public boolean equals(State s, Value o) throws RuntimeError {
        var v = force(s);
        return v.equals(s, o);
    }
}
