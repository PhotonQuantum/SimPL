package simpl.parser.ast;

import kala.collection.immutable.ImmutableMap;

public interface EntryPoint {
    ImmutableMap<String, String> pragmas();

    Expr expr();
}
