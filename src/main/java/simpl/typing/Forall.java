package simpl.typing;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Forall extends TypeScheme {
    public final TypeVar a;
    public final TypeScheme s;

    public Forall(TypeVar a, TypeScheme s) {
        this.a = a;
        this.s = s;
    }

    @Contract("_, _ -> new")
    @Override
    public @NotNull TypeScheme replace(TypeVar a, Type t) {
        return new Forall(a, s.replace(a, t));
    }

    @Override
    public Type instantiate() {
        return s.replace(a, new TypeVar(true)).instantiate();
    }
}
