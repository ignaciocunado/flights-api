package server.database;

import commons.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    /**
     * Finds all matching flights according to their flightNumber, origin, destination or airline.
     * Any one of these can be null.
     * @param flightNumber The unique flight number to look for.
     * @param origin The unique IATA 3-letter code of the origin airport to look for.
     * @param destination The unique IATA 3-letter code of the destination airport to look for.
     * @param airline The name of the airline to search for.
     * @return a list of flights matching the query.
     */
    @Query("""
        SELECT f
        FROM Flight f
        LEFT JOIN f.origin o
        LEFT JOIN f.destination d
        WHERE (:flightNumber IS NULL OR f.flightNumber = :flightNumber)
          AND (:airline IS NULL OR f.airline = :airline)
          AND (:origin IS NULL OR o.code = :origin)
          AND (:destination IS NULL OR d.code = :destination)
    """)
    List<Flight> findAllWithQuery(
            @Param("flightNumber") String flightNumber,
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("airline") String airline
    );
}