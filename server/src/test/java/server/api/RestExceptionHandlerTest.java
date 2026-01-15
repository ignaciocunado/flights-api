package server.api;

import org.junit.jupiter.api.Test;
import server.exceptions.ConcurrentBookingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestExceptionHandlerTest {

    @Test
    public void testApiErrorConstructor() {
        RestExceptionHandler.APIError err = new RestExceptionHandler.APIError(new ConcurrentBookingException(""));

        assertEquals("There was a concurrency issue while trying to book a flight", err.getMessage());
        assertEquals("", err.getReason());
    }
}
