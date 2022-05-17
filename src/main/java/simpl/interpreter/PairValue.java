package simpl.interpreter;

import org.jetbrains.annotations.NotNull;

public class PairValue implements Value {

    @NotNull
    public final Value v1, v2;

    public PairValue(@NotNull Value v1, @NotNull Value v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public String toString() {
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
