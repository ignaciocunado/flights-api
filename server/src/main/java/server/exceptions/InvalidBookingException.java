package server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public final class InvalidBookingException extends ServerException {

    /**
     * Creates a new exception signaling that the booking is not valid
     * @param reason The reason why the booking is invalid.
     */
    public InvalidBookingException(final String reason) {
        super(HttpStatus.BAD_REQUEST, "This flight cannot be booked", reason);
    }

}
