package com.fishingLog.spring.service.integration;

import com.fishingLog.spring.model.Angler;
import com.fishingLog.spring.model.Astrological;
import com.fishingLog.spring.model.Record;
import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.model.Weather;
import com.fishingLog.spring.repository.RecordRepository;
import com.fishingLog.spring.service.AstrologicalService;
import com.fishingLog.spring.service.RecordService;
import com.fishingLog.spring.service.TideService;
import com.fishingLog.spring.service.WeatherService;
import com.fishingLog.spring.utils.ApiResponse;
import com.fishingLog.spring.utils.StormGlassApiService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Tag("integration")
public class RecordServiceIntegrationTest extends BaseIntegrationIntegrationTest {

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordRepository recordRepository;

    @MockBean
    private StormGlassApiService apiService;

    @MockBean
    private WeatherService weatherService;

    @MockBean
    private TideService tideService;

    @MockBean
    private AstrologicalService astrologicalService;

    @Test
    public void testCreateRecordWithRelatedEntities() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(new Angler(1L, "Samwise", "Gamgee", "SamwiseGamgee",
                "SamWizeGamGee@noplace.com", "USER","password", Instant.now(), Instant.now()));
        SecurityContextHolder.setContext(securityContext);

        List<ApiResponse> mockResponses = List.of(
                new ApiResponse(200, Map.of(), "{\"weather\": \"mocked weather data\"}"),
                new ApiResponse(200, Map.of(), "{\"astrological\": \"mocked astrological data\"}"),
                new ApiResponse(200, Map.of(), "{\"tides\": [{\"height\": 1.5, \"time\": \"2024-11-16T10:00:00Z\", \"type\": \"high\"}]}")
        );

        when(apiService.obtainData(any(), any(Double.class), any(Double.class))).thenReturn(mockResponses);
        when(weatherService.createWeather(any())).thenReturn(new Weather());
        when(astrologicalService.createAstrological(any())).thenReturn(new Astrological());
        Set<Tide> mockTides = new HashSet<>();
        mockTides.add(new Tide());
        when(tideService.createTides(any())).thenReturn(mockTides);

        Record record = new Record.RecordBuilder()
                .setName("Test Record")
                .setSuccess("true")
                .setBody("A body")
                .setLatitude(37.7749)
                .setLongitude(-122.4194)
                .setDatetime(Instant.now())
                .setTimezone("America/Los_Angeles")
                .build();

        Record createdRecord = recordService.createRecordWithRelatedEntities(record);

        assertNotNull(createdRecord);
        assertNotNull(createdRecord.getId());
        assertNotNull(createdRecord.getWeather());
        assertNotNull(createdRecord.getTides());
        assertNotNull(createdRecord.getAstrological());

        Record savedRecord = recordRepository.findById(createdRecord.getId()).orElse(null);
        assertNotNull(savedRecord);
    }
}
