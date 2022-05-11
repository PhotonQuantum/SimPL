package simpl.parser;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class Symbol implements Comparable<Symbol> {

    private static final HashMap<String, Symbol> dict = new HashMap<>();
    private final String name;

    private Symbol(String n) {
        name = n;
    }

    /**
     * Make return the unique symbol associated with a string. Repeated calls to <tt>symbol("abc")</tt> will return the
     * same Symbol.
     */
    public static @NotNull Symbol of(@NotNull String n) {
        String u = n.intern();
        Symbol s = dict.get(u);
        if (s == null) {
            s = new Symbol(u);
            dict.put(u, s);
        }
        return s;
    }

    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        // `Symbol` is interned.
        return this == o;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(@NotNull Symbol o) {
        return name.compareTo(o.name);
    }
}
