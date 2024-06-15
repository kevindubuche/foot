package com.foot.team_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler for PlayerNotFoundException and TeamNotFoundException.
 */
@RestControllerAdvice
public class NotFoundAdvice {

    /**
     * Handles PlayerNotFoundException.
     *
     * @param ex the PlayerNotFoundException
     * @return the error message
     */
    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String playerNotFoundHandler(PlayerNotFoundException ex) {
        return ex.getMessage();
    }

    /**
     * Handles TeamNotFoundException.
     *
     * @param ex the TeamNotFoundException
     * @return the error message
     */
    @ExceptionHandler(TeamNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String teamNotFoundHandler(TeamNotFoundException ex) {
        return ex.getMessage();
    }
}
