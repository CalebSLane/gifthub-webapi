package ca.csl.gifthub.web.api.modules;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import ca.csl.gifthub.web.api.modules.error.ErrorDTO;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    private static final Logger LOG = LogManager.getLogger(RestResponseEntityExceptionHandler.class);
    private final MessageSource messageSource;

    public RestResponseEntityExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void mediaTypeExceptionHandler(HttpMediaTypeNotAcceptableException ex, Locale locale) {
        LOG.warn("HttpMediaTypeNotAcceptableException occured");
        // don't return anything, could not agree on return type
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDTO noHandlerExceptionHandler(NoHandlerFoundException ex, Locale locale) {
        LOG.warn("NoHandlerFoundException occured");
        return new ErrorDTO(this.messageSource.getMessage("error.server.api.notfound", null, locale));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO runtimeExceptionHandler(RuntimeException ex, Locale locale) {
        LOG.warn("RuntimeException occured");
        return new ErrorDTO(this.messageSource.getMessage("error.server.api.internal", null, locale));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO exceptionHandler(Exception ex, Locale locale) {
        LOG.warn("Exception occured");
        return new ErrorDTO(this.messageSource.getMessage("error.server.api.internal", null, locale));
    }

}
