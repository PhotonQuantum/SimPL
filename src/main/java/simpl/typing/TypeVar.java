package simpl.typing;

import kala.collection.immutable.ImmutableCompactSet;
import kala.collection.immutable.ImmutableSet;
import org.jetbrains.annotations.NotNull;
import simpl.parser.Symbol;

import java.util.Objects;

public class TypeVar implements Type, Comparable<TypeVar> {

    private static int tvcnt = 0;
    public final Symbol name;
    private final boolean equalityType;

    public TypeVar(boolean equalityType) {
        // TODO What's the purpose of recording the equality property here?
        // All usage are defined as equalityType=true temporarily.
        this.equalityType = equalityType;
        name = Symbol.of("tv" + ++tvcnt);
    }

    @Override
    public boolean isEqualityType() {
        // TODO check where this is used
        return equalityType;
    }

    @Override
    public Substitution unify(Type t) throws TypeCircularityError {
        if (t instanceof TypeVar rhs && this.equals(rhs)) {
            return Substitution.IDENTITY;
        } else if (t.contains(this)) {
            throw new TypeCircularityError();
        } else {
            return Substitution.of(this, t);
        }
    }

    public String toString() {
        return "" + name;
    }

    @Override
    public boolean contains(TypeVar tv) {
        return this.equals(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        return this.equals(a) ? t : this;
    }

    @Override
    public ImmutableSet<TypeVar> freeTypeVars() {
        return ImmutableCompactSet.of(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TypeVar typeVar = (TypeVar) o;
        return name.equals(typeVar.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(@NotNull TypeVar o) {
        return name.compareTo(o.name);
    }
}
