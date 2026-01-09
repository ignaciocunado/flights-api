package server.api.controllers;

import commons.entities.Airport;
import commons.entities.Flight;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.TestAirportRepository;
import server.database.TestFlightRepository;
import server.exceptions.InvalidBookingException;
import server.services.FlightService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlightControllerTest {

    private TestFlightRepository flightRepo;
    private TestAirportRepository airportRepo;
    private FlightService flightService;
    private FlightController controller;

    private Flight f1;
    private Flight f2;
    private Flight f3;
    private Airport agp;
    private Airport ams;
    private Airport lhr;
    private Airport rtm;
    private Airport jfk;

    @BeforeEach
    public void setup() {
        flightRepo = new TestFlightRepository();
        airportRepo = new TestAirportRepository();
        flightService = new FlightService(flightRepo, airportRepo);
        controller = new FlightController(flightService);

        LocalDateTime departure = LocalDateTime.parse("2025-01-01T00:00:00");
        LocalDateTime arrival = LocalDateTime.parse("2025-01-01T02:00:00");

        agp =  new Airport("AGP", "Aeropuerto de Malaga", "Malaga", "Spain", "CET");
        ams = new Airport("AMS", "Amsterdam Schiphol Airport", "Amsterdam", "The Netherlands", "CET");
        lhr = new Airport("LHR", "London Heathrow Airport", "London", "United Kingdom", "UTC");
        rtm = new Airport("RTM", "Rotterdam Airport", "Rotterdam", "The Netherlands", "CET");
        jfk = new Airport("JFK", "John F. Kennedy International Airport", "New York", "United States of America", "ET");

        f1 = new Flight(1L, "1234", "Ignacio AIR", agp, ams, departure.plusYears(2), arrival.plusHours(3).plusYears(2), 120, 10000, "EUR", 100, Collections.emptySet());
        f2 = new Flight(2L,"0000", "TU Delft AIR", lhr, jfk, departure, arrival,120, 10000, "EUR", 100, Collections.emptySet());
        f3 = new Flight(3L,"7890", "Booking AIR", rtm, ams, departure.minusDays(4).minusYears(4), arrival.minusDays(3).minusYears(4),120 ,100, "EUR", 29, Collections.emptySet());
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
        saveSampleAirports();

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
        final Airport agp = new Airport("AGP", "Aeropuerto de Malaga", "Malaga", "Spain", "CET");
        final Airport ams = new Airport("AMS", "Amsterdam Schiphol Airport", "Amsterdam", "The Netherlands", "CET");
        final Flight flight = new Flight("1234", "Ignacio AIR", agp, ams, LocalDateTime.parse("2027-01-01T00:00:00"), LocalDateTime.parse("2027-01-01T05:00:00"), 120, 10000, "EUR", 100);
        flight.setId(1L);

        this.controller.createFlight(new Flight("1234", "Ignacio AIR", agp, ams, LocalDateTime.parse("2027-01-01T00:00:00"), LocalDateTime.parse("2027-01-01T05:00:00"), 120, 10000, "EUR", 100));

        assertEquals(this.flightRepo.getById(1L), flight);
    }

    @Test
    public void createFlightViolatePriceConstraintTest() {
        final Airport agp = new Airport("AGP", "Aeropuerto de Malaga", "Malaga", "Spain", "CET");
        final Airport ams = new Airport("AMS", "Amsterdam Schiphol Airport", "Amsterdam", "The Netherlands", "CET");
        final Flight flight = new Flight("1234", "Ignacio AIR", agp, ams, LocalDateTime.parse("2027-01-01T00:00:00"), LocalDateTime.parse("2027-01-01T05:00:00"), 0, 10000, "EUR", 100);
        flight.setId(1L);

        this.controller.createFlight(new Flight("1234", "Ignacio AIR", agp, ams, LocalDateTime.parse("2027-01-01T00:00:00"), LocalDateTime.parse("2027-01-01T05:00:00"), -1, 10000, "EUR", 100));

        System.out.println(flightRepo.getById(1L));
        assertEquals(this.flightRepo.getById(1L), flight);
    }

    @Test
    public void getFlightByIdTest() {
        final Airport agp = new Airport("AGP", "Aeropuerto de Malaga", "Malaga", "Spain", "CET");
        final Airport ams = new Airport("AMS", "Amsterdam Schiphol Airport", "Amsterdam", "The Netherlands", "CET");
        final Flight flight = new Flight("1234", "Ignacio AIR", agp, ams, LocalDateTime.parse("2027-01-01T00:00:00"), LocalDateTime.parse("2027-01-01T05:00:00"),120, 10000, "EUR", 100);

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
        final Airport agp = new Airport("AGP", "Aeropuerto de Malaga", "Malaga", "Spain", "CET");
        final Airport ams = new Airport("AMS", "Amsterdam Schiphol Airport", "Amsterdam", "The Netherlands", "CET");
        final Flight flight = new Flight("1234", "Ignacio AIR", agp, ams, LocalDateTime.parse("2027-01-01T00:00:00"),  LocalDateTime.parse("2027-01-01T05:00:00"),120, 10000, "EUR", 100);

        this.flightRepo.save(flight);
        flight.setId(1L);
        this.controller.bookFlight(1L);

        assertEquals(99, flightRepo.getById(1L).getAvailableSeats());
    }

    @Test
    public void bookFlightDeletesWhenZeroTest() {
        final Airport agp = new Airport("AGP", "Aeropuerto de Malaga", "Malaga", "Spain", "CET");
        final Airport ams = new Airport("AMS", "Amsterdam Schiphol Airport", "Amsterdam", "The Netherlands", "CET");
        final Flight flight = new Flight("1234", "Ignacio AIR", agp, ams, LocalDateTime.parse("2027-01-01T00:00:00"), LocalDateTime.parse("2027-01-01T05:00:00"), 120, 10000, "EUR", 1);

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
        final Airport agp = new Airport("AGP", "Aeropuerto de Malaga", "Malaga", "Spain", "CET");
        final Airport ams = new Airport("AMS", "Amsterdam Schiphol Airport", "Amsterdam", "The Netherlands", "CET");
        final Flight flight = new Flight("1234", "Ignacio AIR", agp, ams, LocalDateTime.parse("2027-01-01T00:00:00"), LocalDateTime.parse("2027-01-01T05:00:00"), 120, 10000, "EUR", 0);

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

    private void saveSampleAirports() {
        airportRepo.save(agp);
        agp.setId(1L);
        airportRepo.save(ams);
        ams.setId(2L);
        airportRepo.save(rtm);
        rtm.setId(3L);
        airportRepo.save(lhr);
        lhr.setId(4L);
        airportRepo.save(jfk);
        jfk.setId(5L);
    }
}
