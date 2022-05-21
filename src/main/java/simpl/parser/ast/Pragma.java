package simpl.parser.ast;

import kala.collection.immutable.ImmutableMap;
import kala.collection.mutable.MutableMap;
import kala.tuple.Tuple;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Pragma(String pragma, String value, EntryPoint next) implements EntryPoint {
    @Contract(pure = true)
    public @NotNull String toString() {
        if (value == null)
            return "{#- " + pragma + " -#}\n" + next;
        return "{#- " + pragma + ": " + value + " -#}\n" + next;
    }

    @Override
    public @NotNull ImmutableMap<String, String> pragmas() {
        MutableMap<String, String> pragmas = MutableMap.create();
        EntryPoint curr = this;
        while (curr instanceof Pragma p) {
            pragmas.set(Tuple.of(p.pragma, p.value));
            curr = p.next;
        }
        return pragmas.toImmutableMap();
    }

    @Override
    public Expr expr() {
        EntryPoint curr = this;
        while (curr instanceof Pragma p) {
            curr = p.next;
        }
        return (Expr) curr;
    }
}
