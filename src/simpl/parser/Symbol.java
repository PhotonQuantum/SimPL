package simpl.parser;

import java.util.HashMap;

public class Symbol {

    private static final HashMap<String, Symbol> dict = new HashMap<String, Symbol>();
    private final String name;

    private Symbol(String n) {
        name = n;
    }

    /**
     * Make return the unique symbol associated with a string. Repeated calls to <tt>symbol("abc")</tt> will return the
     * same Symbol.
     */
    public static Symbol symbol(String n) {
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
}
