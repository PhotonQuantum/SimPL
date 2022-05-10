package simpl.typing;

import simpl.parser.Symbol;

public class DefaultTypeEnv extends TypeEnv {

    private final TypeEnv E;

    public DefaultTypeEnv() {
        E = empty;
    }

    @Override
    public Type get(Symbol x) {
        return E.get(x);
    }
}
