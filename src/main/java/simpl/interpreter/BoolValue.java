package simpl.interpreter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record BoolValue(boolean b) implements Value {

    @Contract(pure = true)
    public @NotNull String toString() {
        return "" + b;
    }

    @Override
    public boolean equals(State s, Value o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BoolValue boolValue = (BoolValue) o;
        return b == boolValue.b;
    }
}
