package server.exceptions;

import org.springframework.http.HttpStatus;

public class FlightNotFoundException extends ServerException {

    /**
     * Constructor for a flight not found exception
     *
     * @param reason  The reason why this exception was thrown.
     */
    public FlightNotFoundException(Object reason) {
        super(HttpStatus.NOT_FOUND, "No flights were found", reason);
    }
}
