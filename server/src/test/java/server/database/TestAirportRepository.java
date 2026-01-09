package server.database;

import commons.entities.Airport;
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

public class TestAirportRepository implements AirportRepository {

    private final List<Airport> airports = new ArrayList<>();
    private long nextInt = 0;

    @Override
    public void flush() {

    }

    @Override
    public <S extends Airport> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Airport> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Airport> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Airport getOne(Long aLong) {
        return null;
    }

    @Override
    public Airport getById(Long aLong) {
        final Optional<Airport> airportOpt = find(aLong);
        if (!airportOpt.isPresent()) throw new JpaObjectRetrievalFailureException(new EntityNotFoundException());
        return airportOpt.get();
    }

    private Optional<Airport> find(Long id) {
        return this.airports.stream().filter(b -> Objects.equals(b.getId(), id)).findFirst();
    }

    @Override
    public Airport getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Airport> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Airport> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Airport> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Airport> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Airport> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Airport> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Airport, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Airport> S save(S entity) {
        for (Airport airport : airports) {
            if (airport.getId().equals(entity.getId())) {
                airport.setCode(entity.getCode());
                airport.setName(entity.getName());
                airport.setCity(entity.getCity());
                airport.setTimezone(entity.getTimezone());
                airport.setCountry(entity.getCountry());

                return (S) airport;
            }
        }

        final Airport airport = new Airport(entity.getCode(), entity.getName(), entity.getCity(), entity.getCountry(), entity.getTimezone());
        nextInt++;
        airport.setId(nextInt);

        entity.setId(nextInt);
        this.airports.add(airport);
        return (S) airport;
    }

    @Override
    public <S extends Airport> List<S> saveAll(Iterable<S> entities) {
        for(Airport airport : entities) {
            airports.add(airport);
            airport.setId(nextInt++);
        }

        return (List<S>) airports;
    }

    @Override
    public Optional<Airport> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Airport> findAll() {
        return this.airports;
    }

    @Override
    public List<Airport> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return airports.size();
    }

    @Override
    public void deleteById(Long aLong) {
        airports.removeIf(airport -> airport.getId().equals(aLong));
    }

    @Override
    public void delete(Airport entity) {
        airports.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Airport> entities) {

    }

    @Override
    public void deleteAll() {
        airports.clear();
    }

    @Override
    public List<Airport> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Airport> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Airport> findAllWithQuery(String query) {
        if(query == null) {
            return airports;
        }

        final String finalQuery = query.toLowerCase();

        return airports.stream()
                .filter(a -> a.getName().toLowerCase().contains(finalQuery) || a.getCity().toLowerCase().contains(finalQuery) || a.getCode().toLowerCase().equals(finalQuery))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Airport> findByCode(String code) {
        return airports.stream().filter(b -> b.getCode().equals(code)).findFirst();
    }

    @Override
    public boolean existsByCode(String code) {
        return airports.stream().anyMatch(airport -> airport.getCode().equals(code));
    }
}
