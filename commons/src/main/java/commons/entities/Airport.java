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
public final class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotBlank
    @Column(unique = true)
    private String code;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private String timezone;

    public Airport(String code) {
        this.code = code;
    }

    public Airport(String code, String name, String city, String country, String timezone) {
        this.code = code;
        this.name = name;
        this.city = city;
        this.country = country;
        this.timezone = timezone;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Airport airport)) return false;
        return Objects.equals(code, airport.code) && Objects.equals(name, airport.name) && Objects.equals(city, airport.city) && Objects.equals(country, airport.country) && Objects.equals(timezone, airport.timezone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, city, country, timezone);
    }

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
