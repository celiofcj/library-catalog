package com.library.catalog.exception;

import com.library.catalog.services.exceptions.InvalidArgumentException;
import com.library.catalog.services.exceptions.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllUncaughtExceptions(Exception exception, WebRequest request){
        final String message = "Unknow server error.";
        log.error(message, exception);
        ProblemDetail body = createProblemDetail(exception, HttpStatus.INTERNAL_SERVER_ERROR, message,
                null, null, request);
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleObjectNotFoundException(ObjectNotFoundException exception, WebRequest request){
        final String message = exception.getMessage();
        log.error(message, exception);
        ProblemDetail body = createProblemDetail(exception, HttpStatus.NOT_FOUND, message,
                null, null, request);
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException exception,
                                                                        WebRequest request){
        final String message = exception.getMostSpecificCause().getMessage();
        HttpStatus status;
        if(message.contains("Duplicate entry")){
            status = HttpStatus.CONFLICT;
        }
        else {
            status = HttpStatus.BAD_REQUEST;
        }
        log.error(message, exception);
        ProblemDetail body = createProblemDetail(exception, status, message,
                null, null, request);
        return handleExceptionInternal(exception, body, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<Object> handleInvalidArgumentException(InvalidArgumentException exception,
                                                                           WebRequest request){
        final String message = exception.getMessage();
        log.error(message, exception);
        ProblemDetail body = createProblemDetail(exception, HttpStatus.BAD_REQUEST, message,
                null, null, request);
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  @NonNull HttpHeaders headers, @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        StringBuilder errorMessages = new StringBuilder("Invalid data: ");
        for(FieldError fieldError : exception.getBindingResult().getFieldErrors()){
            errorMessages.append(fieldError.getField()).append(" ").
                    append(fieldError.getDefaultMessage()).append("; ");
        }
        final String message = errorMessages.toString();
        log.error(message, exception);
        ProblemDetail body = createProblemDetail(exception, status, message,
                null, null, request);
        return handleExceptionInternal(exception, body, new HttpHeaders(), status, request);
    }
}
