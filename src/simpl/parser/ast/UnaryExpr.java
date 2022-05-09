package simpl.parser.ast;

public abstract class UnaryExpr extends Expr {

    public Expr e;

    public UnaryExpr(Expr e) {
        this.e = e;
    }
}
