package com.fishingLog.spring.service.unit;

import com.fishingLog.spring.model.Angler;
import com.fishingLog.spring.model.Astrological;
import com.fishingLog.spring.model.Record;
import com.fishingLog.spring.model.Species;
import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.model.Weather;
import com.fishingLog.spring.repository.AstrologicalRepository;
import com.fishingLog.spring.repository.RecordRepository;
import com.fishingLog.spring.repository.SpeciesRepository;
import com.fishingLog.spring.repository.TideRepository;
import com.fishingLog.spring.repository.TideStationRepository;
import com.fishingLog.spring.repository.WeatherRepository;
import com.fishingLog.spring.service.AstrologicalService;
import com.fishingLog.spring.service.RecordService;
import com.fishingLog.spring.service.SpeciesService;
import com.fishingLog.spring.service.TideService;
import com.fishingLog.spring.service.TideStationService;
import com.fishingLog.spring.service.WeatherService;
import com.fishingLog.spring.utils.ApiResponse;
import com.fishingLog.spring.utils.ResponseDataForTest;
import com.fishingLog.spring.utils.StormGlassApiService;
import com.fishingLog.spring.utils.StormGlassAstrologicalConverter;
import com.fishingLog.spring.utils.StormGlassTideConverter;
import com.fishingLog.spring.utils.StormGlassWeatherConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("UnitTest")
@Tag("unit")
public class RecordServiceUnitTest {
    private RecordRepository recordRepository;
    private StormGlassApiService apiService;
    private SpeciesRepository speciesRepository;
    private SpeciesService speciesService;
    private SecurityContext securityContext;
    private Authentication authentication;
    private StormGlassWeatherConverter weatherConverter;
    private WeatherRepository weatherRepository;
    private WeatherService weatherService;
    private StormGlassAstrologicalConverter astrologicalConverter;
    private AstrologicalRepository astrologicalRepository;
    private AstrologicalService astrologicalService;
    private TideStationRepository tideStationRepository;
    private TideStationService tideStationService;
    private StormGlassTideConverter tideConverter;
    private TideRepository tideRepository;
    private TideService tideService;
    private RecordService recordService;

    private Angler angler;
    private Record record;

    @BeforeEach
    public void setUp() {
        createTestRecord();
        angler = new Angler();
        angler.setId(1L);
        mockServices();
    }

    @Test
    public void testFindAllRecords() {
        List<Record> records = Collections.singletonList(record);
        when(recordRepository.findAll()).thenReturn(records);

        List<Record> result = recordService.findAllRecords();
        assertEquals(records, result);
    }

    @Test
    public void testFindRecordById() {
        when(recordRepository.findById(1L)).thenReturn(Optional.of(record));

        Optional<Record> result = recordService.findRecordById(1L);
        assertTrue(result.isPresent());
        assertEquals(record, result.get());
    }

    @Test
    public void testFindEqualRecord_Exists() {
        when(recordRepository.findOne(any())).thenReturn(Optional.of(record));
        Optional<Record> result = recordService.findEqualRecord(record);
        assertTrue(result.isPresent());
        assertEquals(record, result.get());
    }

    @Test
    public void testFindEqualRecord_DoesNotExist() {
        when(recordRepository.findOne(any())).thenReturn(Optional.empty());
        Optional<Record> result = recordService.findEqualRecord(record);
        assertFalse(result.isPresent());
    }

    @Test
    public void testSaveRecord_Exists() {
        when(recordRepository.findOne(any())).thenReturn(Optional.of(record));
        Record result = recordService.saveRecord(record);
        assertEquals(record, result);
        verify(recordRepository, times(0)).save(record);
    }

    @Test
    public void testSaveRecord_DoesNotExist() {
        when(recordRepository.findOne(any())).thenReturn(Optional.empty());
        when(recordRepository.save(record)).thenReturn(record);
        Record result = recordService.saveRecord(record);
        assertEquals(record, result);
        verify(recordRepository, times(1)).save(record);
    }

    @Test
    public void testDeleteRecord() {
        doNothing().when(recordRepository).deleteById(1L);

        recordService.deleteRecord(1L);
        verify(recordRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testCreateRecordWithRelatedEntities() throws IOException {
        Map<String, List<String>> headers = Map.of("header", List.of("stuff in the header"));
        ResponseDataForTest testdata = new ResponseDataForTest();

        ApiResponse weatherResponse = new ApiResponse(200, headers, testdata.getWeatherDataString());
        ApiResponse astrologicalResponse = new ApiResponse(200, headers, testdata.getAstrologicalDataString());
        ApiResponse tideResponse = new ApiResponse(200, headers, testdata.getTideDataString());
        List<ApiResponse> responses = Arrays.asList(weatherResponse, astrologicalResponse, tideResponse);

        doReturn(responses).when(apiService).obtainData(any(Instant.class), anyDouble(), anyDouble());

        Weather weather = new Weather(testdata.getWeatherDataJsonNode().get("hours").get(0));
        weather.setId(1L);
        Astrological astrological = new Astrological(testdata.getAstrologicalDataJsonNode().get("data").get(0));
        astrological.setId(1L);
        Set<Tide> tides = new HashSet<>();
        tides.add(new Tide(testdata.getTideDataJsonNode().get("data").get(0)));
        tides.iterator().next().setId(1L);

        Species species = new Species(1L, "Bass", "Micropterus salmoides", 1, 10, Instant.now(), Instant.now(), Collections.emptySet());
        when(speciesService.createSpecies(any())).thenReturn(species);

        when(weatherService.createWeather(anyString())).thenReturn(weather);
        when(tideService.createTides(any())).thenReturn(tides);
        when(recordRepository.save(any(Record.class))).thenReturn(record);

        Record result = recordService.createRecordWithRelatedEntities(record);
        record.setAstrological(astrological);
        record.setWeather(weather);
        record.setTides(tides);

        assertNotNull(result);
        assertEquals("Expected Record Name", result.getName());
        assertEquals(weather, result.getWeather());
        assertEquals(astrological, result.getAstrological());
        assertEquals(tides, result.getTides());
    }

    @Test
    public void testCreateRecordWithRelatedEntities_NullResponses() {
        doReturn(null).when(apiService).obtainData(any(Instant.class), anyDouble(), anyDouble());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> recordService.createRecordWithRelatedEntities(record));
        assertEquals("Incorrect number of responses from Stormglass", exception.getMessage());
    }

    @Test
    public void testCreateRecordWithRelatedEntities_FewerResponses() throws IOException {
        Map<String, List<String>> headers = Map.of("header", List.of("stuff in the header"));
        ResponseDataForTest testdata = new ResponseDataForTest();
        ApiResponse weatherResponse = new ApiResponse(200, headers, testdata.getWeatherDataString());
        ApiResponse astrologicalResponse = new ApiResponse(200, headers, testdata.getAstrologicalDataString());
        List<ApiResponse> responses = Arrays.asList(weatherResponse, astrologicalResponse);
        doReturn(responses).when(apiService).obtainData(any(Instant.class), anyDouble(), anyDouble());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> recordService.createRecordWithRelatedEntities(record));
        assertEquals("Incorrect number of responses from Stormglass", exception.getMessage());
    }

    @Test
    public void testCreateRecordWithRelatedEntities_EmptyResponseBodies() {
        Map<String, List<String>> headers = Map.of("header", List.of("stuff in the header"));
        ApiResponse emptyResponse = new ApiResponse(200, headers, "");
        List<ApiResponse> responses = Arrays.asList(emptyResponse, emptyResponse, emptyResponse);
        doReturn(responses).when(apiService).obtainData(any(Instant.class), anyDouble(), anyDouble());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> recordService.createRecordWithRelatedEntities(record));
        assertEquals("Weather data is empty", exception.getMessage());
    }

    @Test
    public void testGetCurrentAngler() {
        Optional<Angler> result = recordService.getCurrentAngler();
        assertTrue(result.isPresent());
        assertEquals(angler, result.get());
    }

    @Test
    public void testGetCurrentAngler_NotInstanceOfAngler() {
        when(authentication.getPrincipal()).thenReturn("Not an Angler");
        Optional<Angler> result = recordService.getCurrentAngler();
        assertFalse(result.isPresent());
    }

    private void createTestRecord() {
        record = new Record.RecordBuilder()
                .setName("Expected Record Name")
                .setSuccess("Success")
                .setAnglerId(1L)
                .setCreatedAt(Instant.now())
                .setUpdatedAt(Instant.now())
                .setBody("Test Body")
                .setLatitude(41.6)
                .setLongitude(-70.8)
                .setDatetime(Instant.parse("2024-07-12T21:00:00+00:00"))
                .setTimezone("UTC")
                .build();
    }

    private void mockServices() {
        // Security related mocks
        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.getPrincipal()).thenReturn(angler);

        // Weather related mocks
        weatherConverter = mock(StormGlassWeatherConverter.class);
        weatherRepository = mock(WeatherRepository.class);
        weatherService = mock(WeatherService.class);
        weatherService.setWeatherConverter(weatherConverter);
        weatherService.setWeatherRepository(weatherRepository);

        // Astrological related mocks
        astrologicalConverter = mock(StormGlassAstrologicalConverter.class);
        astrologicalRepository = mock(AstrologicalRepository.class);
        astrologicalService = mock(AstrologicalService.class);
        astrologicalService.setAstrologicalConverter(astrologicalConverter);
        astrologicalService.setAstrologicalRepository(astrologicalRepository);

        // Tide related mocks
        tideStationRepository = mock(TideStationRepository.class);
        tideStationService = mock(TideStationService.class);
        tideStationService.setTideStationRepository(tideStationRepository);

        tideConverter = mock(StormGlassTideConverter.class);
        tideRepository = mock(TideRepository.class);
        tideService = mock(TideService.class);
        tideService.setTideConverter(tideConverter);
        tideService.setTideStationService(tideStationService);
        tideService.setTideRepository(tideRepository);

        // Species related mocks
        speciesService = mock(SpeciesService.class);
        speciesRepository = mock(SpeciesRepository.class);

        // Record related mocks
        apiService = mock(StormGlassApiService.class);
        recordRepository = mock(RecordRepository.class);
        recordService = new RecordService(weatherService, tideService, astrologicalService, apiService, speciesService);
        recordService.setRecordRepository(recordRepository);
    }
}
