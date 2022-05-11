package simpl.parser.ast;

import simpl.interpreter.NilValue;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.ListType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Nil extends Expr {

    public String toString() {
        return "nil";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) {
        var T = new TypeVar(true);
        return TypeResult.of(ListType.of(T));
    }

    @Override
    public Value eval(State s) {
        // E-Nil
        return NilValue.INSTANCE;
    }
}
