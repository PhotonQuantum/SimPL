package simpl.typing;

public abstract class Type {

    public static final Type INT = new IntType();
    public static final Type BOOL = new BoolType();
    public static final Type UNIT = new UnitType();

    public abstract boolean isEqualityType();

    public abstract Type replace(TypeVar a, Type t);

    public abstract boolean contains(TypeVar tv);

    public abstract Substitution unify(Type t) throws TypeError;
}
