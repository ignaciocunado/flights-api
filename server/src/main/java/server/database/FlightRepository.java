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
        SELECT f
        FROM Flight f
        LEFT JOIN f.origin o
        LEFT JOIN f.destination d
        WHERE (:flightNumber IS NULL OR f.flightNumber = :flightNumber)
          AND (:airline IS NULL OR f.airline = :airline)
          AND (:origin IS NULL OR o = :origin)
          AND (:destination IS NULL OR d = :destination)
    """)
    List<Flight> findAllWithQuery(
            @Param("flightNumber") String flightNumber,
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("airline") String airline
    );
}