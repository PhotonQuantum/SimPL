package simpl.typing;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class ArrowType extends Type {

    public final Type t1;
    public final Type t2;

    private ArrowType(Type t1, Type t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    @Contract("_, _ -> new")
    public static @NotNull ArrowType of(Type t1, Type t2) {
        return new ArrowType(t1, t2);
    }

    @Override
    public boolean isEqualityType() {
        return false;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        if (t instanceof ArrowType rhs) {
            return t1.unify(rhs.t1).compose(t2.unify(rhs.t2));
        } else if (t instanceof TypeVar) {
            return t.unify(this);
        }
        throw new TypeMismatchError();
    }

    @Override
    public boolean contains(TypeVar tv) {
        return t1.contains(tv) || t2.contains(tv);
    }

    @Contract("_, _ -> new")
    @Override
    public @NotNull Type replace(TypeVar a, Type t) {
        return ArrowType.of(t1.replace(a, t), t2.replace(a, t));
    }

    @Contract(pure = true)
    public @NotNull String toString() {
        return "(" + t1 + " -> " + t2 + ")";
    }
}
