package ai.leantech.delivery.controller.advice;

import ai.leantech.delivery.controller.model.ErrorResponse;
import ai.leantech.delivery.exception.NoSuchOrderStatusException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchOrderStatusException.class)
    public ErrorResponse handleNoSuchOrderStatusException(NoSuchOrderStatusException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({JsonPatchException.class, JsonProcessingException.class})
    public ErrorResponse handleJsonException(Exception e) {
        log.error(e.getMessage());
        return new ErrorResponse("Server error. Please contact your administrator");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleUnfoundExceptions(Exception e) {
        log.error(e.getMessage());
        return new ErrorResponse("Server error. Please contact your administrator");
    }

}
