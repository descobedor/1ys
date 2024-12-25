package iys.customer.employee.interactions.common.exception;

public class RequestHasAlreadyBeenMadeException extends RuntimeException {

    public RequestHasAlreadyBeenMadeException(String message) {
        super(message);
    }
}
