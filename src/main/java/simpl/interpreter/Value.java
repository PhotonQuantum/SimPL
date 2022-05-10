package simpl.interpreter;

public abstract class Value {
    // NIL and UNIT are moved to `NilValue.INSTANCE` and `UnitValue.INSTANCE` to avoid vm deadlock.
    // See https://bugs.openjdk.java.net/browse/JDK-6301579.

    protected Value() {
        super();
    }

    public abstract boolean equals(Object other);
}
