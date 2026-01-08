package commons.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    public Airport(String code) {
        this.code = code;
    }

    public Airport(String code, String name, String city, String country) {
        this.code = code;
        this.name = name;
        this.city = city;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Airport airport)) return false;
        return Objects.equals(code, airport.code) && Objects.equals(name, airport.name) && Objects.equals(city, airport.city) && Objects.equals(country, airport.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, city, country);
    }
}
