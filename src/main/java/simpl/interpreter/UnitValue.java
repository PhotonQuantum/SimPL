package simpl.interpreter;

public final class UnitValue implements Value {
    public static final UnitValue INSTANCE = new UnitValue();

    private UnitValue() {
        super();
    }

    @Override
    public boolean equals(State s, Value o) {
        return o != null && getClass() == o.getClass();
    }

    public String toString() {
        return "unit";
    }
}
