package simpl.typing;

import kala.collection.immutable.ImmutableSet;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public record RefType(Type t) implements Type {

    @Contract("_ -> new")
    public static @NotNull RefType of(Type t) {
        return new RefType(t);
    }

    @Override
    public boolean isEqualityType() {
        return true;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        try {
            if (t instanceof RefType rhs) {
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
    public @NotNull @Unmodifiable Type replace(TypeVar a, Type t) {
        return RefType.of(this.t.replace(a, t));
    }

    @Override
    public ImmutableSet<TypeVar> freeTypeVars() {
        return t.freeTypeVars();
    }

    @Contract(pure = true)
    public @NotNull String toString() {
        return t + " ref";
    }
}
