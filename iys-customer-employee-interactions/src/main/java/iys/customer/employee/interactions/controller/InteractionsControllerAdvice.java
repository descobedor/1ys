package iys.customer.employee.interactions.controller;

import iys.customer.employee.interactions.common.exception.*;
import iys.customer.employee.interactions.model.ErrorResponse;
import iys.customer.employee.interactions.model.ErrorValidationResponse;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice(value = "iys.customer.employee.interactions.controller")
public class InteractionsControllerAdvice {

    @ExceptionHandler(value = InteractionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInteractionNotFound(InteractionNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = RequestHasAlreadyBeenMadeException.class)
    public ResponseEntity<ErrorResponse> handleRequestHasAlreadyBeenMadeException(RequestHasAlreadyBeenMadeException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(value = RequestNotAllowedBillsIsWaiting.class)
    public ResponseEntity<ErrorResponse> handleRequestNotAllowedBillsIsWaiting(RequestNotAllowedBillsIsWaiting ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(value = SpaceNotCreatedException.class)
    public ResponseEntity<ErrorResponse> handleSpaceNotCreated(SpaceNotCreatedException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(value = ReferenceDuplicatedException.class)
    public ResponseEntity<ErrorResponse> handleReferenceDuplicatedException(ReferenceDuplicatedException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(value = HandlerMethodValidationException.class)
    public ResponseEntity<ErrorValidationResponse> handleException(HandlerMethodValidationException ex) {

        Stream<ErrorValidationResponse.ValidationError> validationErrorStream =
                ex.getAllValidationResults().stream()
                        .filter(a -> a.getArgument() != null)
                        .map(error -> new ErrorValidationResponse
                                .ValidationError(error.getArgument().toString(),
                                error.getResolvableErrors().stream()
                                        .map(MessageSourceResolvable::getDefaultMessage)
                                        .collect(Collectors.joining())));
        return ResponseEntity.badRequest().body(new ErrorValidationResponse(
                ex.getMessage(), validationErrorStream.toList()
        ));
    }

}
