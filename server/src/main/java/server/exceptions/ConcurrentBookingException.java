package server.exceptions;

import org.springframework.http.HttpStatus;

public class ConcurrentBookingException extends ServerException {
    public ConcurrentBookingException(Object reason) {
        super(HttpStatus.CONFLICT, "There was a concurrency issue while trying to book a flight", reason);
    }
}
