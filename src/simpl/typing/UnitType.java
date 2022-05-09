package simpl.typing;

public final class UnitType extends Type {
    public static final UnitType INSTANCE = new UnitType();

    private UnitType() {
        super();
    }

    @Override
    public boolean isEqualityType() {
        return false;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        if (t instanceof UnitType) {
            return Substitution.IDENTITY;
        } else if (t instanceof TypeVar) {
            return t.unify(this);
        }
        throw new TypeMismatchError();
    }

    @Override
    public boolean contains(TypeVar tv) {
        return false;
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        return this;
    }

    public String toString() {
        return "unit";
    }
}
