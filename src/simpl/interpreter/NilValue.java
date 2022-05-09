package simpl.interpreter;

class NilValue extends Value {

    protected NilValue() {
    }

    public String toString() {
        return "nil";
    }

    @Override public boolean equals(Object o) {
        return o != null && getClass() == o.getClass();
    }
}
