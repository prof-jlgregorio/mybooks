package br.com.jlgregorio.mybooks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@RestController
public class HandlerException {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ResponseException> handleAllExceptions(Exception ex, WebRequest request){
        ResponseException responseException = new ResponseException(
                new Date(),
                ex.getMessage(),
                request.getDescription(false) );
        return new ResponseEntity<>(responseException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ResponseException> handleNotFoundException(Exception ex, WebRequest request){
        String message;
        if (ex.getMessage() == null) {
            message = "Resource Not Found!";
        } else {
            message =  ex.getMessage();
        }
        ResponseException responseException = new ResponseException(
                new Date(),
                message,
                request.getDescription(false) );
        return new ResponseEntity<>(responseException, HttpStatus.NOT_FOUND);
    }






}
