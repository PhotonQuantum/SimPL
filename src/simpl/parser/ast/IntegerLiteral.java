package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.*;

public class IntegerLiteral extends Expr {

    public final int n;

    public IntegerLiteral(int n) {
        this.n = n;
    }

    public String toString() {
        return "" + n;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        return TypeResult.of(IntType.INSTANCE);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return new IntValue(n);
    }
}
