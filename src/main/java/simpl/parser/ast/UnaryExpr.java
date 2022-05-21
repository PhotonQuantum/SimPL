package simpl.parser.ast;

public abstract class UnaryExpr implements Expr {

    public final Expr e;

    protected UnaryExpr(Expr e) {
        this.e = e;
    }
}
