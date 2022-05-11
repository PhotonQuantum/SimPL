package simpl.typing;

public abstract class Type extends TypeScheme {
    // NIL and UNIT are moved to `NilValue.INSTANCE` and `UnitValue.INSTANCE` to avoid vm deadlock.
    // See https://bugs.openjdk.java.net/browse/JDK-6301579.

    public abstract boolean isEqualityType();

    public abstract boolean contains(TypeVar tv);

    public abstract Substitution unify(Type t) throws TypeError;

    @Override
    public abstract Type replace(TypeVar a, Type t);
}
