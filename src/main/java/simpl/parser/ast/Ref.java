package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Ref extends UnaryExpr {

    public Ref(Expr e) {
        super(e);
    }

    public String toString() {
        return "(ref " + e + ")";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        /* W(Γ; e) = (S; τ) */
        var W = e.typeCheck(E);

        /* W(Γ; ref e) = (S; ref τ) */
        return TypeResult.of(W.subst(), RefType.of(W.ty()));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // E-Ref
        var value = e.eval(s);

        var cell = s.M.allocate(s, value);
        System.err.println("Allocating ref " + e + " = " + value + " got cell " + cell.unsafeGetPointer());

        return new RefValue(cell);
    }
}
