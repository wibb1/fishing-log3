package com.fishingLog.spring.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishingLog.FishingLogApplication;
import com.fishingLog.spring.service.integration.BaseIntegrationIntegrationTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = FishingLogApplication.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("UnitTest")
@Tag("unit")
public class StormGlassApiServiceTest extends BaseIntegrationIntegrationTest {
    private static final String expectedResponse = """
            [{"hours":[{"airTemperature":{"sg":23.38},"cloudCover":{"sg":43.7},"currentDirection":{"sg":321.8},
            "currentSpeed":{"sg":0.06},"gust":{"sg":13.53},"humidity":{"sg":90.9},"pressure":{"sg":1022.44},
            "seaLevel":{"sg":-0.54},"secondarySwellDirection":{"sg":173.39},"secondarySwellHeight":{"sg":0.09},
            "secondarySwellPeriod":{"sg":8.71},"swellDirection":{"sg":175.11},"swellHeight":{"sg":0.18},
            "swellPeriod":{"sg":5.82},"time":"2024-07-12T21:00:00+00:00","visibility":{"sg":24.13},
            "waveDirection":{"sg":195.18},"waveHeight":{"sg":0.76},"wavePeriod":{"sg":4.67},
            "windDirection":{"sg":211.35},"windSpeed":{"sg":7.16},"windWaveDirection":{"sg":203.34},
            "windWaveHeight":{"sg":0.76},"windWavePeriod":{"sg":5.36}}],"meta":{"cost":1,"dailyQuota":50,
            "end":"2024-07-12 21:00","lat":41.6,"lng":-70.8,"params":["airTemperature","pressure","cloudCover",
            "currentDirection","currentSpeed","gust","humidity","seaLevel","visibility","windDirection",
            "windSpeed","seaLevel","swellDirection","swellHeight","swellPeriod","secondarySwellDirection",
            "secondarySwellHeight","secondarySwellPeriod","waveDirection","waveHeight","wavePeriod","windWaveDirection",
            "windWaveHeight","windWavePeriod"],"requestCount":13,"source":["sg"],"start":"2024-07-12 21:00"}},
            {"data":[{"height":0.484495766600653,"time":"2024-07-12T04:55:00+00:00","type":"high"},
            {"height":-0.3884259370281767,"time":"2024-07-12T10:10:00+00:00","type":"low"},
            {"height":0.5242476943841401,"time":"2024-07-12T17:25:00+00:00","type":"high"},
            {"height":-0.2936569385171069,"time":"2024-07-12T22:40:00+00:00","type":"low"}],
            "meta":{"cost":1,"dailyQuota":50,"datum":"MSL","end":"2024-07-12 23:59","lat":41.6,
            "lng":-70.8,"offset":0,"requestCount":14,"start":"2024-07-12 00:00","station":{"distance":8,"lat":41.5933,"lng":-70.9,
            "name":"new bedford, clarks point, ma","source":"noaa"}}}, {"data":[{"astronomicalDawn":"2024-07-12T07:16:38+00:00",
            "astronomicalDusk":"2024-07-13T02:23:16+00:00","civilDawn":"2024-07-12T08:48:00+00:00",
            "civilDusk":"2024-07-13T00:51:55+00:00","moonFraction":0.3267293520444956,
            "moonPhase":{"closest":{"text":"First quarter","time":"2024-07-13T15:55:00+00:00","value":0.25},
            "current":{"text":"Waxing crescent","time":"2024-07-12T00:00:00+00:00","value":0.19367802604339596}},
            "moonrise":"2024-07-12T15:53:14+00:00","moonset":"2024-07-12T03:26:21+00:00",
            "nauticalDawn":"2024-07-12T08:05:52+00:00","nauticalDusk":"2024-07-13T01:34:03+00:00",
            "sunrise":"2024-07-12T09:21:12+00:00","sunset":"2024-07-13T00:18:43+00:00","time":"2024-07-12T00:00:00+00:00"}],
            "meta":{"cost":1,"dailyQuota":50,"lat":41.6,"lng":-70.8,"requestCount":15,"start":"2024-07-12 00:00"}}]""";
    private static final String expectedWeatherUrl = "https://api.stormglass.io/v2/weather/point?lat=41.6&lng=-70.8&start=1720818000&end=1720818000&source=sg&params=airTemperature,pressure,cloudCover,currentDirection,currentSpeed,gust,humidity,seaLevel,visibility,windDirection,windSpeed,seaLevel,swellDirection,swellHeight,swellPeriod,secondarySwellDirection,secondarySwellHeight,secondarySwellPeriod,waveDirection,waveHeight,wavePeriod,windWaveDirection,windWaveHeight,windWavePeriod";
    private static final String expectedTideUrl = "https://api.stormglass.io/v2/tide/extremes/point?lat=41.6&lng=-70.8&start=1720742400&end=1720828799";
    private static final String expectedAstroUrl = "https://api.stormglass.io/v2/astronomy/point?lat=41.6&lng=-70.8&start=1720742400&end=1720828799&params=astronomicalDawn,astronomicalDusk,civilDawn,civilDusk,moonFraction,moonPhase,moonrise,moonset,sunrise,sunset,time";

    @Test
    public void createUrlTest() throws IOException {
        Instant instant = Instant.parse("2024-07-12T21:00:00.00z");
        StormGlassApiService stormGlassApiService = new StormGlassApiService();

        String weatherUrl = stormGlassApiService.createUrl("weather", instant, 41.6, -70.8);
        String tideUrl = stormGlassApiService.createUrl("tide", instant, 41.6, -70.8);
        String astroUrl = stormGlassApiService.createUrl("astro", instant, 41.6, -70.8);

        assertEquals(weatherUrl, expectedWeatherUrl);
        assertEquals(tideUrl, expectedTideUrl);
        assertEquals(astroUrl, expectedAstroUrl);
    }

    @Test
    public void obtainTestData_UnitTest_WithMocks() {
        Instant instant = Instant.parse("2024-07-12T21:00:00.00z");
        ResponseDataForTest responseDataForTest = new ResponseDataForTest();
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("header", List.of("header1", "header2"));
        List<ApiResponse> expectedResponses;
        try {
            expectedResponses = List.of(
                    new ApiResponse(200, headers, responseDataForTest.getWeatherDataString()),
                    new ApiResponse(200, headers, responseDataForTest.getAstrologicalDataString()),
                    new ApiResponse(200, headers, responseDataForTest.getTideDataString())
            );
        } catch (IOException e) {
            throw new RuntimeException("Unable to obtain expected responses", e);
        }

        StormGlassApiService stormGlassApiService = mock(StormGlassApiService.class);
        when(stormGlassApiService.obtainData(instant, 41.6, -70.8)).thenReturn(expectedResponses);

        List<ApiResponse> data = stormGlassApiService.obtainData(instant, 41.6, -70.8);
        ObjectMapper om = new ObjectMapper();
        JsonNode expectedNode, weatherNode, astrologicalNode, tideNode;
        try {
            expectedNode = om.readTree(expectedResponse);
            weatherNode = om.readTree(data.get(0).getBody());
            astrologicalNode = om.readTree(data.get(1).getBody());
            tideNode = om.readTree(data.get(2).getBody());

        } catch (JsonMappingException e) {
            throw new RuntimeException("Json Mapping Error", e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json Processing Error", e);
        }

        Double expectedHumidity = expectedNode.get(0).get("hours").get(0).get("humidity").get("sg").asDouble();
        Double expectedMoonFraction = expectedNode.get(2).get("data").get(0).get("moonFraction").asDouble();
        Double expectedHeight = expectedNode.get(1).get("data").get(0).get("height").asDouble();

        Double actualHumidity = weatherNode.get("hours").get(0).get("humidity").get("sg").asDouble();
        Double actualMoonFraction = astrologicalNode.get("data").get(0).get("moonFraction").asDouble();
        Double actualHeight = tideNode.get("data").get(0).get("height").asDouble();

        assertEquals(expectedHumidity, actualHumidity);
        assertEquals(expectedMoonFraction, actualMoonFraction);
        assertEquals(expectedHeight, actualHeight);
    }
}
