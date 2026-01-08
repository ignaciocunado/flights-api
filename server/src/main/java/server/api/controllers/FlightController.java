package server.api.controllers;


import commons.entities.Flight;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.FlightService;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public final class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Flight>> getFlights(@RequestParam(required = false) String flightNumber, @RequestParam(required = false) String origin, @RequestParam(required = false) String destination, @RequestParam(required = false) String airline) {
        return new ResponseEntity<>(flightService.getFlights(
                flightNumber, origin, destination, airline
        ), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        return new ResponseEntity<>(flightService.createFlight(flight), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        return new ResponseEntity<>(flightService.getFlight(id), new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Flight> deleteFlight(@PathVariable Long id) {
        return new ResponseEntity<>(flightService.deleteFlight(id), new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping("/{id}/book")
    public ResponseEntity<Flight> bookFlight(@PathVariable Long id) {
        return new ResponseEntity<>(flightService.bookFlight(id), new HttpHeaders(), HttpStatus.OK);
    }


}