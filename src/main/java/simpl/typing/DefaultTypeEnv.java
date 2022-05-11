package simpl.typing;

import simpl.parser.Symbol;

public class DefaultTypeEnv extends TypeEnv {

    private final TypeEnv E;

    public DefaultTypeEnv() {
        // TODO need to type builtin functions (require let-poly)
        E = EMPTY;
    }

    @Override
    public Type get(Symbol x) {
        return E.get(x);
    }
}
