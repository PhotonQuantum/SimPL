package simpl.typing;

public class TypeResult {

    public Substitution s;
    public Type t;

    private TypeResult(Substitution s, Type t) {
        this.s = s;
        this.t = t;
    }

    public static TypeResult of(Type t) {
        return TypeResult.of(Substitution.IDENTITY, t);
    }

    public static TypeResult of(Substitution s, Type t) {
        return new TypeResult(s, t);
    }
}
