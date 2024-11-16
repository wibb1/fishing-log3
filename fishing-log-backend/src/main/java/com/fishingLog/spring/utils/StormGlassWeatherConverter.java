package com.fishingLog.spring.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class StormGlassWeatherConverter implements StormGlassDataConverter {
    Map<String, String> weatherDataMap = new HashMap<>() {
        {
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

    public Map<String, Object> dataConverter(String data) throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(data);
        JsonNode actualObj = mapper.readTree(parser);
        JsonNode firstWeatherData = actualObj.get("hours").get(0);
        Set<String> keys = weatherDataMap.keySet();
        for (String each : keys) {
            if (firstWeatherData.get(each) != null) {
                String classType = weatherDataMap.get(each);
                switch (classType) {
                    case "Instant" ->
                            responseData.put(each, Instant.parse(firstWeatherData.get(each).asText()));
                    case "Double" -> responseData.put(each, firstWeatherData.get(each).get("sg").asDouble());
                    default -> throw new IOException("Unrecognized class type in StormGlass Weather Converter");
                }
            }

        }
        return responseData;
    }
}
