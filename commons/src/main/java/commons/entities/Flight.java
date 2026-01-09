package commons.entities;

import commons.enums.Amenity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    @ManyToOne
    @JoinColumn(name = "origin_id")
    @NotNull
    private Airport origin;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "destination_id")
    @NotNull
    private Airport destination;

    @Getter
    @Setter
    private LocalDateTime departureTime;

    @Getter
    @Setter
    private LocalDateTime arrivalTime;

    @Getter
    @Setter
    @Min(0)
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

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @Getter
    @Setter
    private Set<Amenity> amenities;

    public Flight(String flightNumber, String airline, Airport origin, Airport destination, LocalDateTime departureTime, LocalDateTime arrivalTime, int duration, int price, String currency, int availableSeats) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.price = price;
        this.currency = currency;
        this.availableSeats = availableSeats;
        this.amenities = new HashSet<>();
    }

    public Flight(String flightNumber, String airline, Airport origin, Airport destination, LocalDateTime departureTime, LocalDateTime arrivalTime, int duration, int price, String currency, int availableSeats, Set<Amenity> amenities) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.price = price;
        this.currency = currency;
        this.availableSeats = availableSeats;
        this.amenities = amenities;
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
                && Objects.equals(arrivalTime, flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flightNumber, airline, origin, destination, departureTime, arrivalTime);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", airline='" + airline + '\'' +
                ", origin=" + origin +
                ", destination=" + destination +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", duration=" + duration +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", availableSeats=" + availableSeats +
                ", amenities=" + amenities +
                '}';
    }
}
