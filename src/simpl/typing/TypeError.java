package simpl.typing;

import java.io.Serial;

public class TypeError extends Exception {

    @Serial
    private static final long serialVersionUID = -355382387246978981L;

    public TypeError(String message) {
        super(message);
    }
}
