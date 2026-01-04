package server.services;

import commons.entities.Flight;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import server.database.FlightRepository;
import server.exceptions.InvalidBookingException;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getFlights(String flightNumber, String origin, String destination, String airline, boolean isAvailable) {
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

    public Flight createFlight(Flight flight) {
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

        if(flight.getAvailableSeats() == 0) {
            flightRepository.delete(flight);
            return flight;
        }

        return flightRepository.save(flight);
    }
}
