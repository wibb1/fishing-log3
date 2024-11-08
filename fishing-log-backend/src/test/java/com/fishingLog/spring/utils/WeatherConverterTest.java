package com.fishingLog.spring.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeatherConverterTest {
    private final StormGlassWeatherConverter stormGlassWeatherConverter = new StormGlassWeatherConverter();
    private final ResponseDataForTest response = new ResponseDataForTest();

    @Test
    public void weatherConverterTest() throws IOException {
        StormGlassApiService stormGlassApiService = mock(StormGlassApiService.class);
        Map<String, List<String>> headers = Map.of(
                "access-control-allow-origin", List.of("*"),
                "content-length", List.of("1223"),
                "content-type", List.of("application/json"),
                "date", List.of("Mon, 04 Nov 2024 02:03:08 GMT"),
                "server", List.of("gunicorn"),
                "vary", List.of("Accept-Encoding")
        );
        ApiResponse weatherResponse = new ApiResponse(200, headers, response.getDataString());
        when(stormGlassApiService.obtainData(Instant.parse("2024-07-12T21:00:00.00z"), 41.6, -70.8)).thenReturn((List.of(weatherResponse)));

        Map<String,Object> actualResponse = stormGlassWeatherConverter.dataConverter(response.getWeatherDataString());
        Map<String,Object> expectedResponse = response.getWeatherMapTest();

        assertEquals(expectedResponse.get("airTemperature"), actualResponse.get("airTemperature"));
        assertEquals(expectedResponse.get("cloudCover"), actualResponse.get("cloudCover"));
        assertEquals(expectedResponse.get("currentDirection"), actualResponse.get("currentDirection"));
        assertEquals(expectedResponse.get("currentSpeed"), actualResponse.get("currentSpeed"));
        assertEquals(expectedResponse.get("gust"), actualResponse.get("gust"));
        assertEquals(expectedResponse.get("humidity"), actualResponse.get("humidity"));
        assertEquals(expectedResponse.get("pressure"), actualResponse.get("pressure"));
        assertEquals(expectedResponse.get("seaLevel"), actualResponse.get("seaLevel"));
        assertEquals(expectedResponse.get("secondarySwellDirection"), actualResponse.get("secondarySwellDirection"));
        assertEquals(expectedResponse.get("secondarySwellHeight"), actualResponse.get("secondarySwellHeight"));
        assertEquals(expectedResponse.get("secondarySwellPeriod"), actualResponse.get("secondarySwellPeriod"));
        assertEquals(expectedResponse.get("swellDirection"), actualResponse.get("swellDirection"));
        assertEquals(expectedResponse.get("swellHeight"), actualResponse.get("swellHeight"));
        assertEquals(expectedResponse.get("swellPeriod"), actualResponse.get("swellPeriod"));
        assertEquals(expectedResponse.get("time"), actualResponse.get("time"));
        assertEquals(expectedResponse.get("visibility"), actualResponse.get("visibility"));
        assertEquals(expectedResponse.get("waveDirection"), actualResponse.get("waveDirection"));
        assertEquals(expectedResponse.get("waveHeight"), actualResponse.get("waveHeight"));
        assertEquals(expectedResponse.get("wavePeriod"), actualResponse.get("wavePeriod"));
        assertEquals(expectedResponse.get("windDirection"), actualResponse.get("windDirection"));
        assertEquals(expectedResponse.get("windSpeed"), actualResponse.get("windSpeed"));
        assertEquals(expectedResponse.get("windWaveDirection"), actualResponse.get("windWaveDirection"));
        assertEquals(expectedResponse.get("windWaveHeight"), actualResponse.get("windWaveHeight"));
        assertEquals(expectedResponse.get("windWavePeriod"), actualResponse.get("windWavePeriod"));
    }
}
