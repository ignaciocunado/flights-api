package server.services;

import commons.entities.Airport;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import server.database.AirportRepository;

import java.util.List;

@Service
public class AirportService {
    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<Airport> getAirports(String query) {
        final List<Airport> airports = airportRepository.findAllWithQuery(query);

        if(airports.isEmpty()){
            throw new EntityNotFoundException("Airports not found");
        }

        return airports;
    }
}
