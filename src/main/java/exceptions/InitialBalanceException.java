package exceptions;

/**
 * Created by DOTIN SCHOOL 4 on 9/14/2016.
 */
public class InitialBalanceException extends RuntimeException {
    public InitialBalanceException(String message) {
        super(message);
    }

    public InitialBalanceException() {
        super();
    }
}
