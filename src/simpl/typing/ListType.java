package simpl.typing;

public final class ListType extends Type {

    public Type t;

    public ListType(Type t) {
        this.t = t;
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
        return t + " list";
    }
}
