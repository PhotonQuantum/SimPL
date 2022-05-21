package simpl.interpreter;

import kala.collection.immutable.ImmutableMap;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Config(Boolean strict, Float gcThreshold, Integer stackSize, Integer heapSize) {
    public static @NotNull Config of(@NotNull ImmutableMap<String, String> pragma) {
        var strict = pragma.containsKey("Strict");
        var gcThreshold = Float.valueOf(pragma.getOrDefault("GCThreshold", "0.7"));
        var stackSize = Integer.valueOf(pragma.getOrDefault("StackSize", "10240000"));
        var heapSize = Integer.valueOf(pragma.getOrDefault("HeapSize", "65536"));
        return new Config(strict, gcThreshold, stackSize, heapSize);
    }

    @Contract("_ -> new")
    public @NotNull Config join(@NotNull Config config) {
        return new Config(config.strict != null ? config.strict : this.strict,
                config.gcThreshold != null ? config.gcThreshold : this.gcThreshold,
                config.stackSize != null ? config.stackSize : this.stackSize,
                config.heapSize != null ? config.heapSize : this.heapSize);
    }
}
