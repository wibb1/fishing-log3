package com.fishingLog.spring.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

public class StormGlassContactTest {
    public static void main(String[] args) throws IOException {
        Instant instant = Instant.parse("2024-07-12T21:00:00.00z");
        StormGlassApiService stormGlassApiService = new StormGlassApiService();
        List<ApiResponse> data = stormGlassApiService.obtainData(instant, 41.6, -70.8);
        ObjectMapper om = new ObjectMapper();

        JsonNode weatherNode = om.readTree(data.get(0).getBody());
        JsonNode astrologicalNode = om.readTree(data.get(1).getBody());
        JsonNode tideNode = om.readTree(data.get(2).getBody());
        String enter = "\n";
        String doubleEnter = enter + enter;

        System.out.println("weatherNode" + enter + weatherNode + doubleEnter + "astrologicalNode" + enter +
                           astrologicalNode + doubleEnter + "tideNode" + enter + tideNode);
    }
}
