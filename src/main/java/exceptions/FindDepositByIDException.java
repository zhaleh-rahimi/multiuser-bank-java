package exceptions;

/**
 * Created by DOTIN SCHOOL 4 on 9/14/2016.
 */
public class FindDepositByIDException extends RuntimeException {
    public FindDepositByIDException(String message) {
        super(message);
    }

    public FindDepositByIDException() {
        super();
    }
}
