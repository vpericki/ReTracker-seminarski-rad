package hr.trio.realestatetracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PermissionException extends RuntimeException{

    public PermissionException(final String message) {
        super(message);
    }

    public PermissionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PermissionException(final Throwable cause) {
        super(cause);
    }
}
