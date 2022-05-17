package simpl.interpreter;

public final class NilValue implements Value {
    public static final NilValue INSTANCE = new NilValue();

    private NilValue() {
        super();
    }

    @Override
    public boolean equals(State s, Value o) {
        return o != null && getClass() == o.getClass();
    }

    public String toString() {
        return "nil";
    }
}
