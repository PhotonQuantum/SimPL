package simpl.typing;

import kala.collection.immutable.ImmutableCompactSet;
import kala.collection.immutable.ImmutableSet;
import org.jetbrains.annotations.NotNull;

public abstract class Type extends TypeScheme {
    // NIL and UNIT are moved to `NilValue.INSTANCE` and `UnitValue.INSTANCE` to avoid vm deadlock.
    // See https://bugs.openjdk.java.net/browse/JDK-6301579.

    public abstract boolean isEqualityType();

    public abstract boolean contains(TypeVar tv);

    public abstract Substitution unify(Type t) throws TypeError;

    @Override
    public abstract Type replace(TypeVar a, Type t);

    public abstract ImmutableSet<TypeVar> freeTypeVars();

    public TypeScheme generalizeWith(@NotNull TypeEnv E) {
        var tFreeVars = freeTypeVars();
        var eBoundVars = E.typeVars();

        // TODO filter instead of filterNot?
        var genVars =
                tFreeVars.view().filterNot(v -> eBoundVars.contains(v.name)).collect(ImmutableCompactSet.factory());

        return genVars.foldLeft((TypeScheme) this, (acc, a) -> new Forall(a, acc));
    }

    @Override
    public Type instantiate() {
        return this;
    }
}
