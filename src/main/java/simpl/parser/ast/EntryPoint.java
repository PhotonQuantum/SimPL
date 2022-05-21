package simpl.parser.ast;

import kala.collection.immutable.ImmutableMap;

public interface EntryPoint {
    /**
     * @return pragmas for this entry point
     */
    ImmutableMap<String, String> pragmas();

    /**
     * @return body of this entry point
     */
    Expr expr();
}
