package simpl.typing;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Forall(TypeVar a, TypeScheme s) implements TypeScheme {

    @Contract("_, _ -> new")
    @Override
    public @NotNull Forall replace(TypeVar a, Type t) {
        return new Forall(a, s.replace(a, t));
    }

    @Override
    public Type instantiate() {
        return s.replace(a, new TypeVar(true)).instantiate();
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "∀" + a + ". " + s;
    }
}
