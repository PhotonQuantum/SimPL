package simpl.interpreter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import simpl.parser.Symbol;

public class State {

    public final Env E;
    public final Mem M;
    public final Int p;

    protected State(Env E, Mem M, Int p) {
        this.E = E;
        this.M = M;
        this.p = p;
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull State of(Env E, Mem M, Int p) {
        return new State(E, M, p);
    }

    public State extend(Symbol x, Value eval) {
        return State.of(E.extend(x, eval), M, p);
    }
}
