package simpl.parser.ast;

import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public abstract class RelExpr extends BinaryExpr {

    public RelExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        return null;
    }
}
