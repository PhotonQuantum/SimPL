package simpl.interpreter;

import kala.collection.immutable.ImmutableMap;
import org.jetbrains.annotations.NotNull;

public record Config(Boolean strict, Integer stackSize) {
    public static final Config DEFAULT = new Config(false, 0);

    public static @NotNull Config of(@NotNull ImmutableMap<String, String> pragma) {
        var strict = pragma.containsKey("Strict");
        var stackSize = Integer.valueOf(pragma.getOrDefault("StackSize", "0"));
        return new Config(strict, stackSize);
    }
}
