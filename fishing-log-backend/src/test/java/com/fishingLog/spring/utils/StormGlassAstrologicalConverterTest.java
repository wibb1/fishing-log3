package com.fishingLog.spring.utils;

import com.fishingLog.spring.service.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StormGlassAstrologicalConverterTest extends BaseIntegrationTest {
    private final ResponseDataForTest response = new ResponseDataForTest();
    private Map<String,Object> actualResponse;
    private Map<String,Object> expectedResponse;
    private final StormGlassAstrologicalConverter stormGlassAstrologicalConverter = new StormGlassAstrologicalConverter();
    @Test
    public void astrologicalDataConverterTest() throws IOException {
        StormGlassApiService stormGlassApiService = mock(StormGlassApiService.class);
        Map<String, List<String>> headers = Map.of(
                "access-control-allow-origin", List.of("*"),
                "content-length", List.of("792"),
                "content-type", List.of("application/json"),
                "date", List.of("Mon, 04 Nov 2024 02:04:45 GMT"),
                "server", List.of("gunicorn"),
                "vary", List.of("Accept-Encoding")
        );
        ApiResponse apiResponse = new ApiResponse(200, headers, response.getDataString());
        when(stormGlassApiService.obtainData(Instant.parse("2024-07-12T21:00:00.00z"), 41.6, -70.8)).thenReturn(List.of(apiResponse));
        actualResponse = stormGlassAstrologicalConverter.dataConverter(response.getAstrologicalDataString());
        expectedResponse = response.getAstroMapTest();

        assertEqualsKey("astronomicalDawn");
        assertEqualsKey("astronomicalDusk");
        assertEqualsKey("civilDawn");
        assertEqualsKey("civilDusk");
        assertEqualsKey("moonFraction");
        assertEqualsMoonPhase("current");
        assertEqualsMoonPhase("closest");
        assertEqualsKey("moonrise");
        assertEqualsKey("moonset");
        assertEqualsKey("nauticalDawn");
        assertEqualsKey("sunrise");
        assertEqualsKey("sunset");
        assertEqualsKey("time");
    }

    private void assertEqualsKey(String key) {
        assertEquals(expectedResponse.get(key), actualResponse.get(key));
    }

    private void assertEqualsMoonPhase(String key) {
        MoonPhase expectedMoonPhase = (MoonPhase) expectedResponse.get(key);
        MoonPhase actualMoonPhase = (MoonPhase) actualResponse.get(key);
        assertEquals(expectedMoonPhase.getText(), actualMoonPhase.getText());
        assertEquals(expectedMoonPhase.getValue(), actualMoonPhase.getValue());
        assertEquals(expectedMoonPhase.getTime(), actualMoonPhase.getTime());
    }
}
