package simpl.typing;

import java.io.Serial;

public class TypeCircularityError extends TypeError {

    @Serial
    private static final long serialVersionUID = -5845539927612802390L;

    public TypeCircularityError() {
        super("Circularity");
    }
}
