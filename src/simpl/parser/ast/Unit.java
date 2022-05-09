package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.UnitValue;
import simpl.interpreter.Value;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.UnitType;

public class Unit extends Expr {

    public String toString() {
        return "()";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        return TypeResult.of(UnitType.INSTANCE);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return UnitValue.INSTANCE;
    }
}
