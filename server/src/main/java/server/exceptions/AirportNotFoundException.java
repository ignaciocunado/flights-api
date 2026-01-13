package server.exceptions;

import org.springframework.http.HttpStatus;

public class AirportNotFoundException extends ServerException {

    /**
     * Constructor for an exception when an airport is not found.
     * @param reason The reason why this exception was fired.
     */
    public AirportNotFoundException(Object reason) {
        super(HttpStatus.NOT_FOUND, "No airports were found", reason);
    }
}
