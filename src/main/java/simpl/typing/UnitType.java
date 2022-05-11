package simpl.typing;

import kala.collection.immutable.ImmutableCompactSet;
import kala.collection.immutable.ImmutableSet;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class UnitType extends Type {
    public static final UnitType INSTANCE = new UnitType();

    private UnitType() {
        super();
    }

    @Override
    public boolean isEqualityType() {
        return false;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        try {
            if (t instanceof UnitType) {
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
        return "unit";
    }
}
