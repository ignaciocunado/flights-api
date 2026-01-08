package server.database;

import commons.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    @Query("""
    SELECT a FROM Airport a
    WHERE (:query IS NULL
        OR LOWER(a.code) LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(a.city) LIKE LOWER(CONCAT('%', :query, '%'))
    )""")
    List<Airport> findAllWithQuery(
            @Param("query") String query
    );
}
