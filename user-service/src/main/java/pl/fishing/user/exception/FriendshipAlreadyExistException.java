package pl.fishing.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class FriendshipAlreadyExistException extends RuntimeException {

    public FriendshipAlreadyExistException() {
        super();
    }

    public FriendshipAlreadyExistException(String message){
        super(message);
    }
}
