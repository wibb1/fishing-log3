package com.fishingLog.spring.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit")
@Tag("integration")
public class ResponseDataForTestTest {
    private final String expectedStringResponse = "[{\"hours\":[{\"airTemperature\":{\"sg\":23.38},\"cloudCover\":{\"sg\"" +
            ":43.7},\"currentDirection\":{\"sg\":321.8},\"currentSpeed\":{\"sg\":0.06},\"gust\":{\"sg\":13.53},\"humidity\"" +
            ":{\"sg\":90.9},\"pressure\":{\"sg\":1022.44},\"seaLevel\":{\"sg\":-0.54},\"secondarySwellDirection\":{\"sg\"" +
            ":173.39},\"secondarySwellHeight\":{\"sg\":0.09},\"secondarySwellPeriod\":{\"sg\":8.71},\"swellDirection\":" +
            "{\"sg\":175.11},\"swellHeight\":{\"sg\":0.18},\"swellPeriod\":{\"sg\":5.82},\"time\":\"" +
            "2024-07-12T21:00:00+00:00\",\"visibility\":{\"sg\":24.13},\"waveDirection\":{\"sg\":195.18},\"waveHeight\"" +
            ":{\"sg\":0.76},\"wavePeriod\":{\"sg\":4.67},\"windDirection\":{\"sg\":211.35},\"windSpeed\":{\"sg\":7.16}" +
            ",\"windWaveDirection\":{\"sg\":203.34},\"windWaveHeight\":{\"sg\":0.76},\"windWavePeriod\":{\"sg\":5.36}}]" +
            ",\"meta\":{\"cost\":1,\"dailyQuota\":50,\"end\":\"2024-07-12 21:00\",\"lat\":41.6,\"lng\":-70.8,\"params\"" +
            ":[\"airTemperature\",\"pressure\",\"cloudCover\",\"currentDirection\",\"currentSpeed\",\"gust\",\"humidity" +
            "\",\"seaLevel\",\"visibility\",\"windDirection\",\"windSpeed\",\"seaLevel\",\"swellDirection\"" +
            ",\"swellHeight\",\"swellPeriod\",\"secondarySwellDirection\",\"secondarySwellHeight\",\"secondarySwellPeriod\"" +
            ",\"waveDirection\",\"waveHeight\",\"wavePeriod\",\"windWaveDirection\",\"windWaveHeight\",\"windWavePeriod\"]" +
            ",\"requestCount\":10,\"source\":[\"sg\"],\"start\":\"2024-07-12 21:00\"}},{\"data\":[{\"height\"" +
            ":0.484495766600653,\"time\":\"2024-07-12T04:55:00+00:00\",\"type\":\"high\"},{\"height\":-0.3884259370281767" +
            ",\"time\":\"2024-07-12T10:10:00+00:00\",\"type\":\"low\"},{\"height\":0.5242476943841401,\"time\":" +
            "\"2024-07-12T17:25:00+00:00\",\"type\":\"high\"},{\"height\":-0.2936569385171069,\"time\":\"" +
            "2024-07-12T22:40:00+00:00\",\"type\":\"low\"}],\"meta\":{\"cost\":1,\"dailyQuota\":50,\"datum\":\"MSL\"," +
            "\"end\":\"2024-07-12 23:59\",\"lat\":41.6,\"lng\":-70.8,\"offset\":0,\"requestCount\":11,\"start\":" +
            "\"2024-07-12 00:00\",\"station\":{\"distance\":8,\"lat\":41.5933,\"lng\":-70.9,\"name\":\"new bedford, " +
            "clarks point, ma\",\"source\":\"noaa\"}}},{\"data\":[{\"astronomicalDawn\":\"2024-07-12T07:16:38+00:00\"" +
            ",\"astronomicalDusk\":\"2024-07-13T02:23:16+00:00\",\"civilDawn\":\"2024-07-12T08:48:00+00:00\",\"civilDusk\"" +
            ":\"2024-07-13T00:51:55+00:00\",\"moonFraction\":0.3267293520444956,\"moonPhase\":{\"closest\":{\"text\"" +
            ":\"First quarter\",\"time\":\"2024-07-13T15:55:00+00:00\",\"value\":0.25},\"current\":{\"text\":\"Waxing " +
            "crescent\",\"time\":\"2024-07-12T00:00:00+00:00\",\"value\":0.19367802604339596}},\"moonrise\":\"" +
            "2024-07-12T15:53:14+00:00\",\"moonset\":\"2024-07-12T03:26:21+00:00\",\"nauticalDawn\":\"" +
            "2024-07-12T08:05:52+00:00\",\"nauticalDusk\":\"2024-07-13T01:34:03+00:00\",\"sunrise\":\"" +
            "2024-07-12T09:21:12+00:00\",\"sunset\":\"2024-07-13T00:18:43+00:00\",\"time\":\"" +
            "2024-07-12T00:00:00+00:00\"}],\"meta\":{\"cost\":1,\"dailyQuota\":50,\"lat\":41.6,\"lng\":-70.8,\"" +
            "requestCount\":12,\"start\":\"2024-07-12 00:00\"}}]";

    @Test
    void getDataTest() throws IOException {
        ResponseDataForTest responseDataForTest = new ResponseDataForTest();
        JsonNode actualResponse = responseDataForTest.getData();

        assertEquals(getJsonNode(), actualResponse);
    }

    @Test
    void getDataStringTest() throws IOException {
        ResponseDataForTest responseDataForTest = new ResponseDataForTest();
        String actualResponse = responseDataForTest.getDataString();

        assertEquals(expectedStringResponse, actualResponse);
    }

    @Test
    public void getWeatherDataJsonNodeTest() throws IOException {
        ResponseDataForTest responseDataForTest = new ResponseDataForTest();
        JsonNode actualResponse = responseDataForTest.getWeatherDataJsonNode();

        assertEquals(getJsonNode().get(0), actualResponse);
    }

    @Test
    public void getWeatherDataStringTest() throws IOException {
        ResponseDataForTest responseDataForTest = new ResponseDataForTest();
        String actualResponse = responseDataForTest.getWeatherDataString();

        String expectedWeather = "{\"hours\":[{\"airTemperature\":{\"sg\":23.38},\"cloudCover\":{\"sg\":43.7}," +
                "\"currentDirection\":{\"sg\":321.8},\"currentSpeed\":{\"sg\":0.06},\"gust\":{\"sg\":13.53}," +
                "\"humidity\":{\"sg\":90.9},\"pressure\":{\"sg\":1022.44},\"seaLevel\":{\"sg\":-0.54}," +
                "\"secondarySwellDirection\":{\"sg\":173.39},\"secondarySwellHeight\":{\"sg\":0.09}," +
                "\"secondarySwellPeriod\":{\"sg\":8.71},\"swellDirection\":{\"sg\":175.11},\"swellHeight\":{\"sg\"" +
                ":0.18},\"swellPeriod\":{\"sg\":5.82},\"time\":\"2024-07-12T21:00:00+00:00\",\"visibility\":{\"sg\":" +
                "24.13},\"waveDirection\":{\"sg\":195.18},\"waveHeight\":{\"sg\":0.76},\"wavePeriod\":{\"sg\":4.67}," +
                "\"windDirection\":{\"sg\":211.35},\"windSpeed\":{\"sg\":7.16},\"windWaveDirection\":{\"sg\":203.34}," +
                "\"windWaveHeight\":{\"sg\":0.76},\"windWavePeriod\":{\"sg\":5.36}}],\"meta\":{\"cost\":1," +
                "\"dailyQuota\":50,\"end\":\"2024-07-12 21:00\",\"lat\":41.6,\"lng\":-70.8,\"params\":[" +
                "\"airTemperature\",\"pressure\",\"cloudCover\",\"currentDirection\",\"currentSpeed\",\"gust\"," +
                "\"humidity\",\"seaLevel\",\"visibility\",\"windDirection\",\"windSpeed\",\"seaLevel\"," +
                "\"swellDirection\",\"swellHeight\",\"swellPeriod\",\"secondarySwellDirection\",\"secondarySwellHeight\"" +
                ",\"secondarySwellPeriod\",\"waveDirection\",\"waveHeight\",\"wavePeriod\",\"windWaveDirection\"," +
                "\"windWaveHeight\",\"windWavePeriod\"],\"requestCount\":10,\"source\":[\"sg\"],\"start\":\"2024-07-12 21:00\"}}";
        assertEquals(expectedWeather, actualResponse);
    }

    @Test
    public void getTideDataJsonNodeTest() throws IOException {
        ResponseDataForTest responseDataForTest = new ResponseDataForTest();
        JsonNode actualResponse = responseDataForTest.getTideDataJsonNode();

        assertEquals(getJsonNode().get(1), actualResponse);
    }

    @Test
    public void getTideDataStringTest() throws IOException {
        ResponseDataForTest responseDataForTest = new ResponseDataForTest();
        String actualResponse = responseDataForTest.getTideDataString();

        String expectedTideData = "{\"data\":[{\"height\":0.484495766600653,\"time\":" +
                "\"2024-07-12T04:55:00+00:00\",\"type\":\"high\"},{\"height\":-0.3884259370281767,\"time\":" +
                "\"2024-07-12T10:10:00+00:00\",\"type\":\"low\"},{\"height\":0.5242476943841401,\"time\":" +
                "\"2024-07-12T17:25:00+00:00\",\"type\":\"high\"},{\"height\":-0.2936569385171069,\"time\":" +
                "\"2024-07-12T22:40:00+00:00\",\"type\":\"low\"}],\"meta\":{\"cost\":1,\"dailyQuota\":50,\"datum\":\"MSL\"," +
                "\"end\":\"2024-07-12 23:59\",\"lat\":41.6,\"lng\":-70.8,\"offset\":0,\"requestCount\":11,\"start\":" +
                "\"2024-07-12 00:00\",\"station\":{\"distance\":8,\"lat\":41.5933,\"lng\":-70.9,\"name\":\"" +
                "new bedford, clarks point, ma\",\"source\":\"noaa\"}}}";
        assertEquals(expectedTideData, actualResponse);
    }

    @Test
    public void getAstrologicalDataJsonNodeTest() throws IOException {
        ResponseDataForTest responseDataForTest = new ResponseDataForTest();
        JsonNode actualResponse = responseDataForTest.getAstrologicalDataJsonNode();

        assertEquals(getJsonNode().get(2), actualResponse);
    }

    @Test
    public void getAstrologicalDataStringTest() throws IOException {
        ResponseDataForTest responseDataForTest = new ResponseDataForTest();
        String actualResponse = responseDataForTest.getAstrologicalDataString();

        String expectedAstrologicalData = "{\"data\":[{\"astronomicalDawn\":\"2024-07-12T07:16:38+00:00" +
                "\",\"astronomicalDusk\":\"2024-07-13T02:23:16+00:00\",\"civilDawn\":\"2024-07-12T08:48:00+00:00\"," +
                "\"civilDusk\":\"2024-07-13T00:51:55+00:00\",\"moonFraction\":0.3267293520444956,\"moonPhase\":{\"closest" +
                "\":{\"text\":\"First quarter\",\"time\":\"2024-07-13T15:55:00+00:00\",\"value\":0.25},\"current\":{" +
                "\"text\":\"Waxing crescent\",\"time\":\"2024-07-12T00:00:00+00:00\",\"value\":0.19367802604339596}}," +
                "\"moonrise\":\"2024-07-12T15:53:14+00:00\",\"moonset\":\"2024-07-12T03:26:21+00:00\",\"nauticalDawn\":" +
                "\"2024-07-12T08:05:52+00:00\",\"nauticalDusk\":\"2024-07-13T01:34:03+00:00\",\"sunrise\":" +
                "\"2024-07-12T09:21:12+00:00\",\"sunset\":\"2024-07-13T00:18:43+00:00\",\"time\":\"" +
                "2024-07-12T00:00:00+00:00\"}],\"meta\":{\"cost\":1,\"dailyQuota\":50,\"lat\":41.6,\"lng\":-70.8," +
                "\"requestCount\":12,\"start\":\"2024-07-12 00:00\"}}";
        assertEquals(expectedAstrologicalData, actualResponse);
    }

    private JsonNode getJsonNode() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.readTree(expectedStringResponse);
    }
}
