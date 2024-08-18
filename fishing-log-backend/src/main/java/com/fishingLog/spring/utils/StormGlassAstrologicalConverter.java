package com.fishingLog.spring.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StormGlassAstrologicalConverter implements StormGlassDataConverter {

    Map<String, String> astrologicalDataMap = new HashMap<>() {
        {
            {
                put("astronomicalDawn", "Instant");
                put("astronomicalDusk", "Instant");
                put("civilDawn", "Instant");
                put("civilDusk", "Instant");
                put("moonrise", "Instant");
                put("moonset", "Instant");
                put("nauticalDusk", "Instant");
                put("nauticalDawn", "Instant");
                put("sunrise", "Instant");
                put("sunset", "Instant");
                put("time", "Instant");
                put("closest", "MoonPhase");
                put("current", "MoonPhase");
            }
        }
    };

    public Map<String, Object> dataConverter(String data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(data);
        JsonNode actualObj = mapper.readTree(parser);
        JsonNode dataJson = actualObj.get("data").get(0);

        Map<String, Object> responseData = new HashMap<>();
        Set<String> keys = astrologicalDataMap.keySet();
        for (String each : keys) {
            String classType = astrologicalDataMap.get(each);
            if (astrologicalDataMap.get(each) != null) {
                switch (classType) {
                    case "Instant" -> responseData.put(each, Instant.parse(dataJson.get(each).asText()));
                    case "MoonPhase" -> {
                        MoonPhase moonPhase = new MoonPhase(dataJson.get("moonPhase").get(each));
                        responseData.put(each, moonPhase);
                    }
                    default -> throw new IOException("Unrecognized class type in StormGlass Astrological Converter");
                }
            }
        }
        return responseData;
    }
}
