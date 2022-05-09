package simpl.typing;

import simpl.parser.Symbol;

public class TypeVar extends Type {

    private static int tvcnt = 0;

    private final boolean equalityType;
    private final Symbol name;

    public TypeVar(boolean equalityType) {
        this.equalityType = equalityType;
        name = Symbol.symbol("tv" + ++tvcnt);
    }

    @Override public boolean isEqualityType() {
        return equalityType;
    }

    @Override public Substitution unify(Type t) throws TypeCircularityError {
        // TODO
        return null;
    }

    public String toString() {
        return "" + name;
    }

    @Override public boolean contains(TypeVar tv) {
        // TODO
        return false;
    }

    @Override public Type replace(TypeVar a, Type t) {
        // TODO
        return null;
    }
}
