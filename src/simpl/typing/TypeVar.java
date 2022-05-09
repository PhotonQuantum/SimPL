package simpl.typing;

import simpl.parser.Symbol;

import java.util.Objects;

public class TypeVar extends Type {

    private static int tvcnt = 0;

    private final boolean equalityType;
    private final Symbol name;

    public TypeVar(boolean equalityType) {
        this.equalityType = equalityType;
        name = Symbol.of("tv" + ++tvcnt);
    }

    @Override public boolean isEqualityType() {
        return equalityType;
    }

    @Override public Substitution unify(Type t) throws TypeCircularityError {
        if (t instanceof TypeVar rhs && this.equals(rhs)) {
            return Substitution.IDENTITY;
        }
        else if (t.contains(this)) {
            throw new TypeCircularityError();
        }
        else {
            return Substitution.of(this, t);
        }
    }

    public String toString() {
        return "" + name;
    }

    @Override public boolean contains(TypeVar tv) {
        return this.equals(tv);
    }

    @Override public Type replace(TypeVar a, Type t) {
        return this.equals(a) ? t : this;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TypeVar typeVar = (TypeVar) o;
        return name.equals(typeVar.name);
    }

    @Override public int hashCode() {
        return Objects.hash(name);
    }
}
