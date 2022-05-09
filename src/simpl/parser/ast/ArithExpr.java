package simpl.parser.ast;

import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public abstract class ArithExpr extends BinaryExpr {

    public ArithExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        return null;
    }
}
