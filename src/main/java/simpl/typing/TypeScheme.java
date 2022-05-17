package simpl.typing;

public interface TypeScheme {
    /**
     * Replace a type variable with a type.
     *
     * @param a the type variable to replace.
     * @param t the type to replace the type variable with.
     * @return the type scheme after replace.
     */
    TypeScheme replace(TypeVar a, Type t);

    /**
     * Instantiate this type scheme.
     *
     * @return the instantiated type.
     */
    Type instantiate();
}
