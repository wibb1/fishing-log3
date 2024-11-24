package com.fishingLog.spring.utils;

import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.model.TideStation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("integration")
public class StormGlassTideConverterTest {
    private final StormGlassTideConverter stormGlassTideConverter = new StormGlassTideConverter();
    private final ResponseDataForTest response = new ResponseDataForTest();
    private Map<String,Object> actualResponse;
    private Map<String,Object> expectedResponse;

    @Test
    public void tideDataConverterTest() throws IOException {
        StormGlassApiService stormGlassApiService = mock(StormGlassApiService.class);
        Map<String, List<String>> headers = Map.of(
                "access-control-allow-origin", List.of("*"),
                "content-length", List.of("583"),
                "content-type", List.of("application/json"),
                "date", List.of("Mon, 04 Nov 2024 02:04:39 GMT"),
                "server", List.of("gunicorn"),
                "vary", List.of("Accept-Encoding")
        );
        ApiResponse apiResponse = new ApiResponse(200, headers, response.getDataString());
        when(stormGlassApiService.obtainData(Instant.parse("2024-07-12T21:00:00.00z"), 41.6, -70.8)).thenReturn(List.of(apiResponse));
        actualResponse = stormGlassTideConverter.dataConverter(response.getTideDataString());
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
        assertEquals(expectedTide.getStationLat(), actualTide.getStationLat());
        assertEquals(expectedTide.getStationLng(), actualTide.getStationLng());
        assertEquals(expectedTide.getStationSource(), actualTide.getStationSource());
    }
}
