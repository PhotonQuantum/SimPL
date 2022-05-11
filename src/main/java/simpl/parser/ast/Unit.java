package simpl.parser.ast;

import simpl.interpreter.State;
import simpl.interpreter.UnitValue;
import simpl.interpreter.Value;
import simpl.typing.TypeEnv;
import simpl.typing.TypeResult;
import simpl.typing.UnitType;

public class Unit extends Expr {

    public String toString() {
        return "()";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) {
        return TypeResult.of(UnitType.INSTANCE);
    }

    @Override
    public Value eval(State s) {
        return UnitValue.INSTANCE;
    }
}
