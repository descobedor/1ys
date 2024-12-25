package iys.customer.employee.interactions.common.exception;

public class RequestNotAllowedException extends RuntimeException {

    public RequestNotAllowedException(String message) {
        super(message);
    }
}
