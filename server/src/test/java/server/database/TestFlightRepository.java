package server.database;

import commons.entities.Flight;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestFlightRepository implements FlightRepository {

    private final List<Flight> flights = new ArrayList<>();
    private long nextInt = 0;

    @Override
    public List<Flight> findAll() {
        return this.flights;
    }

    @Override
    public List<Flight> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Flight> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Flight> findAllById(Iterable<Long> integers) {
        return null;
    }

    @Override
    public long count() {
        return this.flights.size();
    }

    @Override
    public void deleteById(Long integer) {
        flights.removeIf(flight -> flight.getId() == integer);
    }

    @Override
    public void delete(Flight entity) {
        this.flights.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Flight> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Flight> S save(S entity) {
        for (Flight c : flights) {
            if (c.getId() == entity.getId()) {
                c.setFlightNumber(entity.getFlightNumber());
                c.setOrigin(entity.getOrigin());
                c.setDestination(entity.getDestination());
                c.setAirline(entity.getAirline());
                c.setDepartureTime(entity.getDepartureTime());
                c.setArrivalTime(entity.getArrivalTime());
                c.setDuration(entity.getDuration());
                c.setPrice(entity.getPrice());
                c.setCurrency(c.getCurrency());
                c.setAvailableSeats(entity.getAvailableSeats());
                return (S) c;
            }
        }

        final Flight flight = new Flight(entity.getFlightNumber(), entity.getAirline(), entity.getOrigin(), entity.getDestination(), entity.getDepartureTime(), entity.getArrivalTime(), entity.getDuration(), entity.getPrice(), entity.getCurrency(), entity.getAvailableSeats());
        nextInt++;
        flight.setId(nextInt);

        entity.setId(nextInt);
        this.flights.add(flight);
        return (S) flight;
    }

    @Override
    public <S extends Flight> List<S> saveAll(Iterable<S> entities) {
        for(Flight f: entities) {
            flights.add(f);
            f.setId(nextInt++);
        }

        return (List<S>) flights;
    }

    @Override
    public Optional<Flight> findById(Long integer) {
        return this.find(integer);
    }

    @Override
    public boolean existsById(Long integer) {
        return this.find(integer).isPresent();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Flight> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Flight> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Flight> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Flight getOne(Long integer) {
        return null;
    }

    @Override
    public Flight getById(Long integer) {
        final Optional<Flight> flightopt = find(integer);
        if (!flightopt.isPresent()) throw new JpaObjectRetrievalFailureException(new EntityNotFoundException());
        return flightopt.get();

    }

    @Override
    public Flight getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Flight> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Flight> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Flight> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Flight> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Flight> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Flight> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Flight, R> R findBy(Example<S> example,
                                          Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    private Optional<Flight> find(Long id) {
        return this.flights.stream().filter(b -> Objects.equals(b.getId(), id)).findFirst();
    }

    @Override
    public List<Flight> findAllWithQuery(String flightNumber, String origin, String destination, String airline) {
        return this.flights.stream()
                .filter(f -> flightNumber == null || f.getFlightNumber().equals(flightNumber))
                .filter(f -> origin == null || f.getOrigin().getCode().equals(origin))
                .filter(f -> destination == null || f.getDestination().getCode().equals(destination))
                .filter(f -> airline == null || f.getAirline().equals(airline))
                .collect(Collectors.toList());
    }
}
