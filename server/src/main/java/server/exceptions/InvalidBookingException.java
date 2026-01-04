package server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public final class InvalidBookingException extends ServerException {

    public InvalidBookingException(final String reason) {
        super(HttpStatus.BAD_REQUEST, "This flight cannot be booked", reason);
    }

}
