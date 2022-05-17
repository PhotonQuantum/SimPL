package simpl.interpreter;

public class Int {

    private int n;

    public Int(int n) {
        this.n = n;
    }

    public int increment() {
        return n++;
    }

    public int decrement() {
        return n--;
    }
}
