package simpl.interpreter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import simpl.parser.Symbol;

public class Env {

    public static final Env EMPTY = new Env() {
        public Value get(@NotNull Symbol y) {
            return null;
        }

        public Env copy() {
            return this;
        }
    };
    private final Env E;
    private final Symbol x;
    private final Value v;

    private Env() {
        E = null;
        x = null;
        v = null;
    }

    private Env(Env E, Symbol x, Value v) {
        this.E = E;
        this.x = x;
        this.v = v;
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull Env of(Env E, Symbol x, Value v) {
        return new Env(E, x, v);
    }

    public @NotNull Env extend(Symbol x, Value v) {
        return of(this, x, v);
    }

    public Value get(@NotNull Symbol y) {
        assert E != null;

        if (x == y) {
            return v;
        }
        return E.get(y);
    }

    public Env copy() {
        assert E != null;
        // TODO Value must be immutable, or must be deep-copied
        return Env.of(E.copy(), /* interned */ x, /* immutable */ v);
    }
}
