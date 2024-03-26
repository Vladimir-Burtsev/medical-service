package academy.kata.mis.medicalservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthException extends AuthenticationException {
    public AuthException(String msg) {
        super(msg);
    }
}