package simpl.interpreter;

import simpl.parser.Symbol;
import simpl.parser.ast.Expr;

public record RecValue(Env E, Symbol x, Expr e) implements Value {

    @Override
    public boolean equals(State s, Value other) {
        return false;
    }
}
