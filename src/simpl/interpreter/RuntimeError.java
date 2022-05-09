package simpl.interpreter;

import java.io.Serial;

public class RuntimeError extends Exception {

    @Serial
    private static final long serialVersionUID = -8801124990278919816L;

    public RuntimeError(String message) {
        super(message);
    }
}
