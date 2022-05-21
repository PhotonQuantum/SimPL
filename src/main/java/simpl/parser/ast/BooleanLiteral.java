package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.BoolType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeResult;

public record BooleanLiteral(boolean b) implements Expr {

    public String toString() {
        return "" + b;
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) {
        return TypeResult.of(BoolType.INSTANCE);
    }

    @Override
    public Value eval(State s) {
        return new BoolValue(b);
    }
}
