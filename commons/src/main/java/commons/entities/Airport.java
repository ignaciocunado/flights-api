package commons.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "airports")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public final class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String code;

    private String name;

    private String city;

    private String country;

    private String timezone;

    /**
     * Constructor for when we only have the airport code (e.g. data ingestion)
     * @param code IATA three-letter code identifying the airport
     */
    public Airport(String code) {
        this.code = code;
    }

    /**
     * Regular airport constructor
     * @param code The IATA three-letter code identifying the airport.
     * @param name The name of the airport.
     * @param city The city where the airport is in.
     * @param country The country where the airport is in.
     * @param timezone The timezone of the airport.
     */
    public Airport(String code, String name, String city, String country, String timezone) {
        this.code = code;
        this.name = name;
        this.city = city;
        this.country = country;
        this.timezone = timezone;
    }

    /**
     * Indicates whether some other airport is the same as this one
     * @param o   the reference object with which to compare.
     * @return true if they are the same airport
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Airport airport)) return false;
        return Objects.equals(code, airport.code); // IATA codes are unique
    }

    /**
     * Returns a hash code value for the airport
     * @return airport hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(code, name, city, country, timezone);
    }

    /**
     * String representation of this airport object
     * @return a string representation of this airport.
     */
    @Override
    public String toString() {
        return "Airport{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", timezone='" + timezone + '\'' +
                '}';
    }
}
