package simpl.interpreter;

public class NilValue extends Value {
    public static final NilValue INSTANCE = new NilValue();

    private NilValue() {
        super();
    }

    public String toString() {
        return "nil";
    }

    @Override
    public boolean equals(Object o) {
        return o != null && getClass() == o.getClass();
    }
}
