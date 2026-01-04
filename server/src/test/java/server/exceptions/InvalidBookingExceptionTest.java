package server.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvalidBookingExceptionTest {
    @Test
    public void invalidBookConstructorTEst() {
        new InvalidBookingException("");
    }

    @Test
    public void entityNotFoundGetStatusTest() {
        assertEquals(new InvalidBookingException("").getStatus(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void entityNotFoundGetReasonTest() {
        assertEquals(new InvalidBookingException("Good Reason").getReason(), "Good Reason");
    }
}
