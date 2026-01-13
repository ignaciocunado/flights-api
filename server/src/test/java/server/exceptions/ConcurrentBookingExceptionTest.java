package server.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConcurrentBookingExceptionTest {
    @Test
    public void constructorConcurrentBookingExceptionTest() {
        assertThrows(ConcurrentBookingException.class, () -> {throw new ConcurrentBookingException("");});
    }

    @Test
    public void concurrentBookinGetStatusTest() {
        assertEquals(HttpStatus.CONFLICT, new ConcurrentBookingException("").getStatus());
    }

    @Test
    public void concurrentBookingGetReasonTest() {
        assertEquals("Good Reason", new ConcurrentBookingException("Good Reason").getReason());
    }
}
