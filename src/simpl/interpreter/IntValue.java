package simpl.interpreter;

public class IntValue extends Value {

    public final int n;

    public IntValue(int n) {
        this.n = n;
    }

    public String toString() {
        return "" + n;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntValue intValue = (IntValue) o;
        return n == intValue.n;
    }
}
