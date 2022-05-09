package simpl.typing;

import simpl.parser.Symbol;

public class DefaultTypeEnv extends TypeEnv {

    private TypeEnv E;

    public DefaultTypeEnv() {
        // TODO
    }

    @Override public Type get(Symbol x) {
        return E.get(x);
    }
}
