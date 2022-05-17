package simpl.interpreter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class MutableCell<T> {
    @NotNull T value;

    private MutableCell(@NotNull T value) {
        this.value = value;
    }

    @Contract(value = "_ -> new", pure = true)
    public static <T> @NotNull MutableCell<T> of(@NotNull T value) {
        return new MutableCell<>(value);
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
