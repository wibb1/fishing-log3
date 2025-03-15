package com.fishingLog.spring.service.unit;

import com.fishingLog.spring.model.Weather;
import com.fishingLog.spring.repository.WeatherRepository;
import com.fishingLog.spring.service.WeatherService;
import com.fishingLog.spring.utils.ResponseDataForTest;
import com.fishingLog.spring.utils.StormGlassWeatherConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("UnitTest")
@Tag("unit")
class WeatherServiceUnitTest {

    private WeatherRepository weatherRepository;
    private StormGlassWeatherConverter weatherConverter;
    private WeatherService weatherService;
    private Weather weather;

    @BeforeEach
    void setUp() {
        weatherRepository = mock(WeatherRepository.class);
        weatherConverter = mock(StormGlassWeatherConverter.class);
        weatherService = new WeatherService(weatherRepository, weatherConverter);
        ResponseDataForTest testData = new ResponseDataForTest();
        weather = new Weather(testData.getWeatherMapTest());
        weather.setId(1L);
    }

    @Test
    void testFindAllWeather() {
        List<Weather> weathers = List.of(weather);
        when(weatherRepository.findAll()).thenReturn(weathers);
        List<Weather> result = weatherService.findAllWeather();
        assertEquals(weathers, result);
        verify(weatherRepository, times(1)).findAll();
    }

    @Test
    void testFindWeatherById_WhenExists() {
        when(weatherRepository.findById(1L)).thenReturn(Optional.of(weather));
        Optional<Weather> result = weatherService.findWeatherById(1L);
        assertTrue(result.isPresent());
        assertEquals(weather, result.get());
        verify(weatherRepository, times(1)).findById(1L);
    }

    @Test
    void testFindWeatherById_WhenNotExists() {
        when(weatherRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Weather> result = weatherService.findWeatherById(1L);
        assertFalse(result.isPresent());
        verify(weatherRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveWeather_WhenDoesNotExist() {
        when(weatherRepository.findOne(any())).thenReturn(Optional.empty());
        when(weatherRepository.save(weather)).thenReturn(weather);
        Weather result = weatherService.saveWeather(weather);
        assertEquals(weather, result);
        verify(weatherRepository, times(1)).save(weather);
    }

    @Test
    void testSaveWeather_WhenExists() {
        when(weatherRepository.findOne(any())).thenReturn(Optional.of(weather));
        Weather result = weatherService.saveWeather(weather);
        assertEquals(weather, result);
        verify(weatherRepository, never()).save(weather);
    }

    @Test
    void testDeleteWeather() {
        when(weatherRepository.existsById(anyLong())).thenReturn(true);
        weatherService.deleteWeather(1L);
        verify(weatherRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCreateWeather_ValidData() throws IOException {
        String rawWeatherData = "{ \"key\": \"value\" }"; // Replace with actual JSON for real testing
        Map<String, Object> convertedData = Map.of("temperature", 25.0, "humidity", 60.0);
        when(weatherConverter.dataConverter(rawWeatherData)).thenReturn(convertedData);

        Weather newWeather = new Weather(convertedData);
        when(weatherRepository.findOne(any())).thenReturn(Optional.empty());
        when(weatherRepository.save(any(Weather.class))).thenReturn(newWeather);
        Weather result = weatherService.createWeather(rawWeatherData);
        assertEquals(newWeather.getAirTemperature(), result.getAirTemperature());
        assertEquals(newWeather.getHumidity(), result.getHumidity());
        verify(weatherConverter, times(1)).dataConverter(rawWeatherData);
        verify(weatherRepository, times(1)).save(any(Weather.class));
    }

    @Test
    void testCreateWeather_InvalidData() throws IOException {
        String rawWeatherData = "{ \"invalid\": \"data\" }";
        when(weatherConverter.dataConverter(rawWeatherData)).thenThrow(new IOException("Conversion error"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> weatherService.createWeather(rawWeatherData));
        assertEquals("Error converting weather data", exception.getMessage());
        verify(weatherConverter, times(1)).dataConverter(rawWeatherData);
        verify(weatherRepository, never()).save(any(Weather.class));
    }
}
