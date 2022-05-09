package simpl.interpreter;

public class UnitValue extends Value {
    public static final UnitValue INSTANCE = new UnitValue();

    private UnitValue() {
        super();
    }

    public String toString() {
        return "unit";
    }

    @Override
    public boolean equals(Object o) {
        return o != null && getClass() == o.getClass();
    }
}
