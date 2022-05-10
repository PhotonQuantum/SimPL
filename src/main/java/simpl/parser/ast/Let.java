package simpl.parser.ast;

import org.jetbrains.annotations.NotNull;
import simpl.interpreter.Env;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Let extends Expr {

    public final Symbol x;
    public final Expr e1;
    public final Expr e2;

    public Let(Symbol x, Expr e1, Expr e2) {
        this.x = x;
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(let " + x + " = " + e1 + " in " + e2 + ")";
    }

    @Override
    public TypeResult typeCheck(TypeEnv E) throws TypeError {
        // TODO
        return null;
    }

    @Override
    public Value eval(@NotNull State s) throws RuntimeError {
        // E-Let
        // Call by value
        var v1 = e1.eval(s);
        var E = Env.of(s.E, x, v1);
        return e2.eval(State.of(E, s.M, s.p));
        // TODO Call by name
    }
}
