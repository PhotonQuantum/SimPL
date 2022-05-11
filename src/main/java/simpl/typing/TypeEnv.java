package simpl.typing;

import kala.collection.immutable.ImmutableCompactSet;
import kala.collection.immutable.ImmutableSet;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import simpl.parser.Symbol;

public class TypeEnv {
    public static final TypeEnv EMPTY = new TypeEnv();
    private final TypeEnv E;
    protected final Symbol x;
    protected final TypeScheme t;

    private TypeEnv(TypeEnv e, Symbol x, TypeScheme t) {
        E = e;
        this.x = x;
        this.t = t;
    }

    protected TypeEnv() {
        E = null;
        this.x = null;
        this.t = null;
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull TypeEnv of(final TypeEnv E, final Symbol x, final TypeScheme t) {
        return new TypeEnv(E, x, t);
    }

    public TypeScheme get(Symbol x) {
        if (x == this.x)
            return t;
        if (E == null)
            return null;
        return E.get(x);
    }

    public String toString() {
        return x + ":" + t + ";" + E;
    }

    public ImmutableSet<Symbol> typeVars() {
        if (E == null)
            return ImmutableCompactSet.empty();
        return E.typeVars().added(x);
    }
}
