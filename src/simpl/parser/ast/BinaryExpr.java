package simpl.parser.ast;

public abstract class BinaryExpr extends Expr {

    public Expr l, r;

    public BinaryExpr(Expr l, Expr r) {
        this.l = l;
        this.r = r;
    }
}
