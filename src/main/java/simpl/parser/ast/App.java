package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.*;

public class App extends BinaryExpr {

    public App(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " " + r + ")";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        /*
            W(Γ; l) ⊢ (S1; τ1)
            W(S1∘Γ; r) ⊢ (S2; τ2)
         */
        var W1 = l.typeCheck(E);
        var W2 = r.typeCheck(W1.subst().applyOn(E));

        /* S2 τ1 ~ (τ2 → α) ~> S3 */
        var a = new TypeVar(true);
        var S3 = W2.subst().applyOn(W1.ty()).unify(ArrowType.of(W2.ty(), a));

        /* S = S3∘S2∘S1 */
        var S = S3.compose(W2.subst()).compose(W1.subst());

        /* W(Γ; r l) = (S; S3 α) */
        return TypeResult.of(S, S3.applyOn(a));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // E-App
        if (l.eval(s) instanceof FunValue lhs) {
            // Call by value
            var rhs = r.eval(s); // Can't be inlined to next line because of side effects.
            var E = Env.of(lhs.E, lhs.x, rhs);
            return lhs.e.eval(State.of(E, s.M, s.p));
            // TODO Call by name
        }
        throw new RuntimeError(l + " is not a function");
    }
}
