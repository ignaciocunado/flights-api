package commons.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "flights")
@AllArgsConstructor
@NoArgsConstructor
public final class Flight {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotBlank
    private String flightNumber;

    @Getter
    @Setter
    @NotNull
    private String airline;

    @Getter
    @Setter
    @NotNull
    private String origin;

    @Getter
    @Setter
    @NotNull
    private String destination;

    @Getter
    @Setter
    private LocalDateTime departureTime;

    @Getter
    @Setter
    private LocalDateTime arrivalTime;

    @Getter
    @Setter
    private String departureTimezone;

    @Getter
    @Setter
    private String arrivalTimezone;

    @Getter
    @Setter
    private int duration; // in minutes

    @Getter
    @Setter
    private int price; // in cents to avoid floating point arithmetic

    @Getter
    @Setter
    private String currency;

    @Getter
    @Setter
    private int availableSeats;

    public Flight(String flightNumber, String airline, String origin, String destination, LocalDateTime departureTime, String departureTimezone, LocalDateTime arrivalTime, String arrivalTimezone, int duration, int price, String currency, int availableSeats) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.departureTimezone = departureTimezone;
        this.arrivalTime = arrivalTime;
        this.arrivalTimezone = arrivalTimezone;
        this.duration = duration;
        this.price = price;
        this.currency = currency;
        this.availableSeats = availableSeats;
    }

    public boolean canBeBooked() {
        return this.availableSeats > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Flight flight)) return false;
        return Objects.equals(id, flight.id)
                && Objects.equals(flightNumber, flight.flightNumber)
                && Objects.equals(airline, flight.airline)
                && Objects.equals(origin, flight.origin)
                && Objects.equals(destination, flight.destination)
                && Objects.equals(departureTime, flight.departureTime)
                && Objects.equals(arrivalTime, flight.arrivalTime)
                && Objects.equals(departureTimezone, flight.departureTimezone)
                && Objects.equals(arrivalTimezone, flight.arrivalTimezone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flightNumber, airline, origin, destination, departureTime, arrivalTime, departureTimezone, arrivalTimezone);
    }
}
