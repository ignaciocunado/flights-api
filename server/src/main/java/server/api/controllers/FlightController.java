package server.api.controllers;


import commons.entities.Flight;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.dto.BookingRequest;
import server.services.FlightService;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public final class FlightController {

    private final FlightService flightService;

    /**
     * Creates the flight controller for the flight-related API endpoints
     * @param flightService the service with the main business logic
     */
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    /**
     * Gets flights with the specified query
     * @param flightNumber The unique flight number.
     * @param origin The 3-letter IATA code identifying the origin airport.
     * @param destination The 3-letter IATA code identifying the destination airport.
     * @param airline The airline operating flights.
     * @return a list of flights matching the specified query or all flights there is no query
     */
    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<Flight>> getFlights(@RequestParam(name = "flightNumber", required = false) String flightNumber, @RequestParam(name = "origin", required = false) String origin, @RequestParam(name = "destination", required = false) String destination, @RequestParam(name = "airline", required = false) String airline) {
        return new ResponseEntity<>(flightService.getFlights(
                flightNumber, origin, destination, airline
        ), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Saves a new flight object to the database.
     * @param flight Flight payload.
     * @return the created flight object
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        return new ResponseEntity<>(flightService.createFlight(flight), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Gets a flight by its unique ID from the database
     * @param id The unique ID of the flight.
     * @return the flight or 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        return new ResponseEntity<>(flightService.getFlight(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Deletes a flight from the database.
     * @param id The ID of the flight to delete.
     * @return the deleted flight or 404
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Flight> deleteFlight(@PathVariable Long id) {
        return new ResponseEntity<>(flightService.deleteFlight(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Books a specified flight.
     * If the flight cannot be booked, it throws an error.
     * If the number of available seats becomes 0, it deletes it from the database.
     * @param id The ID if the flight to book.
     * @param req The request body containing the version of the flight in the frontend.
     * @return the booked flight
     */
    @PatchMapping("/{id}/book")
    public ResponseEntity<Flight> bookFlight(@PathVariable Long id, @Valid @RequestBody BookingRequest req) {
        return new ResponseEntity<>(flightService.bookFlight(id, req), new HttpHeaders(), HttpStatus.OK);
    }


}