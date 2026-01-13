package server.api;

import lombok.Getter;
import server.exceptions.ServerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public final class RestExceptionHandler {

    @Getter
    class APIError {

        private final String message;

        private final Object reason;

        private final Instant timestamp;

        /**
         * Constructs a new API Error object
         * @param exception the exception that was fired
         */
        public APIError(final ServerException exception) {
            this.message = exception.getMessage();
            this.reason = exception.getReason();
            this.timestamp = Instant.now();
        }
    }

    /**
     * Handles all server exceptions to give an appropriate response
     * with a JSON marshaled error and the correct status code
     * @param exception the exception we extract this information from
     * @return the correct response entity as described
     */
    @ExceptionHandler(ServerException.class)
    public ResponseEntity<Object> handleServerExceptions(final ServerException exception) {
        return new ResponseEntity<>(new APIError(exception), exception.getStatus());
    }

}
