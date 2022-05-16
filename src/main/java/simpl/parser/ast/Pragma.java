package simpl.parser.ast;

import kala.collection.immutable.ImmutableMap;
import kala.collection.mutable.MutableMap;
import kala.tuple.Tuple;

public class Pragma implements EntryPoint {
    private final String pragma;
    private final String value;
    private final EntryPoint next;

    public Pragma(String pragma, String value, EntryPoint next) {
        this.pragma = pragma;
        this.value = value;
        this.next = next;
    }

    public String toString() {
        if (value == null)
            return "{#- " + pragma + " -#}\n" + next;
        return "{#- " + pragma + ": " + value + " -#}\n" + next;
    }

    @Override
    public ImmutableMap<String, String> pragmas() {
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
