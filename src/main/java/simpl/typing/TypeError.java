package simpl.typing;

import java.io.Serial;

public class TypeError extends Exception {
    String checkTrace = "";

    @Serial
    private static final long serialVersionUID = -355382387246978981L;

    public TypeError(String message) {
        super(message);
    }

    public void appendTrace(String message) {
        checkTrace += "\n    " + message;
    }

    public void appendTrace(Type lhs, Type rhs) {
        appendTrace("when unifying " + lhs + " with " + rhs);
    }

    public String getCheckTrace() {
        return checkTrace;
    }
}
