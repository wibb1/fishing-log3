package com.fishingLog.spring.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class StormGlassWeatherConverter {
    Map<String, String> weatherDataMap = new HashMap<>() {
        {
            // weather - hours
            put("airTemperature", "Double");
            put("cloudCover", "Double");
            put("currentDirection", "Double");
            put("currentSpeed", "Double");
            put("gust", "Double");
            put("humidity", "Double");
            put("pressure", "Double");
            put("seaLevel", "Double");
            put("secondarySwellDirection", "Double");
            put("secondarySwellHeight", "Double");
            put("secondarySwellPeriod", "Double");
            put("swellDirection", "Double");
            put("swellHeight", "Double");
            put("swellPeriod", "Double");
            put("time", "Instant");
            put("visibility", "Double");
            put("waveDirection", "Double");
            put("waveHeight", "Double");
            put("wavePeriod", "Double");
            put("windDirection", "Double");
            put("windSpeed", "Double");
            put("windWaveDirection", "Double");
            put("windWaveHeight", "Double");
            put("windWavePeriod", "Double");
        }
    };

    public Map<String, Object> weatherDataConverter(String data, String[] keys) throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(data);
        JsonNode actualObj = mapper.readTree(parser);
        JsonNode firstWeatherData = actualObj.get("hours").get(0);
        JsonNode metaData = actualObj.get("meta").get("params");
        for (String each : keys) {
            if (weatherDataMap.get(each) != (null)) {
                switch (weatherDataMap.get(each)) {
                    case "Instant" ->
                            responseData.put(each, Instant.parse(firstWeatherData.get(each).get("sg").asText()));
                    case "Double" -> responseData.put(each, firstWeatherData.get(each).get("sg").asDouble());
                    default -> throw new IOException("Unrecognized class type in StormGlass Weather Converter");
                }
            }

        }
        return responseData;
    }
}
