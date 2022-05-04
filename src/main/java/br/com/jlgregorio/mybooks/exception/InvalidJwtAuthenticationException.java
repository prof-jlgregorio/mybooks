package br.com.jlgregorio.mybooks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidJwtAuthenticationException extends AuthenticationServiceException {

    public InvalidJwtAuthenticationException(String exception){
        super(exception);
    }


}
