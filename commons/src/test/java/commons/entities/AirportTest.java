package commons.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AirportTest {

    private Airport a1;
    private Airport a2;

    @BeforeEach
    void setUp() {
        this.a1 = new Airport("AGP", "Aeropuerto de Malaga", "Malaga", "Spain", "CET");
        this.a2 = new Airport("AMS", "Amsterdam Schiphol Airport", "Amsterdam", "The Netherlands", "CET");
    }

    @Test
    void testEquals() {
        assertEquals(new Airport("AGP", "Aeropuerto de Malaga", "Malaga", "Spain", "CET"), this.a1);
    }

    @Test
    void testEqualsBadCase() {
        assertNotEquals(this.a1, this.a2);
    }

    @Test
    void testHashCode() {
        assertEquals(this.a1.hashCode(), this.a1.hashCode());
    }

    @Test
    void testHashCodeBadCase() {
        assertNotEquals(this.a1.hashCode(), this.a2.hashCode());
    }

    @Test
    void getCode() {
        assertEquals("AGP", this.a1.getCode());
    }

    @Test
    void getName() {
        assertEquals("Aeropuerto de Malaga", this.a1.getName());
    }

    @Test
    void getCity() {
        assertEquals("Malaga", this.a1.getCity());
    }

    @Test
    void getCountry() {
        assertEquals("Spain", this.a1.getCountry());
    }

    @Test
    void getTimezone() {
        assertEquals("CET", this.a1.getTimezone());
    }

    @Test
    void setCode() {
        this.a1.setCode("MAL");

        assertEquals("MAL", this.a1.getCode());
    }

    @Test
    void setName() {
        this.a1.setName("Other name");

        assertEquals("Other name", this.a1.getName());
    }

    @Test
    void setCity() {
        this.a1.setCity("Sevilla");

        assertEquals("Sevilla", this.a1.getCity());
    }

    @Test
    void setCountry() {
        this.a1.setCountry("Portugal");

        assertEquals("Portugal", this.a1.getCountry());
    }

    @Test
    void setTimezone() {
        this.a1.setTimezone("America/Los_Angeles");

        assertEquals("America/Los_Angeles", this.a1.getTimezone());
    }

    @Test
    void testToString() {
        assertEquals("Airport{code='AGP', name='Aeropuerto de Malaga', city='Malaga', country='Spain', timezone='CET'}", this.a1.toString());
    }
}