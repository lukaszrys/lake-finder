package pl.fishing.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UsernameNotUniqueException extends RuntimeException {

    public UsernameNotUniqueException() {
        super();
    }

    public UsernameNotUniqueException(String message){
        super(message);
    }
}
