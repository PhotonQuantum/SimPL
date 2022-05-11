package simpl.typing;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import simpl.parser.Symbol;

public abstract class Substitution {

    public static final Substitution IDENTITY = new Identity();

    @Contract("_, _ -> new")
    public static @NotNull Substitution of(TypeVar a, Type t) {
        return new Replace(a, t);
    }

    public abstract Type applyOn(Type t);

    public Substitution compose(Substitution inner) {
        return new Compose(this, inner);
    }

    public TypeEnv applyOn(final TypeEnv E) {
        // We keep this method override instead of cons list because it doesn't touch free vars, and it's faster than
        // subst the whole list.
        return new TypeEnv() {
            public TypeScheme get(Symbol x) {
                // SAFETY: we never keep universal quantifiers around when unifying.
                return applyOn((Type) E.get(x));
            }

            public String toString() {
                return x + ":" + get(x) + ";" + E;
            }
        };
    }

    private static final class Identity extends Substitution {
        @Override
        public Type applyOn(Type t) {
            return t;
        }

        @Contract(pure = true)
        @Override
        public @NotNull String toString() {
            return "⋅";
        }
    }

    private static final class Replace extends Substitution {
        private final TypeVar a;
        private final Type t;

        public Replace(TypeVar a, Type t) {
            this.a = a;
            this.t = t;
        }

        @Override
        public Type applyOn(@NotNull Type b) {
            return b.replace(a, t);
        }

        @Contract(pure = true)
        @Override
        public @NotNull String toString() {
            return a + "=" + t;
        }
    }

    private static final class Compose extends Substitution {
        private final Substitution f;
        private final Substitution g;

        public Compose(Substitution f, Substitution g) {
            this.f = f;
            this.g = g;
        }

        public Type applyOn(Type t) {
            return f.applyOn(g.applyOn(t));
        }

        @Contract(pure = true)
        @Override
        public @NotNull String toString() {
            return f + "∘" + g;
        }
    }

    @Override
    public abstract String toString();
}
