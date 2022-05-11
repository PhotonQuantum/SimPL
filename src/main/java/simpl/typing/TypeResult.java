package simpl.typing;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record TypeResult(Substitution subst, Type ty) {
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull TypeResult of(Type t) {
        return TypeResult.of(Substitution.IDENTITY, t);
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull TypeResult of(Substitution s, Type t) {
        return new TypeResult(s, t);
    }
}
