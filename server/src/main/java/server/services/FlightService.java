package server.services;

import commons.entities.Airport;
import commons.entities.Flight;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import server.database.AirportRepository;
import server.database.FlightRepository;
import server.exceptions.InvalidBookingException;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    public FlightService(FlightRepository flightRepository,  AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    public List<Flight> getFlights(String flightNumber, String origin, String destination, String airline) {
        final List<Flight> flights = flightRepository.findAllWithQuery(flightNumber, origin, destination, airline);

        if(flights.isEmpty()) {
            throw new EntityNotFoundException("No flights found");
        }

        return flights;
    }

    public Flight getFlight(Long id) {
        final Optional<Flight> flightOpt = flightRepository.findById(id);

        if (flightOpt.isEmpty()) {
            throw new EntityNotFoundException("Flight with id " + id + " not found");
        }

        return flightOpt.get();
    }

    public Flight deleteFlight(Long id) {
        Flight toDelete = this.getFlight(id);
        flightRepository.deleteById(id);

        return toDelete;
    }

    @Transactional
    public Flight createFlight(Flight flight) {
        Airport origin = getOrCreateAirport(flight.getOrigin());
        Airport destination = getOrCreateAirport(flight.getDestination());
        System.out.println(origin);
        System.out.println(destination);

        flight.setOrigin(origin);
        flight.setDestination(destination);

        return flightRepository.save(flight);
    }

    public Flight bookFlight(Long id) {
        Optional<Flight> flightOpt = flightRepository.findById(id);

        if (flightOpt.isEmpty()) {
            throw new EntityNotFoundException("Flight with id " + id + " not found");
        }

        Flight flight = flightOpt.get();

        if(! flight.canBeBooked()) {
            throw new InvalidBookingException("Flight with with id" + id + "has no seats available.");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);

        if(flight.getAvailableSeats() <= 0) {
            flightRepository.delete(flight);
            return flight;
        }

        return flightRepository.save(flight);
    }

    private Airport getOrCreateAirport(@NotNull Airport origin) {
        String code = origin.getCode();
        String name = origin.getName();
        String city = origin.getCity();
        String country = origin.getCountry();
        String timezone = origin.getTimezone();

        return airportRepository.findByCode(code)
                .orElseGet(() -> airportRepository.save(new Airport(code, name, city, country, timezone)));
    }
}
