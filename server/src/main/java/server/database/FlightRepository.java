package server.database;

import commons.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
public interface FlightRepository extends JpaRepository<Flight, Long>{
}