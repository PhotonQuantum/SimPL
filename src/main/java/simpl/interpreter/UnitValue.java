package simpl.interpreter;

public class UnitValue extends Value {
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
