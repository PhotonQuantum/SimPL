package simpl.interpreter;

import kala.collection.immutable.ImmutableMap;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Config(Boolean strict, Boolean gc, Integer stackSize, Integer heapSize) {
    public static @NotNull Config of(@NotNull ImmutableMap<String, String> pragma) {
        var strict = pragma.containsKey("Strict");
        var gc = !pragma.containsKey("DisableGC");
        var stackSize = Integer.valueOf(pragma.getOrDefault("StackSize", "0"));
        var heapSize = Integer.valueOf(pragma.getOrDefault("HeapSize", "65536"));
        return new Config(strict, gc, stackSize, heapSize);
    }

    @Contract("_ -> new")
    public @NotNull Config join(@NotNull Config config) {
        return new Config(config.strict != null ? config.strict : this.strict,
                config.gc != null ? config.gc : this.gc,
                config.stackSize != null ? config.stackSize : this.stackSize,
                config.heapSize != null ? config.heapSize : this.heapSize);
    }
}
