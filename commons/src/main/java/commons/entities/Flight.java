package commons.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "flights")
public final class Flight {

    @Id
    @GeneratedValue
    private Long id;

}


//flight_number, airline, origin, destination,
//departure time, arrival time, duration, and price