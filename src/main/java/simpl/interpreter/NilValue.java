package simpl.interpreter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class NilValue implements Value {
    public static final NilValue INSTANCE = new NilValue();

    @Override
    public boolean equals(State s, Value o) {
        return o != null && getClass() == o.getClass();
    }

    @Contract(pure = true)
    public @NotNull String toString() {
        return "nil";
    }
}
