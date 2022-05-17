package simpl.interpreter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record IntValue(int n) implements Value {

    @Contract(pure = true)
    public @NotNull String toString() {
        return "" + n;
    }

    @Override
    public boolean equals(State s, Value o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntValue intValue = (IntValue) o;
        return n == intValue.n;
    }
}
