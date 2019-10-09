package com.haud.exception;

import com.haud.utils.Errors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.haud.utils.Errors.Error;

/**
 * @author webwerks
 * <p>
 * This is global exception handling class.Any exception which occur in
 * Application will be mapped to suitable method from this class. Based on it,
 * Error object will be populated and that Error object will be returned.
 */
@RestControllerAdvice
@Slf4j
public class ExceptionMapper {
    public ExceptionMapper() {
    }

    /**
     * This method will populate Errors object from input clientException.
     *
     * @param clientException
     * @return Errors object with suitable title and error message, created from
     * ClientException.
     */
    @ExceptionHandler(ClientException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errors handleSecurityException(ClientException clientException) {

        log.info("ClientException " + clientException.getMessage());

        return clientException.getErrors();
    }

    /**
     * This method will populate Errors object from input ex.
     *
     * @param ex
     * @return Errors object with suitable title and error message
     */
    @ExceptionHandler(JpaObjectRetrievalFailureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errors handleEntityNotFoundException(JpaObjectRetrievalFailureException ex) {

        log.error("JpaObjectRetrievalFailureException" + ex.getMessage());

        Errors errors = new Errors();
        errors.addError(Error.builder().title("Bad Request").detail("Entity not found").build());
        return errors;
    }

    /**
     * @param ex
     * @return Errors when the client attempt a invalid input request or
     * Micro-services connection exception.
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Errors runtimeException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        Errors errors = new Errors();
        errors.addError(Error.builder().title("Invalid Attempt!").detail("Something Went Wrong.").build());
        return errors;
    }

    /**
     * @param ex
     * @return Errors in case of some unhandled exceptions.
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Errors unexpectedException(Exception ex) {
        log.error(ex.getMessage(), ex);
        log.error("Unexpected error occurred!");
        Errors errors = new Errors();
        errors.addError(Error.builder().title("Internal Error").detail("Something Went Wrong.").build());
        return errors;
    }

    /**
     * This method will populate Errors object from input ex.
     *
     * @param ex
     * @return Errors object with suitable title and error message,created from
     * HttpRequestMethodNotSupportedException
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errors handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {

        log.info("HttpRequestMethodNotSupportedException " + ex.getMessage());

        Errors errors = new Errors();
        errors.addError(Error.builder().title("Bad Request").detail(ex.getMessage()).build());
        return errors;
    }
}
