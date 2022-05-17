package simpl.interpreter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record ConsValue(@NotNull Value v1, @NotNull Value v2) implements Value {

    public ConsValue(@NotNull Value v1, @NotNull Value v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    @Contract(pure = true)
    public @NotNull String toString() {
        return "list@" + length();
    }

    public int length() {
        if (v2 instanceof ConsValue v) {
            return 1 + v.length();
        }
        // This must be NilValue.
        return 1;
    }

    @Override
    public boolean equals(State s, Value o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ConsValue consValue = (ConsValue) o;
        return v1.equals(consValue.v1) && v2.equals(consValue.v2);
    }
}
