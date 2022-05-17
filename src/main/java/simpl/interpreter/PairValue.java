package simpl.interpreter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record PairValue(@NotNull Value v1, @NotNull Value v2) implements Value {

    public PairValue(@NotNull Value v1, @NotNull Value v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    @Contract(pure = true)
    public @NotNull String toString() {
        return "pair@" + v1 + "@" + v2;
    }

    @Override
    public boolean equals(State s, Value o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairValue pairValue = (PairValue) o;
        return v1.equals(pairValue.v1) && v2.equals(pairValue.v2);
    }
}
