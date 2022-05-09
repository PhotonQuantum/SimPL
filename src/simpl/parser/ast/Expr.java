package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public abstract class Expr {

    public abstract TypeResult typeCheck(TypeEnv E) throws TypeError;

    /**
     * Evaluate this expression in the given state.
     *
     * @param s The state to evaluate in.
     * @return The value of this expression.
     * @throws RuntimeError If this expression cannot be evaluated.
     */
    public abstract Value eval(State s) throws RuntimeError;
}
