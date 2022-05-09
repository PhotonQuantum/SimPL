package simpl.typing;

final class UnitType extends Type {

    UnitType() {
    }

    @Override public boolean isEqualityType() {
        return false;
    }

    @Override public Substitution unify(Type t) throws TypeError {
        if (t instanceof UnitType) {
            return Substitution.IDENTITY;
        }
        else if (t instanceof TypeVar) {
            return t.unify(this);
        }
        throw new TypeMismatchError();
    }

    @Override public boolean contains(TypeVar tv) {
        return false;
    }

    @Override public Type replace(TypeVar a, Type t) {
        return Type.UNIT;
    }

    public String toString() {
        return "unit";
    }
}
