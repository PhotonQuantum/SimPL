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
            Env.of(Env.empty, Symbol.of("fst"), new Fst())
                    .append(Symbol.of("snd"), new Snd())
                    .append(Symbol.of("hd"), new Hd())
                    .append(Symbol.of("tl"), new Tl())
                    .append(Symbol.of("iszero"), new IsZero())
                    .append(Symbol.of("pred"), new Pred())
                    .append(Symbol.of("succ"), new Succ());

    public InitialState() {
        super(initialEnv, new Mem(), new Int(0));
    }

}
