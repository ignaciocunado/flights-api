package server.services;

import commons.entities.Airport;
import org.springframework.stereotype.Service;
import server.database.AirportRepository;
import server.exceptions.AirportNotFoundException;

import java.util.List;

@Service
public class AirportService {
    private final AirportRepository airportRepository;

    /**
     * Constructs the service handling airport-related API business logic.
     * @param airportRepository The airport repository.
     */
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    /**
     * Finds airports matching a given query
     * @param query The code, city or airport name to look for.
     * @return a list of airports matching the given query or 404
     */
    public List<Airport> getAirports(String query) {
        final List<Airport> airports = airportRepository.findAllWithQuery(query);

        if(airports.isEmpty()){
            throw new AirportNotFoundException("Airports not found");
        }

        return airports;
    }
}
