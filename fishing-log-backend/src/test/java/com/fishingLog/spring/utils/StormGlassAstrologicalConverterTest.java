package com.fishingLog.spring.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StormGlassAstrologicalConverterTest {
    private final ResponseDataForTest response = new ResponseDataForTest();
    private Map<String,Object> actualResponse;
    private Map<String,Object> expectedResponse;
    private final StormGlassAstrologicalConverter stormGlassAstrologicalConverter = new StormGlassAstrologicalConverter();
    @Test
    public void astrologicalDataConverterTest() throws IOException {
        StormGlassApiService stormGlassApiService = mock(StormGlassApiService.class);
        when(stormGlassApiService.obtainData()).thenReturn(Collections.singletonList(response.getDataString()));

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
