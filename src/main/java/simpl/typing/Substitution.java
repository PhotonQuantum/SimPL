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
        return new TypeEnv() {
            public Type get(Symbol x) {
                return applyOn(E.get(x));
            }
        };
    }

    private static final class Identity extends Substitution {
        public Type applyOn(Type t) {
            return t;
        }
    }

    private static final class Replace extends Substitution {
        private final TypeVar a;
        private final Type t;

        public Replace(TypeVar a, Type t) {
            this.a = a;
            this.t = t;
        }

        public Type applyOn(@NotNull Type b) {
            return b.replace(a, t);
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
    }
}
