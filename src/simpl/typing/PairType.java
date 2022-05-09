package simpl.typing;

public final class PairType extends Type {

    public Type t1, t2;

    public PairType(Type t1, Type t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override public boolean isEqualityType() {
        return true;
    }

    @Override public Substitution unify(Type t) throws TypeError {
        if (t instanceof PairType rhs) {
            return t1.unify(rhs.t1).compose(t2.unify(rhs.t2));
        }
        else if (t instanceof TypeVar) {
            return t.unify(this);
        }
        throw new TypeMismatchError();
    }

    @Override public boolean contains(TypeVar tv) {
        return t1.contains(tv) || t2.contains(tv);
    }

    @Override public Type replace(TypeVar a, Type t) {
        return new PairType(t1.replace(a, t), t2.replace(a, t));
    }

    public String toString() {
        return "(" + t1 + " * " + t2 + ")";
    }
}
