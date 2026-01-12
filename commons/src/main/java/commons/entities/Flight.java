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
@Getter
@Setter
public final class Flight {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String flightNumber;

    @NotNull
    private String airline;

    @ManyToOne
    @JoinColumn(name = "origin_id")
    @NotNull
    private Airport origin;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    @NotNull
    private Airport destination;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    @Min(0)
    private int duration; // in minutes

    private int price; // in cents to avoid floating point arithmetic

    private String currency;

    private int availableSeats;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Amenity> amenities;

    /**
     * Constructs a new Flight object.
     *
     * @param flightNumber The unique flight number (e.g. "KL1234").
     * @param airline The airline operating the flight.
     * @param origin The origin airport object.
     * @param destination The destination airport object.
     * @param departureTime The scheduled departure date and time.
     * @param arrivalTime The scheduled arrival date and time.
     * @param duration The total flight duration in minutes.
     * @param price The ticket price in cents.
     * @param currency The currency of the price (e.g. "EUR", "USD").
     * @param availableSeats The number of seats still available on the flight.
     */
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

    /**
     * Constructs a new Flight object.
     *
     * @param flightNumber The unique flight number (e.g. "KL1234").
     * @param airline The airline operating the flight.
     * @param origin The origin airport object.
     * @param destination The destination airport object.
     * @param departureTime The scheduled departure date and time.
     * @param arrivalTime The scheduled arrival date and time.
     * @param duration The total flight duration in minutes.
     * @param price The ticket price in cents.
     * @param currency The currency of the price (e.g. "EUR", "USD").
     * @param availableSeats The number of seats still available on the flight.
     * @param amenities The amenities this flight includes
     */
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

    /**
     * Indicates whether this flight has any available seats
     * @return true if the flight can be booked
     */
    public boolean canBeBooked() {
        return this.availableSeats > 0;
    }

    /**
     * Indicates whether some other flight is the same as this one
     * @param o the reference object with which to compare.
     * @return true if they are equal
     */
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

    /**
     * Hash code representation of this object
     * @return the hash code for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, flightNumber, airline, origin, destination, departureTime, arrivalTime);
    }

    /**
     * Returns a string representation of this flight
     * @return a string representation of the flight
     */
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
