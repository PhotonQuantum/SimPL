package simpl.interpreter;

public interface Value {
    // NIL and UNIT are moved to `NilValue.INSTANCE` and `UnitValue.INSTANCE` to avoid vm deadlock.
    // See https://bugs.openjdk.java.net/browse/JDK-6301579.

    boolean equals(State s, Value other) throws RuntimeError;
}
