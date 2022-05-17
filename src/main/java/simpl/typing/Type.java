package simpl.typing;

import kala.collection.immutable.ImmutableCompactSet;
import kala.collection.immutable.ImmutableSet;
import org.jetbrains.annotations.NotNull;

public interface Type extends TypeScheme {
    // NIL and UNIT are moved to `NilValue.INSTANCE` and `UnitValue.INSTANCE` to avoid vm deadlock.
    // See https://bugs.openjdk.java.net/browse/JDK-6301579.

    /**
     * Determine the equality decidability.
     *
     * @return true if this type is equality type, otherwise false.
     */
    boolean isEqualityType();

    /**
     * Check occurrence of a specific type variable.
     *
     * @param tv the type variable to check.
     * @return true if this type contains the specified type variable, otherwise false.
     */
    boolean contains(TypeVar tv);

    /**
     * Unify this type with another type.
     *
     * @param t the other type to unify with.
     * @return the substitution that makes this type equal to the other type.
     * @throws TypeError if the unification fails.
     */
    Substitution unify(Type t) throws TypeError;

    /**
     * Replace a type variable with a type.
     *
     * @param a the type variable to replace.
     * @param t the type to replace the type variable with.
     * @return the type after replace.
     */
    @Override
    Type replace(TypeVar a, Type t);

    /**
     * Collect all free type variables in this type.
     *
     * @return the set of free type variables.
     */
    ImmutableSet<TypeVar> freeTypeVars();

    /**
     * Generalize all free type variables in this type.
     *
     * @param E the type environment to generalize under.
     * @return the generalized type scheme.
     */
    default TypeScheme generalizeWith(@NotNull TypeEnv E) {
        var tFreeVars = freeTypeVars();
        var eBoundVars = E.typeVars();

        var genVars =
                tFreeVars.view().filterNot(v -> eBoundVars.contains(v.name)).collect(ImmutableCompactSet.factory());

        return genVars.foldLeft((TypeScheme) this, (acc, a) -> new Forall(a, acc));
    }

    /**
     * Instantiate this type scheme.
     *
     * @return the instantiated type.
     */
    @Override
    default Type instantiate() {
        return this;
    }
}
