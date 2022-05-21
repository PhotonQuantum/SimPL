package simpl.parser.ast;

import kala.collection.immutable.ImmutableMap;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public interface Expr extends EntryPoint {

    /**
     * Type check this expression.
     *
     * @param E The type environment.
     * @return Constraints and type of this expression.
     * @throws TypeError If this expression is ill-typed.
     */
    TypeResult typeCheck(TypeEnv E) throws TypeError;

    /**
     * Evaluate this expression in the given state.
     *
     * @param s The state to evaluate in.
     * @return The value of this expression.
     * @throws RuntimeError If this expression cannot be evaluated.
     */
    Value eval(State s) throws RuntimeError;

    @Override
    default ImmutableMap<String, String> pragmas() {
        return ImmutableMap.empty();
    }

    @Override
    default Expr expr() {
        return this;
    }
}
