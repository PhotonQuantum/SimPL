package simpl.interpreter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record StreamValue(Value head, ThunkValue tail) implements Value {
    @Override
    public boolean equals(State s, Value other) {
        return false;
    }

    @Contract(pure = true)
    public @NotNull String toString() {
        return "stream";
    }
}
