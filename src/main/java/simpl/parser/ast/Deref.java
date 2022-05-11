package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.*;

public class Deref extends UnaryExpr {

    public Deref(Expr e) {
        super(e);
    }

    public String toString() {
        return "!" + e;
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        /* W(Γ; e) ⊢ (S; τ) */
        var W = e.typeCheck(E);

        /* τ ~ ref α ~> S' */
        var a = new TypeVar(true);
        var S_ = W.ty().unify(RefType.of(a));

        /* W(Γ; !e) = (S'∘S; S' α) */
        return TypeResult.of(S_.compose(W.subst()), S_.applyOn(a));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // E-Deref
        if (e.eval(s) instanceof RefValue ref) {
            return s.M.get(ref.p);
        }
        throw new RuntimeError(e + " is not a reference");
    }
}
