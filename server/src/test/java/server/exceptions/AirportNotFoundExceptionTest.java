package server.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AirportNotFoundExceptionTest {

    @Test
    public void airportNotFoundConstructorTest() {
        assertThrows(AirportNotFoundException.class, () -> {throw new AirportNotFoundException("");});
    }

    @Test
    public void airportNotFoundGetStatusTest() {
        assertEquals(HttpStatus.NOT_FOUND, new AirportNotFoundException("").getStatus());
    }

    @Test
    public void airportNotFoundGetReasonTest() {
        assertEquals("Good Reason", new AirportNotFoundException("Good Reason").getReason());
    }
}
