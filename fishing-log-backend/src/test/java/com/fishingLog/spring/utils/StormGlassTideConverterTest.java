package com.fishingLog.spring.utils;

import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.model.TideStation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StormGlassTideConverterTest {
    private final StormGlassTideConverter stormGlassTideConverter = new StormGlassTideConverter();
    private final ResponseDataForTest response = new ResponseDataForTest();
    private Map<String,Object> actualResponse;
    private Map<String,Object> expectedResponse;

    @Test
    public void tideDataConverterTest() throws IOException {
        StormGlassApiService stormGlassApiService = mock(StormGlassApiService.class);
        when(stormGlassApiService.obtainData()).thenReturn(Collections.singletonList(response.getDataString()));

        actualResponse = stormGlassTideConverter.tideDataConverter(response.getTideDataString());
        expectedResponse = response.getTideMapTest();

        assertEqualsTide("firstTide");
        assertEqualsTide("secondTide");
        assertEqualsTide("thirdTide");
        assertEqualsTide("fourthTide");
        assertEqualsStation();
    }

    private void assertEqualsTide(String key) {
        Tide expectedTide = (Tide) expectedResponse.get(key);
        Tide actualTide = (Tide) actualResponse.get(key);

        assertEquals(expectedTide.getType(), actualTide.getType());
        assertEquals(expectedTide.getHeight(), actualTide.getHeight());
        assertEquals(expectedTide.getTime(), actualTide.getTime());
    }

    private void assertEqualsStation() {
        TideStation expectedTide = (TideStation) expectedResponse.get("station");
        TideStation actualTide = (TideStation) actualResponse.get("station");

        assertEquals(expectedTide.getStationName(), actualTide.getStationName());
        assertEquals(expectedTide.getStationDistance(), actualTide.getStationDistance());
        assertEquals(expectedTide.getStationLat(), actualTide.getStationLat());
        assertEquals(expectedTide.getStationLng(), actualTide.getStationLng());
        assertEquals(expectedTide.getStationSource(), actualTide.getStationSource());
    }
}
