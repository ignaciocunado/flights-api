package server.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FlightNotFoundExceptionTest {

    @Test
    public void flightNotFoundConstructorTest() {
        assertThrows(FlightNotFoundException.class, () -> {throw new FlightNotFoundException("");});
    }

    @Test
    public void entityNotFoundGetStatusTest() {
        assertEquals(HttpStatus.NOT_FOUND, new FlightNotFoundException("").getStatus());
    }

    @Test
    public void entityNotFoundGetReasonTest() {
        assertEquals("Good Reason", new FlightNotFoundException("Good Reason").getReason());
    }
}
