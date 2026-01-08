package server.api.controllers;

import commons.entities.Airport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.TestAirportRepository;
import server.services.AirportService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AirportControllerTest {
    private TestAirportRepository airportRepo;
    private AirportService airportService;
    private AirportController controller;

    private Airport agp;
    private Airport ams;
    private Airport lhr;
    private Airport rtm;
    private Airport jfk;

    @BeforeEach
    void setUp() {
        this.airportRepo = new TestAirportRepository();
        this.airportService = new AirportService(this.airportRepo);
        this.controller = new AirportController(this.airportService);

        agp =  new Airport("AGP", "Aeropuerto de Malaga", "Malaga", "Spain", "CET");
        ams = new Airport("AMS", "Amsterdam Schiphol Airport", "Amsterdam", "The Netherlands", "CET");
        lhr = new Airport("LHR", "London Heathrow Airport", "London", "United Kingdom", "UTC");
        rtm = new Airport("RTM", "Rotterdam Airport", "Rotterdam", "The Netherlands", "CET");
        jfk = new Airport("JFK", "John F. Kennedy International Airport", "New York", "United States of America", "ET");

    }

    @Test
    void getAllAirportsTest() {
        saveSampleAirports();

        assertEquals(List.of(agp, ams, rtm, lhr, jfk), this.controller.getAirports(null).getBody());
    }

    @Test
    void getQueryCodeTest() {
        saveSampleAirports();

        assertEquals(List.of(ams), this.controller.getAirports("AMS").getBody());
    }

    private void saveSampleAirports() {
        airportRepo.save(agp);
        agp.setId(1L);
        airportRepo.save(ams);
        ams.setId(2L);
        airportRepo.save(rtm);
        rtm.setId(3L);
        airportRepo.save(lhr);
        lhr.setId(4L);
        airportRepo.save(jfk);
        jfk.setId(5L);
    }
}
