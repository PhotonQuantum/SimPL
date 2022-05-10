package simpl.parser;

import java.io.Serial;

public class SyntaxError extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -382128850133314371L;

    public SyntaxError(String message, int line, int column) {
        super("" + line + "," + column + ": " + message);
    }
}
