package simpl.interpreter;

public class RuntimeError extends Exception {

    private static final long serialVersionUID = -8801124990278919816L;

    public RuntimeError(String message) {
        super(message);
    }
}
