package server.api.controllers;

import commons.entities.Flight;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.TestFlightRepository;
import server.exceptions.InvalidBookingException;
import server.services.FlightService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlightControllerTest {

    private TestFlightRepository flightRepo;
    private FlightService flightService;
    private FlightController controller;

    Flight f1;
    Flight f2;
    Flight f3;

    @BeforeEach
    public void setup() {
        flightRepo = new TestFlightRepository();
        flightService = new FlightService(flightRepo);
        controller = new FlightController(flightService);

        LocalDateTime departure = LocalDateTime.parse("2025-01-01T00:00:00");
        LocalDateTime arrival = LocalDateTime.parse("2025-01-01T02:00:00");
        f1 = new Flight(1L, "1234", "Ignacio AIR", "AGP", "AMS", departure.plusYears(2), arrival.plusHours(3).plusYears(2), "CET", "CET", 120, 10000, "EUR", 100);
        f2 = new Flight(2L,"0000", "TU Delft AIR", "LHR", "JFK", departure, arrival, "CET", "CET", 120, 10000, "EUR", 100);
        f3 = new Flight(3L,"7890", "Booking AIR", "RTM", "AMS", departure.minusDays(4).minusYears(4), arrival.minusDays(3).minusYears(4), "CET", "CET", 120 ,100, "EUR", 29);

    }

    @Test
    public void getFlightsNoQueryTest() {
        saveSampleFlights();


        assertEquals(List.of(f1, f2, f3), controller.getFlights(null, null, null, null).getBody());
    }

    @Test
    public void getFlightsWithQueryFlightNumberTest() {
        saveSampleFlights();

        assertEquals(List.of(f1), controller.getFlights("1234", null, null, null).getBody());
    }

    @Test
    public void getFlightsWithQueryOriginTest() {
        saveSampleFlights();

        assertEquals(List.of(f1), controller.getFlights(null, "AGP", null, null).getBody());
    }

    @Test
    public void getFlightsWithQueryAirlineTest() {
        saveSampleFlights();

        assertEquals(List.of(f1), controller.getFlights(null, null, null, "Ignacio AIR").getBody());
    }

    @Test
    public void getFlightsWithQueryDestinationTest() {
        saveSampleFlights();

        assertEquals(List.of(f1, f3), controller.getFlights(null, null, "AMS", null).getBody());
    }

    @Test
    public void getFlightsCombiTest() {
        saveSampleFlights();

        assertEquals(List.of(f1), controller.getFlights("1234", null, "AMS", null).getBody());
    }

    @Test
    public void createFlightTest() {
        final Flight flight = new Flight("1234", "Ignacio AIR", "AGP", "AMS", LocalDateTime.parse("2027-01-01T00:00:00"), "CET", LocalDateTime.parse("2027-01-01T05:00:00"), "CET", 120, 10000, "EUR", 100);
        flight.setId(1L);

        this.controller.createFlight(new Flight("1234", "Ignacio AIR", "AGP", "AMS", LocalDateTime.parse("2027-01-01T00:00:00"), "CET", LocalDateTime.parse("2027-01-01T05:00:00"), "CET", 120, 10000, "EUR", 100));

        assertEquals(this.flightRepo.getById(1L), flight);
    }

    @Test
    public void getFlightByIdTest() {
        final Flight flight = new Flight("1234", "Ignacio AIR", "AGP", "AMS", LocalDateTime.parse("2027-01-01T00:00:00"), "CET", LocalDateTime.parse("2027-01-01T05:00:00"), "CET", 120, 10000, "EUR", 100);

        this.flightRepo.save(flight);
        flight.setId(1L);

        assertEquals(flight, this.controller.getFlightById(1L).getBody());
    }

    @Test
    public void getFlightByIdNotFoundTest() {
        assertThrows(EntityNotFoundException.class, () -> this.controller.getFlightById(1000L));
    }

    @Test
    public void deleteFlightTest() {
        saveSampleFlights();

        assertEquals(f1, this.controller.deleteFlight(1L).getBody());
        assertEquals(List.of(f2, f3), this.flightRepo.findAll());
    }

    @Test
    public void deleteFlightNotFoundTest() {
        assertThrows(EntityNotFoundException.class, () -> this.controller.deleteFlight(1000L));
    }

    @Test
    public void bookFlightTest() {
        final Flight flight = new Flight("1234", "Ignacio AIR", "AGP", "AMS", LocalDateTime.parse("2027-01-01T00:00:00"), "CET", LocalDateTime.parse("2027-01-01T05:00:00"), "CET", 120, 10000, "EUR", 100);

        this.flightRepo.save(flight);
        flight.setId(1L);
        this.controller.bookFlight(1L);

        assertEquals(99, flightRepo.getById(1L).getAvailableSeats());
    }

    @Test
    public void bookFlightDeletesWhenZeroTest() {
        final Flight flight = new Flight("1234", "Ignacio AIR", "AGP", "AMS", LocalDateTime.parse("2027-01-01T00:00:00"), "CET", LocalDateTime.parse("2027-01-01T05:00:00"), "CET", 120, 10000, "EUR", 1);

        this.flightRepo.save(flight);
        flight.setId(1L);
        this.controller.bookFlight(1L);

        assertFalse(flightRepo.existsById(1L));
    }

    @Test
    public void bookFlightNotFoundTest() {
        assertThrows(EntityNotFoundException.class, () -> this.controller.bookFlight(1000L));
    }

    @Test
    public void bookFlightNotAllowedTest() {
        final Flight flight = new Flight("1234", "Ignacio AIR", "AGP", "AMS", LocalDateTime.parse("2027-01-01T00:00:00"), "CET", LocalDateTime.parse("2027-01-01T05:00:00"), "CET", 120, 10000, "EUR", 0);

        this.flightRepo.save(flight);
        flight.setId(1L);

        assertThrows(InvalidBookingException.class, () -> this.controller.bookFlight(1L));
    }

    private void saveSampleFlights() {
        flightRepo.save(f1);
        f1.setId(1L);
        flightRepo.save(f2);
        f2.setId(2L);
        flightRepo.save(f3);
        f3.setId(3L);
    }
}
