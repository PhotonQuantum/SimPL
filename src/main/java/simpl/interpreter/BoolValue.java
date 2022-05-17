package simpl.interpreter;

public class BoolValue implements Value {

    public final boolean b;

    public BoolValue(boolean b) {
        this.b = b;
    }

    public String toString() {
        return "" + b;
    }

    @Override
    public boolean equals(State s, Value o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BoolValue boolValue = (BoolValue) o;
        return b == boolValue.b;
    }
}
