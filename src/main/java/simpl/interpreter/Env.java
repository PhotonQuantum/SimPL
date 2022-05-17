package simpl.interpreter;

import kala.collection.MapView;
import kala.collection.base.MapIterator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import simpl.parser.Symbol;

public class Env implements MapView<Symbol, Value> {

    public static final Env EMPTY = new Env() {
        @Contract(pure = true)
        public @Nullable Value get(@NotNull Symbol y) {
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

    public static class EnvIterator implements MapIterator<Symbol, Value> {
        private final Env base;
        private Env current;

        private EnvIterator(@NotNull Env base) {
            this.base = base;
        }

        @Override
        public boolean hasNext() {
            if (current == null) return true;
            return current.E != null;
        }

        @Override
        public Symbol nextKey() {
            if (current == null) {
                current = base;
            } else {
                current = current.E;
            }

            if (current != null) return current.x;
            return null;
        }

        @Override
        public Value getValue() {
            if (current != null) return current.v;
            return null;
        }
    }

    @Override
    public @NotNull MapIterator<Symbol, Value> iterator() {
        return new EnvIterator(this);
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
        return Env.of(E.copy(), /* interned */ x, /* immutable */ v);
    }
}
