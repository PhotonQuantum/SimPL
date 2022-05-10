package simpl.typing;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TypeResult {

    public final Substitution s;
    public final Type t;

    private TypeResult(Substitution s, Type t) {
        this.s = s;
        this.t = t;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull TypeResult of(Type t) {
        return TypeResult.of(Substitution.IDENTITY, t);
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull TypeResult of(Substitution s, Type t) {
        return new TypeResult(s, t);
    }
}
