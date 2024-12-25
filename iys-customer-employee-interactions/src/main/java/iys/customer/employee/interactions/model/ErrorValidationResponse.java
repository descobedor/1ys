package iys.customer.employee.interactions.model;

import java.util.List;

public record ErrorValidationResponse(String message, List<ValidationError> validations) {

    public record ValidationError(String value, String resolver) {
    }

}
