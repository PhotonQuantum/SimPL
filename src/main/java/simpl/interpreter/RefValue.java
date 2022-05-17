package simpl.interpreter;

import org.jetbrains.annotations.NotNull;

public final class RefValue extends Mem.Cell implements Value, Comparable<RefValue> {

    public RefValue(Mem.@NotNull Cell cell) {
        super(cell);
    }

    public @NotNull String toString() {
        return "ref@" + unsafeGetPointer();
    }


    @Override
    public boolean equals(State s, Value o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefValue refValue = (RefValue) o;
        return this.unsafeGetPointer() == refValue.unsafeGetPointer();
    }

    @Override
    public int compareTo(@NotNull RefValue o) {
        return Integer.compare(unsafeGetPointer(), o.unsafeGetPointer());
    }
}
