package simpl.interpreter;

import kala.collection.immutable.ImmutableSet;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import simpl.parser.ast.Expr;

public final class ThunkValue implements Value {

    public final Env E;
    public final Expr e;
    public Value cachedValue = null;
    public ImmutableSet<RefValue> cachedRefValues = null;

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
            s.M.updateRoot(s.E);    // Keep the root of the current stack in sync with the current environment
            s.M.pushRoot(E);        // Push the closure environment onto the stack
            cachedValue = e.eval(State.of(E, s.M, s.config));
            s.M.popRoot();
        }
        cachedRefValues = cachedValue.collectRefValues();
        return cachedValue;
    }

    @Override
    public boolean equals(State s, Value o) throws RuntimeError {
        var v = force(s);
        return v.equals(s, o);
    }
}
