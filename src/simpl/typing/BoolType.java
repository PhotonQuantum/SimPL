package simpl.typing;

final class BoolType extends Type {

    protected BoolType() {
    }

    @Override public boolean isEqualityType() {
        // TODO
        return false;
    }

    @Override public Substitution unify(Type t) throws TypeError {
        // TODO
        return null;
    }

    @Override public boolean contains(TypeVar tv) {
        // TODO
        return false;
    }

    @Override public Type replace(TypeVar a, Type t) {
        // TODO
        return null;
    }

    public String toString() {
        return "bool";
    }
}
