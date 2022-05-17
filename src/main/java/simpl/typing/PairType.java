package simpl.typing;

import kala.collection.immutable.ImmutableSet;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public record PairType(Type t1, Type t2) implements Type {

    @Contract("_, _ -> new")
    public static @NotNull PairType of(Type t1, Type t2) {
        return new PairType(t1, t2);
    }

    @Override
    public boolean isEqualityType() {
        return t1.isEqualityType() && t2.isEqualityType();
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        try {
            if (t instanceof PairType rhs) {
                return t1.unify(rhs.t1).compose(t2.unify(rhs.t2));
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
        return t1.contains(tv) || t2.contains(tv);
    }

    @Contract("_, _ -> new")
    @Override
    public @NotNull @Unmodifiable Type replace(TypeVar a, Type t) {
        return PairType.of(t1.replace(a, t), t2.replace(a, t));
    }

    @Override
    public @NotNull ImmutableSet<TypeVar> freeTypeVars() {
        return t1.freeTypeVars().addedAll(t2.freeTypeVars());
    }

    @Contract(pure = true)
    public @NotNull String toString() {
        return "(" + t1 + " * " + t2 + ")";
    }
}
