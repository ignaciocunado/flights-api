package server.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvalidBookingExceptionTest {

    @Test
    public void invalidBookConstructorTest() {
        assertThrows(InvalidBookingException.class, () -> {throw new InvalidBookingException("");});
    }

    @Test
    public void invalidBookingGetStatusTest() {
        assertEquals(HttpStatus.BAD_REQUEST, new InvalidBookingException("").getStatus());
    }

    @Test
    public void invalidBookingGetReasonTest() {
        assertEquals("Good Reason", new InvalidBookingException("Good Reason").getReason());
    }
}
