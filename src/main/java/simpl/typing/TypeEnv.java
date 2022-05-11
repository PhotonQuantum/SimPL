package simpl.typing;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import simpl.parser.Symbol;

public abstract class TypeEnv {

    public static final TypeEnv EMPTY = new TypeEnv() {
        @Contract(pure = true)
        @Override
        public TypeScheme get(Symbol x) {
            return null;
        }
    };

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull TypeEnv of(final TypeEnv E, final Symbol x, final Type t) {
        return new TypeEnv() {
            public TypeScheme get(Symbol x1) {
                if (x == x1)
                    return t;
                return E.get(x1);
            }

            public String toString() {
                return x + ":" + t + ";" + E;
            }
        };
    }

    public abstract TypeScheme get(Symbol x);
}
