package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.BoolType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Not extends UnaryExpr {

    public Not(Expr e) {
        super(e);
    }

    public String toString() {
        return "(not " + e + ")";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        /* W(Γ; e) = (S; τ) */
        var W = e.typeCheck(E);

        /* τ ~ bool ~> S' */
        var S_ = W.ty().unify(BoolType.INSTANCE);

        /* W(Γ; ¬e) = (S'∘S; bool) */
        return TypeResult.of(S_.compose(W.subst()), BoolType.INSTANCE);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // E-Not
        if (e.eval(s) instanceof BoolValue boolValue) {
            return new BoolValue(!boolValue.b);
        }
        throw new RuntimeError(e + " is not a boolean");
    }
}
