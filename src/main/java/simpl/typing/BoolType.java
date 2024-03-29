package simpl.typing;

import kala.collection.immutable.ImmutableCompactSet;
import kala.collection.immutable.ImmutableSet;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class BoolType implements Type {
    public static final BoolType INSTANCE = new BoolType();

    @Override
    public boolean isEqualityType() {
        return true;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        try {
            if (t instanceof BoolType) {
                return Substitution.IDENTITY;
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
        return false;
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        return this;
    }

    @Override
    public @NotNull ImmutableSet<TypeVar> freeTypeVars() {
        return ImmutableCompactSet.empty();
    }

    @Contract(pure = true)
    public @NotNull String toString() {
        return "bool";
    }
}
