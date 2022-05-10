package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.IntType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class IntegerLiteral extends Expr {

    public final int n;

    public IntegerLiteral(int n) {
        this.n = n;
    }

    public String toString() {
        return "" + n;
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        return TypeResult.of(IntType.INSTANCE);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // E-Int
        return new IntValue(n);
    }
}
