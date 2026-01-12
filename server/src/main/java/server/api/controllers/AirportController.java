package server.api.controllers;

import commons.entities.Airport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.AirportService;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;

    /**
     * Creates the airport controller for the airport-related API endpoints.
     * @param airportService The airport service which handles the business logic.
     */
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<Airport>> getAirports(@RequestParam(value = "search", required = false) String query) {
        return new ResponseEntity<>(airportService.getAirports(query), new HttpHeaders(), HttpStatus.OK);
    }
}
