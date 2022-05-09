package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Nil extends Expr {

    public String toString() {
        return "nil";
    }

    @Override public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        return null;
    }

    @Override public Value eval(State s) throws RuntimeError {
        // TODO
        return null;
    }
}
