package simpl.typing;

import kala.collection.immutable.ImmutableCompactSet;
import kala.collection.immutable.ImmutableSeq;
import kala.collection.immutable.ImmutableSet;
import kala.tuple.Tuple2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import simpl.parser.Symbol;

public class TypeEnv {
    public static final TypeEnv DEFAULT = withBuiltIns();
    private final TypeEnv E;
    protected final Symbol x;
    protected final TypeScheme t;

    private TypeEnv(TypeEnv e, Symbol x, TypeScheme t) {
        E = e;
        this.x = x;
        this.t = t;
    }

    protected TypeEnv() {
        E = null;
        this.x = null;
        this.t = null;
    }

    private static TypeEnv withBuiltIns() {
        var tyFst = new TypeVar(true);
        var tySnd = new TypeVar(true);
        var tyHd = new TypeVar(true);
        var tyTl = new TypeVar(true);

        var defaultTypes = ImmutableSeq.of(
                // fst: τ1 × τ2 → τ1
                Tuple2.of(Symbol.of("fst"), ArrowType.of(PairType.of(tyFst, new TypeVar(true)), tyFst)),
                // snd: τ1 × τ2 → τ2
                Tuple2.of(Symbol.of("snd"), ArrowType.of(PairType.of(new TypeVar(true), tySnd), tySnd)),
                // hd: τ list → τ
                Tuple2.of(Symbol.of("hd"), ArrowType.of(ListType.of(tyHd), tyHd)),
                // tl: τ list → τ list
                Tuple2.of(Symbol.of("tl"), ArrowType.of(ListType.of(tyTl), ListType.of(tyTl))),
                // iszero: int → bool
                Tuple2.of(Symbol.of("iszero"), ArrowType.of(IntType.INSTANCE, BoolType.INSTANCE)),
                // succ: int → int
                Tuple2.of(Symbol.of("succ"), ArrowType.of(IntType.INSTANCE, IntType.INSTANCE)),
                // pred: int → int
                Tuple2.of(Symbol.of("pred"), ArrowType.of(IntType.INSTANCE, IntType.INSTANCE))
        );

        return defaultTypes.foldLeft(new TypeEnv(), (E, symTy) -> TypeEnv.of(E, symTy._1, symTy._2.generalizeWith(E)));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull TypeEnv of(final TypeEnv E, final Symbol x, final TypeScheme t) {
        return new TypeEnv(E, x, t);
    }

    public TypeScheme get(Symbol x) {
        if (x == this.x)
            return t;
        if (E == null)
            return null;
        return E.get(x);
    }

    public String toString() {
        return x + ":" + t + ";" + E;
    }

    public ImmutableSet<Symbol> typeVars() {
        if (E == null)
            return ImmutableCompactSet.empty();
        return E.typeVars().added(x);
    }
}
