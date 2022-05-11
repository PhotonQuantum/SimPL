package simpl.parser.ast;

import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Rec extends Expr {

    public final Symbol x;
    public final Expr e;

    public Rec(Symbol x, Expr e) {
        this.x = x;
        this.e = e;
    }

    public String toString() {
        return "(rec " + x + "." + e + ")";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        /* W(Γ, x:α; e) ⊢ (S; τ) */
        var a = new TypeVar(true);
        var W = e.typeCheck(TypeEnv.of(E, x, a));

        /* τ ~ (S α) ~> S' */
        var S_ = W.ty().unify(W.subst().applyOn(a));

        /* W(Γ; (rec x e)) = (S'∘S; S'∘S α) */
        var subst = S_.compose(W.subst());
        return TypeResult.of(subst, subst.applyOn(a));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // E-Rec
        var recVal = new RecValue(s.E, x, e);
        return e.eval(State.of(s.E.extend(x, recVal), s.M, s.p));
    }
}
