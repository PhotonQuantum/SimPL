package simpl.typing;

import kala.collection.immutable.ImmutableSet;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class ListType extends Type {

    public final Type t;

    private ListType(Type t) {
        this.t = t;
    }

    @Contract("_ -> new")
    public static @NotNull ListType of(Type t) {
        return new ListType(t);
    }

    @Override
    public boolean isEqualityType() {
        return t.isEqualityType();
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        try {
            if (t instanceof ListType rhs) {
                return this.t.unify(rhs.t);
            } else if (t instanceof TypeVar) {
                return t.unify(this);
            }
        } catch (TypeError e) {
            e.appendTrace(this, t);
            throw e;
        }

        throw new TypeMismatchError(this, t);
    }

    @Override
    public boolean contains(TypeVar tv) {
        return t.contains(tv);
    }

    @Contract("_, _ -> new")
    @Override
    public @NotNull Type replace(TypeVar a, Type t) {
        return ListType.of(this.t.replace(a, t));
    }

    @Override
    public ImmutableSet<TypeVar> freeTypeVars() {
        return t.freeTypeVars();
    }

    @Contract(pure = true)
    public @NotNull String toString() {
        return t + " list";
    }
}
