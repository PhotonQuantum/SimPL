package simpl.typing;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import simpl.parser.Symbol;

public interface Substitution {

    Substitution IDENTITY = new Identity();

    @Contract("_, _ -> new")
    static @NotNull Substitution of(TypeVar a, Type t) {
        return new Replace(a, t);
    }

    <T extends TypeScheme> T applyOn(T t);

    default Substitution compose(Substitution inner) {
        return new Compose(this, inner);
    }

    default TypeEnv applyOn(final TypeEnv E) {
        // We keep this method override instead of cons list because it doesn't touch free vars, and it's faster than
        // subst the whole list.
        return new TypeEnv() {
            public TypeScheme get(Symbol x) {
                return applyOn(E.get(x));
            }

            public String toString() {
                return x + ":" + get(x) + ";" + E;
            }
        };
    }

    @Override
    String toString();

    final class Identity implements Substitution {
        @Override
        public <T extends TypeScheme> T applyOn(T t) {
            return t;
        }

        @Contract(pure = true)
        @Override
        public @NotNull String toString() {
            return "⋅";
        }
    }

    final class Replace implements Substitution {
        private final TypeVar a;
        private final Type t;

        public Replace(TypeVar a, Type t) {
            this.a = a;
            this.t = t;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends TypeScheme> T applyOn(@NotNull T b) {
            // SAFETY: all replace implementations are covariant
            return (T) b.replace(a, t);
        }

        @Contract(pure = true)
        @Override
        public @NotNull String toString() {
            return a + "=" + t;
        }
    }

    final class Compose implements Substitution {
        private final Substitution f;
        private final Substitution g;

        public Compose(Substitution f, Substitution g) {
            this.f = f;
            this.g = g;
        }

        @Override
        public <T extends TypeScheme> T applyOn(T t) {
            return f.applyOn(g.applyOn(t));
        }

        @Contract(pure = true)
        @Override
        public @NotNull String toString() {
            return f + "∘" + g;
        }
    }
}
