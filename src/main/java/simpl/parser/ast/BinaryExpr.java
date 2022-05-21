package simpl.parser.ast;

public abstract class BinaryExpr implements Expr {

    public final Expr l;
    public final Expr r;

    protected BinaryExpr(Expr l, Expr r) {
        this.l = l;
        this.r = r;
    }
}
