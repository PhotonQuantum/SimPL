package simpl.interpreter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class State {
    public final Env E;
    public final Mem M;
    public final Int p;
    public final Config config;

    protected State(Env E, Mem M, Int p, Config config) {
        this.E = E;
        this.M = M;
        this.p = p;
        this.config = config;
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull State of(Env E, Mem M, Int p, Config c) {
        return new State(E, M, p, c);
    }

}
