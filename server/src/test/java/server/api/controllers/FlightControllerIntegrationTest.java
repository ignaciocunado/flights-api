package server.api.controllers;
import commons.entities.Airport;
import commons.entities.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import server.api.requests.BookingRequest;
import server.services.FlightService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FlightControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FlightService flightService;

    @Test
    void getFlights_noParams_returns200() throws Exception {
        Airport mad = new Airport("MAD");
        Airport ams = new Airport("AMS");

        var flights = List.of(
                makeFlight(1L, "KL123", "KLM", ams, mad),
                makeFlight(2L, "IB456", "Iberia", mad, ams)
        );

        when(flightService.getFlights(null, null, null, null)).thenReturn(flights);

        mockMvc.perform(get("/api/flights"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].flightNumber").value("KL123"))
                .andExpect(jsonPath("$[0].airline").value("KLM"))
                .andExpect(jsonPath("$[0].origin.code").value("AMS"))
                .andExpect(jsonPath("$[0].destination.code").value("MAD"));

        verify(flightService, times(1)).getFlights(null, null, null, null);
        verifyNoMoreInteractions(flightService);
    }

    @Test
    void getFlights_trailingSlash_returns200() throws Exception {
        when(flightService.getFlights(null, null, null, null)).thenReturn(List.of());

        mockMvc.perform(get("/api/flights/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

        verify(flightService, times(1)).getFlights(null, null, null, null);
        verifyNoMoreInteractions(flightService);
    }

    @Test
    void getFlights_allParams_forwardsToService() throws Exception {
        var flights = List.of(makeFlight(10L, "FR999", "Ryanair", new Airport("DUB"), new Airport("MAD")));

        when(flightService.getFlights("FR999", "DUB", "MAD", "Ryanair")).thenReturn(flights);

        mockMvc.perform(get("/api/flights")
                        .param("flightNumber", "FR999")
                        .param("origin", "DUB")
                        .param("destination", "MAD")
                        .param("airline", "Ryanair"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].flightNumber").value("FR999"))
                .andExpect(jsonPath("$[0].origin.code").value("DUB"))
                .andExpect(jsonPath("$[0].destination.code").value("MAD"))
                .andExpect(jsonPath("$[0].airline").value("Ryanair"));

        verify(flightService, times(1)).getFlights("FR999", "DUB", "MAD", "Ryanair");
        verifyNoMoreInteractions(flightService);
    }

    @Test
    void getFlights_partialParams_forwardsNulls() throws Exception {
        when(flightService.getFlights(null, "AMS", null, null)).thenReturn(List.of());

        mockMvc.perform(get("/api/flights").param("origin", "AMS"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

        verify(flightService, times(1)).getFlights(null, "AMS", null, null);
        verifyNoMoreInteractions(flightService);
    }

    @Test
    void createFlight_returns200AndBody() throws Exception {
        var created = makeFlight(5L, "UX101", "Air Europa", new Airport("MAD"), new Airport("PMI"));

        when(flightService.createFlight(ArgumentMatchers.any(Flight.class))).thenReturn(created);

        mockMvc.perform(post("/api/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "flightNumber": "UX101",
                                  "airline": "Air Europa",
                                  "origin": {"code": "MAD"},
                                  "destination": {"code": "PMI"}
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.flightNumber").value("UX101"))
                .andExpect(jsonPath("$.airline").value("Air Europa"))
                .andExpect(jsonPath("$.origin.code").value("MAD"))
                .andExpect(jsonPath("$.destination.code").value("PMI"));

        verify(flightService, times(1)).createFlight(ArgumentMatchers.any(Flight.class));
        verifyNoMoreInteractions(flightService);
    }

    @Test
    void createFlight_trailingSlash_returns200() throws Exception {
        when(flightService.createFlight(ArgumentMatchers.any(Flight.class)))
                .thenReturn(makeFlight(6L, "AF001", "Air France", new Airport("CDG"), new Airport("AMS")));

        mockMvc.perform(post("/api/flights/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "flightNumber": "AF001",
                                  "airline": "Air France",
                                  "origin": "CDG",
                                  "destination": "AMS"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(6));

        verify(flightService, times(1)).createFlight(ArgumentMatchers.any(Flight.class));
        verifyNoMoreInteractions(flightService);
    }

    @Test
    void createFlight_invalidJson_returns400() throws Exception {
        mockMvc.perform(post("/api/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ this is not json }"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(flightService);
    }

    @Test
    void getFlightById_returns200() throws Exception {
        when(flightService.getFlight(42L)).thenReturn(makeFlight(42L, "BA777", "British Airways", new Airport("LHR"), new Airport("JFK")));

        mockMvc.perform(get("/api/flights/42"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(42))
                .andExpect(jsonPath("$.flightNumber").value("BA777"));

        verify(flightService, times(1)).getFlight(42L);
        verifyNoMoreInteractions(flightService);
    }

    @Test
    void deleteFlight_returns200() throws Exception {
        when(flightService.deleteFlight(7L)).thenReturn(makeFlight(7L, "LH400", "Lufthansa", new Airport("FRA"), new Airport("JFK")));

        mockMvc.perform(delete("/api/flights/7"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.airline").value("Lufthansa"));

        verify(flightService, times(1)).deleteFlight(7L);
        verifyNoMoreInteractions(flightService);
    }

    @Test
    void bookFlight_returns200() throws Exception {
        when(flightService.bookFlight(9L, new BookingRequest(1L))).thenReturn(makeFlight(9L, "U2001", "easyJet", new Airport("LGW"), new Airport("AMS")));

        mockMvc.perform(patch("/api/flights/9/book"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(9))
                .andExpect(jsonPath("$.flightNumber").value("U2001"));

        verify(flightService, times(1)).bookFlight(9L, new BookingRequest(1L));
        verifyNoMoreInteractions(flightService);
    }

    private static Flight makeFlight(Long id, String flightNumber, String airline, Airport origin, Airport destination) {
        Flight f = new Flight();
        f.setId(id);
        f.setFlightNumber(flightNumber);
        f.setAirline(airline);
        f.setOrigin(origin);
        f.setDestination(destination);
        f.setVersion(1L);
        return f;
    }
}