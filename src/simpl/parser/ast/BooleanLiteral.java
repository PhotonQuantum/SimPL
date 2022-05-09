package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.BoolType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class BooleanLiteral extends Expr {

    public final boolean b;

    public BooleanLiteral(boolean b) {
        this.b = b;
    }

    public String toString() {
        return "" + b;
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        return TypeResult.of(BoolType.INSTANCE);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return new BoolValue(b);
    }
}
