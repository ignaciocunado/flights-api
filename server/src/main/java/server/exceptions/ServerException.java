package server.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ServerException extends RuntimeException {

    private final HttpStatus status;
    private final Object reason;

    /**
     * Abstract constructor for an exception coming from the server.
     * @param status The HTTP status of the exception.
     * @param message A message for the exception.
     * @param reason The reason why this exception was thrown.
     */
    public ServerException(final HttpStatus status, final String message, final Object reason) {
        super(message);
        this.status = status;
        this.reason = reason;
    }

}
