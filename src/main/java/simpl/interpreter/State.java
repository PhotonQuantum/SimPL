package simpl.interpreter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import simpl.interpreter.lib.*;
import simpl.interpreter.pcf.IsZero;
import simpl.interpreter.pcf.Pred;
import simpl.interpreter.pcf.Succ;
import simpl.parser.Symbol;

public class State {
    public static final Env initialEnv =
            Env.EMPTY.extend(Symbol.of("fst"), Fst.INSTANCE)
                    .extend(Symbol.of("snd"), Snd.INSTANCE)
                    .extend(Symbol.of("hd"), Hd.INSTANCE)
                    .extend(Symbol.of("tl"), Tl.INSTANCE)
                    .extend(Symbol.of("shd"), SHd.INSTANCE)
                    .extend(Symbol.of("stl"), STl.INSTANCE)
                    .extend(Symbol.of("iszero"), IsZero.INSTANCE)
                    .extend(Symbol.of("pred"), Pred.INSTANCE)
                    .extend(Symbol.of("succ"), Succ.INSTANCE);
    public final Env E;
    public final Mem M;
    public final Config config;

    protected State(Env E, Mem M, Config config) {
        this.E = E;
        this.M = M;
        this.config = config;
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull State of(Env E, Mem M, Config c) {
        return new State(E, M, c);
    }

    public static @NotNull State create(Config c) {
        var E = initialEnv;
        var M = new Mem();
        M.pushRoot(E);
        return State.of(E, M, c);
    }
}
