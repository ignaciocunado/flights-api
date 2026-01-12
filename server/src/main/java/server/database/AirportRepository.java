package server.database;

import commons.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    /**
     * Finds all airports whose code, name or city matches the query.
     * @param query The query string to search for.
     * @return a list of matching airport objects
     */
    @Query("""
    SELECT a FROM Airport a
    WHERE (:query IS NULL
        OR LOWER(a.code) LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(a.city) LIKE LOWER(CONCAT('%', :query, '%'))
    )""")
    List<Airport> findAllWithQuery(@Param("query") String query);

    /**
     * Finds an airport by its unique IATA 3-letter code.
     * @param code The code to search for.
     * @return A possibly empty optional airport object
     */
    Optional<Airport> findByCode(String code);

    /**
     * Checks whether an airport with the given code exists in the database.
     * @param code The code to search for.
     * @return true if the specified airport exists in the database
     */
    boolean existsByCode(String code);
}
