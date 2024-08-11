package com.fishingLog.spring.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StormGlassApiServiceTest {
    private static final String expectedResponse = "[{\"hours\":[{\"airTemperature\":{\"sg\":23.38},\"cloudCover\":{\"sg\":43.7},\"currentDirection\":{\"sg\":321.8},\"currentSpeed\":{\"sg\":0.06},\"gust\":{\"sg\":13.53},\"humidity\":{\"sg\":90.9},\"pressure\":{\"sg\":1022.44},\"seaLevel\":{\"sg\":-0.54},\"secondarySwellDirection\":{\"sg\":173.39},\"secondarySwellHeight\":{\"sg\":0.09},\"secondarySwellPeriod\":{\"sg\":8.71},\"swellDirection\":{\"sg\":175.11},\"swellHeight\":{\"sg\":0.18},\"swellPeriod\":{\"sg\":5.82},\"time\":\"2024-07-12T21:00:00+00:00\",\"visibility\":{\"sg\":24.13},\"waveDirection\":{\"sg\":195.18},\"waveHeight\":{\"sg\":0.76},\"wavePeriod\":{\"sg\":4.67},\"windDirection\":{\"sg\":211.35},\"windSpeed\":{\"sg\":7.16},\"windWaveDirection\":{\"sg\":203.34},\"windWaveHeight\":{\"sg\":0.76},\"windWavePeriod\":{\"sg\":5.36}}],\"meta\":{\"cost\":1,\"dailyQuota\":50,\"end\":\"2024-07-12 21:00\",\"lat\":41.6,\"lng\":-70.8,\"params\":[\"airTemperature\",\"pressure\",\"cloudCover\",\"currentDirection\",\"currentSpeed\",\"gust\",\"humidity\",\"seaLevel\",\"visibility\",\"windDirection\",\"windSpeed\",\"seaLevel\",\"swellDirection\",\"swellHeight\",\"swellPeriod\",\"secondarySwellDirection\",\"secondarySwellHeight\",\"secondarySwellPeriod\",\"waveDirection\",\"waveHeight\",\"wavePeriod\",\"windWaveDirection\",\"windWaveHeight\",\"windWavePeriod\"],\"requestCount\":13,\"source\":[\"sg\"],\"start\":\"2024-07-12 21:00\"}}\n" +
            ", {\"data\":[{\"height\":0.484495766600653,\"time\":\"2024-07-12T04:55:00+00:00\",\"type\":\"high\"},{\"height\":-0.3884259370281767,\"time\":\"2024-07-12T10:10:00+00:00\",\"type\":\"low\"},{\"height\":0.5242476943841401,\"time\":\"2024-07-12T17:25:00+00:00\",\"type\":\"high\"},{\"height\":-0.2936569385171069,\"time\":\"2024-07-12T22:40:00+00:00\",\"type\":\"low\"}],\"meta\":{\"cost\":1,\"dailyQuota\":50,\"datum\":\"MSL\",\"end\":\"2024-07-12 23:59\",\"lat\":41.6,\"lng\":-70.8,\"offset\":0,\"requestCount\":14,\"start\":\"2024-07-12 00:00\",\"station\":{\"distance\":8,\"lat\":41.5933,\"lng\":-70.9,\"name\":\"new bedford, clarks point, ma\",\"source\":\"noaa\"}}}\n" +
            ", {\"data\":[{\"astronomicalDawn\":\"2024-07-12T07:16:38+00:00\",\"astronomicalDusk\":\"2024-07-13T02:23:16+00:00\",\"civilDawn\":\"2024-07-12T08:48:00+00:00\",\"civilDusk\":\"2024-07-13T00:51:55+00:00\",\"moonFraction\":0.3267293520444956,\"moonPhase\":{\"closest\":{\"text\":\"First quarter\",\"time\":\"2024-07-13T15:55:00+00:00\",\"value\":0.25},\"current\":{\"text\":\"Waxing crescent\",\"time\":\"2024-07-12T00:00:00+00:00\",\"value\":0.19367802604339596}},\"moonrise\":\"2024-07-12T15:53:14+00:00\",\"moonset\":\"2024-07-12T03:26:21+00:00\",\"nauticalDawn\":\"2024-07-12T08:05:52+00:00\",\"nauticalDusk\":\"2024-07-13T01:34:03+00:00\",\"sunrise\":\"2024-07-12T09:21:12+00:00\",\"sunset\":\"2024-07-13T00:18:43+00:00\",\"time\":\"2024-07-12T00:00:00+00:00\"}],\"meta\":{\"cost\":1,\"dailyQuota\":50,\"lat\":41.6,\"lng\":-70.8,\"requestCount\":15,\"start\":\"2024-07-12 00:00\"}}\n" +
            "]";
    private static final String expectedWeatherUrl = "https://api.stormglass.io/v2/weather/point?lat=41.6&lng=-70.8&start=1720818000&end=1720818000&source=sg&params=airTemperature,pressure,cloudCover,currentDirection,currentSpeed,gust,humidity,seaLevel,visibility,windDirection,windSpeed,seaLevel,swellDirection,swellHeight,swellPeriod,secondarySwellDirection,secondarySwellHeight,secondarySwellPeriod,waveDirection,waveHeight,wavePeriod,windWaveDirection,windWaveHeight,windWavePeriod";
    private static final String expectedTideUrl = "https://api.stormglass.io/v2/tide/extremes/point?lat=41.6&lng=-70.8&start=1720742400&end=1720828799";
    private static final String expectedAstroUrl = "https://api.stormglass.io/v2/astronomy/point?lat=41.6&lng=-70.8&start=1720742400&end=1720828799&params=astronomicalDawn,astronomicalDusk,civilDawn,civilDusk,moonFraction,moonPhase,moonrise,moonset,sunrise,sunset,time";

    @Test
    public void createUrlTest() throws IOException {
        Instant instant = Instant.parse("2024-07-12T21:00:00.00z");
        StormGlassApiService stormGlassApiService = new StormGlassApiService(instant, 41.6,-70.8);

        String weatherUrl = stormGlassApiService.createUrl("weather");
        String tideUrl = stormGlassApiService.createUrl("tide");
        String astroUrl = stormGlassApiService.createUrl("astro");

        assertEquals(weatherUrl, expectedWeatherUrl);
        assertEquals(tideUrl, expectedTideUrl);
        assertEquals(astroUrl, expectedAstroUrl);
    }

    @Test // this method actually tests the endpoints for use during development TODO - mock this test for production
    public void obtainDataTest() throws JsonProcessingException {
        Instant instant = Instant.parse("2024-07-12T21:00:00.00z");
        StormGlassApiService stormGlassApiService = new StormGlassApiService(instant, 41.6,-70.8);
        List<String> data = stormGlassApiService.obtainData();
        ObjectMapper om = new ObjectMapper();
        JsonNode expectedNode = om.readTree(expectedResponse);
        JsonNode dataNode = om.readTree(data.toString());
        assertEquals(expectedNode.get("humidity"), dataNode.get("humidity"));
        assertEquals(expectedNode.get("astronomicalDusk"), dataNode.get("astronomicalDusk"));
        assertEquals(expectedNode.get("moonFraction"), dataNode.get("moonFraction"));
    }
}
