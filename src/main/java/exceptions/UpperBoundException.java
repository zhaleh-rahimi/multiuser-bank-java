package exceptions;

/**
 * Created by DOTIN SCHOOL 4 on 9/14/2016.
 */
public class UpperBoundException extends RuntimeException {
    public UpperBoundException(String message) {
        super(message);
    }

    public UpperBoundException() {
        super();
    }
}
