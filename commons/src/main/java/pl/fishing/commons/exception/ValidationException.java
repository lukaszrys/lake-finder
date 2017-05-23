package pl.fishing.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException{
    public ValidationException() {
        this("Bad request");
    }

    public ValidationException(String message) {
        super(message);
    }
}
