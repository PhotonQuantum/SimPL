package simpl.interpreter;

import simpl.interpreter.lib.Fst;
import simpl.interpreter.lib.Hd;
import simpl.interpreter.lib.Snd;
import simpl.interpreter.lib.Tl;
import simpl.interpreter.pcf.IsZero;
import simpl.interpreter.pcf.Pred;
import simpl.interpreter.pcf.Succ;
import simpl.parser.Symbol;

public class InitialState extends State {

    public static final Env initialEnv =
            Env.of(Env.EMPTY, Symbol.of("fst"), Fst.INSTANCE)
                    .extend(Symbol.of("snd"), Snd.INSTANCE)
                    .extend(Symbol.of("hd"), Hd.INSTANCE)
                    .extend(Symbol.of("tl"), Tl.INSTANCE)
                    .extend(Symbol.of("iszero"), IsZero.INSTANCE)
                    .extend(Symbol.of("pred"), Pred.INSTANCE)
                    .extend(Symbol.of("succ"), Succ.INSTANCE);

    public InitialState() {
        super(initialEnv, new Mem(), new Int(0));
    }

}
