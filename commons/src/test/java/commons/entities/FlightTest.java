package commons.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FlightTest {

    private Flight flight1;
    private Flight flight2;
    private Flight flight3;

    @BeforeEach
    void setUp() {
        LocalDateTime departure = LocalDateTime.parse("2025-01-01T00:00:00");
        LocalDateTime arrival = LocalDateTime.parse("2025-01-01T02:00:00");

        Airport origin = new Airport("AGP", "Aeropuerto de Malaga", "Malaga", "Spain", "CET");
        Airport destination = new Airport("AMS", "Amsterdam Schiphol Airport", "Amsterdam", "The Netherlands", "CET");

        this.flight1 = new Flight("AB1234", "Ignacio AIR", origin, destination, departure, arrival, 120, 10000, "EUR", 100);
        this.flight2 = new Flight("AB1234", "Ignacio AIR", origin, destination, departure, arrival, 120, 10000, "EUR", 100);
        this.flight3 = new Flight("AB1233", "Booking AIR", origin   , destination, departure, arrival, 120 ,100, "EUR", 29);
    }

    @Test
    void testEqualsGoodWeather() {
        assertEquals(this.flight1, this.flight2);
    }

    @Test
    void testEqualsBadWeather() {
        assertNotEquals(this.flight1, this.flight3);
    }

    @Test
    void getFlightNumber() {
        assertEquals("AB1234", this.flight1.getFlightNumber());
    }

    @Test
    void getAirline() {
        assertEquals("Ignacio AIR", this.flight1.getAirline());
    }

    @Test
    void getOrigin() {
        Airport origin = new Airport("AGP", "Aeropuerto de Malaga", "Malaga", "Spain", "CET");

        assertEquals(origin, this.flight1.getOrigin());
    }

    @Test
    void getDestination() {
        Airport destination = new Airport("AMS", "Amsterdam Schiphol Airport", "Amsterdam", "The Netherlands", "CET");

        assertEquals(destination, this.flight1.getDestination());
    }

    @Test
    void getDepartureTime() {
        assertEquals(LocalDateTime.parse("2025-01-01T00:00:00"), this.flight1.getDepartureTime());
    }

    @Test
    void getArrivalTime() {
        assertEquals(LocalDateTime.parse("2025-01-01T02:00:00"), this.flight1.getArrivalTime());
    }

    @Test
    void getDuration() {
        assertEquals(120, this.flight1.getDuration());
    }

    @Test
    void getPrice() {
        assertEquals(10000, this.flight1.getPrice());
    }

    @Test
    void setFlightNumber() {
        this.flight1.setFlightNumber("some other flight number");
        assertEquals("some other flight number", this.flight1.getFlightNumber());
    }

    @Test
    void setAirline() {
        this.flight1.setAirline("some other airline");
        assertEquals("some other airline", this.flight1.getAirline());
    }

    @Test
    void setOrigin() {
        Airport otherOrigin = new Airport("RTM", "Rotterdam Airport", "Rotterdam", "The Netherlands", "CET");

        this.flight1.setOrigin(otherOrigin);

        assertEquals(otherOrigin, this.flight1.getOrigin());
    }

    @Test
    void setDestination() {
        Airport otherDestination = new Airport("RTM", "Rotterdam Airport", "Rotterdam", "The Netherlands", "CET");

        this.flight1.setDestination(otherDestination);
        assertEquals(otherDestination, this.flight1.getDestination());
    }

    @Test
    void setDepartureTime() {
        this.flight1.setDepartureTime(LocalDateTime.parse("2025-01-01T00:00:00").plusDays(2));
        assertEquals(LocalDateTime.parse("2025-01-01T00:00:00").plusDays(2), this.flight1.getDepartureTime());
    }

    @Test
    void setArrivalTime() {
        this.flight1.setArrivalTime(LocalDateTime.parse("2025-01-01T00:00:00").plusDays(2));
        assertEquals(LocalDateTime.parse("2025-01-01T00:00:00").plusDays(2), this.flight1.getArrivalTime());
    }

    @Test
    void setDuration() {
        this.flight1.setDuration(-1);
        assertEquals(-1, this.flight1.getDuration());
    }

    @Test
    void setPrice() {
        this.flight1.setPrice(-1);
        assertEquals(-1, this.flight1.getPrice());
    }

    @Test
    void getAvailableSeats() {
        assertEquals(100, this.flight1.getAvailableSeats());
    }

    @Test
    void setAvailableSeats() {
        this.flight1.setAvailableSeats(-1);
        assertEquals(-1, this.flight1.getAvailableSeats());
    }

    @Test
    void canBeBooked() {
        assertTrue(this.flight1.canBeBooked());
    }

    @Test
    void canNotBeBooked() {
        this.flight1.setAvailableSeats(-1);
        assertFalse(this.flight1.canBeBooked());
    }

    @Test
    void hashCodeTest() {
        assertEquals(this.flight1.hashCode(), this.flight1.hashCode());
    }

    @Test
    void hashCodeTest2() {
        assertNotEquals(this.flight1.hashCode(), this.flight3.hashCode());
    }
}