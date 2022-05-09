package simpl.typing;

final class IntType extends Type {

    IntType() {
    }

    @Override public boolean isEqualityType() {
        return true;
    }

    @Override public Substitution unify(Type t) throws TypeError {
        if (t instanceof IntType) {
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
        return this;
    }

    public String toString() {
        return "int";
    }
}
