package simpl.typing;

import simpl.parser.Symbol;

public abstract class TypeEnv {

    public static final TypeEnv empty = new TypeEnv() {
        @Override public Type get(Symbol x) {
            return null;
        }
    };

    public static TypeEnv of(final TypeEnv E, final Symbol x, final Type t) {
        return new TypeEnv() {
            public Type get(Symbol x1) {
                if (x == x1)
                    return t;
                return E.get(x1);
            }

            public String toString() {
                return x + ":" + t + ";" + E;
            }
        };
    }

    public abstract Type get(Symbol x);
}
