package simpl.interpreter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class UnitValue implements Value {
    public static final UnitValue INSTANCE = new UnitValue();

    private UnitValue() {
        super();
    }

    @Override
    public boolean equals(State s, Value o) {
        return o != null && getClass() == o.getClass();
    }

    @Contract(pure = true)
    public @NotNull String toString() {
        return "unit";
    }
}
