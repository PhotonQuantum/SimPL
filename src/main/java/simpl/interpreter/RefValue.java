package simpl.interpreter;

import org.jetbrains.annotations.NotNull;

public class RefValue extends Mem.Cell implements Value {

    public RefValue(Mem.@NotNull Cell cell) {
        super(cell);
    }

    public String toString() {
        return "ref@" + unsafeGetPointer();
    }


    @Override
    public boolean equals(State s, Value o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefValue refValue = (RefValue) o;
        return this.unsafeGetPointer() == refValue.unsafeGetPointer();
    }
}
