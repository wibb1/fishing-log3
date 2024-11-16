package com.fishingLog.spring.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.model.TideStation;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class StormGlassTideConverter implements StormGlassDataConverter{
    Map<String, String> tideDataMap = new HashMap<>() {
        {
            put("firstTide", "Tide");
            put("secondTide", "Tide");
            put("thirdTide", "Tide");
            put("fourthTide", "Tide");
        }
    };

    public Map<String, Object> dataConverter(String data) throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(data);
        JsonNode actualObj = mapper.readTree(parser);
        JsonNode tideData = actualObj.get("data");
        JsonNode metaJson = actualObj.get("meta").get("station");
        String[] tideNames = tideDataMap.keySet().toArray(new String[0]);
        int counter = 0;
        for (JsonNode each : tideData) {
            Tide tide = new Tide();
            tide.setHeight(each.get("height").asDouble());
            tide.setTimeWithString(each.get("time").asText());
            tide.setType(each.get("type").asText());
            responseData.put(tideNames[counter], tide);
            counter++;
        }
        TideStation tideStation = new TideStation(metaJson);
        responseData.put("station", tideStation);
        return responseData;
    }
}
