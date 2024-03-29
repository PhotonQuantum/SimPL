package simpl.typing;

import kala.collection.immutable.ImmutableSet;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public record ArrowType(Type t1, Type t2) implements Type {

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
        try {
            if (t instanceof ArrowType rhs) {
                /*
                    τ11 ~ τ21 ~> S1
                    S1 τ12 ~ S1 τ22 ~> S2
                */
                var S1 = t1.unify(rhs.t1);
                var S2 = S1.applyOn(t2).unify(S1.applyOn(rhs.t2));

                /* (τ11 → τ12) ~ (τ21 → τ22) ~> S2∘S1 */
                return S2.compose(S1);
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
        return ArrowType.of(t1.replace(a, t), t2.replace(a, t));
    }

    @Contract(pure = true)
    @Override
    public @NotNull ImmutableSet<TypeVar> freeTypeVars() {
        return t1.freeTypeVars().addedAll(t2.freeTypeVars());
    }

    @Contract(pure = true)
    public @NotNull String toString() {
        return "(" + t1 + " -> " + t2 + ")";
    }
}
