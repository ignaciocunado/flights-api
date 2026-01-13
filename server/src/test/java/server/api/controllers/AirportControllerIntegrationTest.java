package server.api.controllers;
import commons.entities.Airport;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import server.services.AirportService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@AutoConfigureMockMvc
public class AirportControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AirportService airportService;

    @Test
    void getAirports_noSearch_returns200AndArray() throws Exception {
        var airports = List.of(
                new Airport("AMS", "Schiphol", "Amsterdam", "Netherlands", "Europe/Amsterdam"),
                new Airport("MAD", "Barajas", "Madrid", "Spain", "Europe/Madrid")
        );

        when(airportService.getAirports(null)).thenReturn(airports);

        mockMvc.perform(get("/api/airports"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].code").value("AMS"))
                .andExpect(jsonPath("$[0].city").value("Amsterdam"))
                .andExpect(jsonPath("$[1].code").value("MAD"))
                .andExpect(jsonPath("$[1].country").value("Spain"));

        verify(airportService, times(1)).getAirports(null);
        verifyNoMoreInteractions(airportService);
    }

    @Test
    void getAirports_trailingSlash_returns200() throws Exception {
        when(airportService.getAirports(null)).thenReturn(List.of());

        mockMvc.perform(get("/api/airports/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

        verify(airportService, times(1)).getAirports(null);
        verifyNoMoreInteractions(airportService);
    }

    @Test
    void getAirports_withSearch_forwardsQuery() throws Exception {
        var airports = List.of(
                new Airport("AMS", "Schiphol", "Amsterdam", "Netherlands", "Europe/Amsterdam")
        );

        when(airportService.getAirports("am")).thenReturn(airports);

        mockMvc.perform(get("/api/airports").param("search", "am"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].code").value("AMS"));

        verify(airportService, times(1)).getAirports("am");
        verifyNoMoreInteractions(airportService);
    }

    @Test
    void getAirports_blankSearch_passedThrough() throws Exception {
        when(airportService.getAirports("")).thenReturn(List.of());

        mockMvc.perform(get("/api/airports").param("search", ""))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

        verify(airportService, times(1)).getAirports("");
        verifyNoMoreInteractions(airportService);
    }

}
