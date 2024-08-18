package com.fishingLog.spring.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.model.TideStation;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ResponseDataForTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final JsonFactory factory = mapper.getFactory();

    public ResponseDataForTest() {
    }

    public JsonNode getData() throws IOException {
        JsonParser parser = factory.createParser(new File("src/test/resources/static/ResponseDataTest.json"));
        return mapper.readTree(parser);
    }

    public String getDataString() throws IOException {
        return getData().toString();
    }

    public JsonNode getWeatherDataJsonNode() throws IOException {
        return getData().get(0);
    }

    public String getWeatherDataString() throws IOException {
        return getAstrologicalDataJsonNode().toString();
    }

    public JsonNode getTideDataJsonNode() throws IOException {
        return getData().get(1);
    }

    public String getTideDataString() throws IOException {
        return getTideDataJsonNode().toString();
    }

    public JsonNode getAstrologicalDataJsonNode() throws IOException {
        return getData().get(2);
    }

    public String getAstrologicalDataString() throws IOException {
        return getAstrologicalDataJsonNode().toString();
    }

    public Map<String, Object> getAstroMapTest() {
        return new HashMap<>() {
            {
                put("astronomicalDawn", Instant.parse("2024-07-12T07:16:38+00:00")); //1720754781L
                put("astronomicalDusk", Instant.parse("2024-07-13T02:23:16+00:00")); //1720776072L
                put("civilDawn", Instant.parse("2024-07-12T08:48:00+00:00")); //1720774080);
                put("civilDusk", Instant.parse("2024-07-13T00:51:55+00:00")); //1720831915);
                put("moonrise", Instant.parse("2024-07-12T15:53:14+00:00")); //1720799594);
                put("moonset", Instant.parse("2024-07-12T03:26:21+00:00")); //
                put("nauticalDusk", Instant.parse("2024-07-13T01:34:03+00:00")); //1720834443);
                put("nauticalDawn", Instant.parse("2024-07-12T08:05:52+00:00"));
                put("sunrise", Instant.parse("2024-07-12T09:21:12+00:00")); //1720776072);
                put("sunset", Instant.parse("2024-07-13T00:18:43+00:00")); //1720829923);
                put("time", Instant.parse("2024-07-12T00:00:00+00:00")); //1720742400);
                put("closest", new MoonPhase("First quarter", Instant.parse("2024-07-13T15:55:00+00:00"), 0.25)); //1720886100
                put("current", new MoonPhase("Waxing crescent", Instant.parse("2024-07-12T00:00:00+00:00"), 0.19367802604339596)); //1720742400L
            }
        };
    }

    public Map<String, Object> getTideMapTest() {
        return new HashMap<>() {{
            put("firstTide", new Tide(-0.2936569385171069, Instant.parse("2024-07-12T22:40:00Z"), "low"));
            put("secondTide", new Tide(0.5242476943841401, Instant.parse("2024-07-12T17:25:00Z"), "high"));
            put("thirdTide", new Tide(0.484495766600653, Instant.parse("2024-07-12T04:55:00Z"), "high"));
            put("fourthTide", new Tide(-0.3884259370281767, Instant.parse("2024-07-12T10:10:00Z"), "low"));
            put("station", new TideStation(8, 41.5933, -70.9, "new bedford, clarks point, ma", "noaa"));
        }};
    }
}
