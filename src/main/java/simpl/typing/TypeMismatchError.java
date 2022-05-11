package simpl.typing;

import java.io.Serial;

public class TypeMismatchError extends TypeError {

    @Serial
    private static final long serialVersionUID = -9010997809568642250L;

    public TypeMismatchError(Type lhs, Type rhs) {
        super("Mismatch: Unable to unify " + lhs + " with " + rhs);
    }
}
