package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.IntType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeResult;

public record IntegerLiteral(int n) implements Expr {

    public String toString() {
        return "" + n;
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) {
        return TypeResult.of(IntType.INSTANCE);
    }

    @Override
    public Value eval(State s) {
        // E-Int
        return new IntValue(n);
    }
}
