package simpl.typing;

public abstract class TypeScheme {
    public abstract TypeScheme replace(TypeVar a, Type t);

    public abstract Type instantiate();
}
