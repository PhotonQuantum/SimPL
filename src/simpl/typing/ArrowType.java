package simpl.typing;

public final class ArrowType extends Type {

    public Type t1, t2;

    public ArrowType(Type t1, Type t2) {
        this.t1 = t1;
        this.t2 = t2;
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
        return "(" + t1 + " -> " + t2 + ")";
    }
}
