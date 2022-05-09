package simpl.parser;

public class SyntaxError extends RuntimeException {

    private static final long serialVersionUID = -382128850133314371L;

    public SyntaxError(String message, int line, int column) {
        super("" + line + "," + column + ": " + message);
    }
}
