package server.services;

import commons.entities.Airport;
import commons.entities.Flight;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import server.database.AirportRepository;
import server.database.FlightRepository;
import server.exceptions.FlightNotFoundException;
import server.exceptions.InvalidBookingException;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    /**
     * Construct the flight service managing the business logic for flight-related API
     * @param flightRepository the flights repository
     * @param airportRepository the airports repository
     */
    public FlightService(FlightRepository flightRepository,  AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    /**
     * Gets all flights matching the given parameters.
     * @param flightNumber The flight number to search for.
     * @param origin The code of the origin airport to search for.
     * @param destination The code of the destination airport to search for.
     * @param airline The airline operating the flights to search for.
     * @return a list of matching flights or 404.
     */
    public List<Flight> getFlights(String flightNumber, String origin, String destination, String airline) {
        final List<Flight> flights = flightRepository.findAllWithQuery(flightNumber, origin, destination, airline);

        if(flights.isEmpty()) {
            throw new FlightNotFoundException("No flights found");
        }

        return flights;
    }

    /**
     * Retrieves a flight by its ID.
     * @param id ID of the flight to search for.
     * @return the flight object or 404.
     */
    public Flight getFlight(Long id) {
        final Optional<Flight> flightOpt = flightRepository.findById(id);

        if (flightOpt.isEmpty()) {
            throw new FlightNotFoundException("Flight with id " + id + " not found");
        }

        return flightOpt.get();
    }

    /**
     * Deletes a flight by its ID
     * @param id ID of the flight to delete
     * @return the deleted flight object or 404.
     */
    public Flight deleteFlight(Long id) {
        Flight toDelete = this.getFlight(id);

        if(! flightRepository.existsById(id)) {
            throw new FlightNotFoundException("Flight with id " + id + " not found");
        }

        flightRepository.deleteById(id);

        return toDelete;
    }

    /**
     * Creates a new flight. If the origin and destination airports do not exist, they are created too.
     * This is a database transaction, either all are saved, or none are.
     * @param flight The flight to save.
     * @return the saved flight
     */
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

    /**
     * Books a flight by its ID
     * @param id ID of the flight to book
     * @return the booked flight or 404.
     */
    public Flight bookFlight(Long id) {
        Optional<Flight> flightOpt = flightRepository.findById(id);

        if (flightOpt.isEmpty()) {
            throw new FlightNotFoundException("Flight with id " + id + " not found");
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

    /**
     * Helper method to get or create an airport.
     * @param airport The airport to get or save.
     * @return the airport
     */
    private Airport getOrCreateAirport(@NotNull Airport airport) {
        String code = airport.getCode();
        String name = airport.getName();
        String city = airport.getCity();
        String country = airport.getCountry();
        String timezone = airport.getTimezone();

        return airportRepository.findByCode(code)
                .orElseGet(() -> airportRepository.save(new Airport(code, name, city, country, timezone)));
    }
}
