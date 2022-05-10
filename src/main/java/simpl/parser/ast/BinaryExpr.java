package simpl.parser.ast;

public abstract class BinaryExpr extends Expr {

    public final Expr l;
    public final Expr r;

    public BinaryExpr(Expr l, Expr r) {
        this.l = l;
        this.r = r;
    }
}
