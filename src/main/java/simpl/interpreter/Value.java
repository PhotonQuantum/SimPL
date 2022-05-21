package simpl.interpreter;

import kala.collection.immutable.ImmutableCompactSet;
import kala.collection.immutable.ImmutableSet;

public sealed interface Value permits BoolValue, ConsValue, FunValue, IntValue, NilValue, PairValue, RecValue,
        RefValue, StreamValue, ThunkValue, UnitValue {
    // NIL and UNIT are moved to `NilValue.INSTANCE` and `UnitValue.INSTANCE` to avoid vm deadlock.
    // See https://bugs.openjdk.java.net/browse/JDK-6301579.

    boolean equals(State s, Value other) throws RuntimeError;

    default ImmutableSet<RefValue> collectRefValues() throws RuntimeError {
        if (this instanceof BoolValue) {
            return ImmutableCompactSet.empty();
        } else if (this instanceof ConsValue cons) {
            return cons.v1().collectRefValues().addedAll(cons.v2().collectRefValues());
        } else if (this instanceof FunValue) {
            return ImmutableCompactSet.empty();
        } else if (this instanceof IntValue) {
            return ImmutableCompactSet.empty();
        } else if (this instanceof NilValue) {
            return ImmutableCompactSet.empty();
        } else if (this instanceof PairValue pair) {
            return pair.v1().collectRefValues().addedAll(pair.v2().collectRefValues());
        } else if (this instanceof RecValue) {
            return ImmutableCompactSet.empty();
        } else if (this instanceof RefValue ref) {
            return ImmutableCompactSet.of(ref);
        } else if (this instanceof ThunkValue thunk) {
            return thunk.cachedRefValues != null ? thunk.cachedRefValues : ImmutableCompactSet.empty();
        } else if (this instanceof UnitValue) {
            return ImmutableCompactSet.empty();
        } else if (this instanceof StreamValue stream) {
            return stream.head().collectRefValues();
        } else {
            throw new RuntimeError("collectRefValues: unexpected value type: " + this.getClass().getName());
        }
    }
}
