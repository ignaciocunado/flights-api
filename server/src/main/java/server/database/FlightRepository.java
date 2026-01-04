package server.database;

import commons.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("""
        SELECT f FROM Flight f
        WHERE (:flightNumber IS NULL OR f.flightNumber = :flightNumber)
          AND (:origin IS NULL OR f.origin = :origin)
          AND (:destination IS NULL OR f.destination = :destination)
          AND (:airline IS NULL OR f.airline = :airline)
    """)
    List<Flight> findAllWithQuery(
            @Param("flightNumber") String flightNumber,
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("airline") String airline
    );

}