package simpl.interpreter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import simpl.interpreter.lib.Fst;
import simpl.interpreter.lib.Hd;
import simpl.interpreter.lib.Snd;
import simpl.interpreter.lib.Tl;
import simpl.interpreter.pcf.IsZero;
import simpl.interpreter.pcf.Pred;
import simpl.interpreter.pcf.Succ;
import simpl.parser.Symbol;

public class State {
    public final Env E;
    public final Mem M;
    public final Int p;
    public final Config config;

    public static final Env initialEnv =
            Env.of(Env.EMPTY, Symbol.of("fst"), Fst.INSTANCE)
                    .extend(Symbol.of("snd"), Snd.INSTANCE)
                    .extend(Symbol.of("hd"), Hd.INSTANCE)
                    .extend(Symbol.of("tl"), Tl.INSTANCE)
                    .extend(Symbol.of("iszero"), IsZero.INSTANCE)
                    .extend(Symbol.of("pred"), Pred.INSTANCE)
                    .extend(Symbol.of("succ"), Succ.INSTANCE);

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

    public static @NotNull State create(Config c) {
        return State.of(initialEnv, new Mem(), new Int(0), c);
    }
}
