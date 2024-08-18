package com.fishingLog.spring.utils;

import com.fishingLog.ENVVariables;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class StormGlassApiService {
    private static final String weatherRequest = "airTemperature,pressure,cloudCover,currentDirection,currentSpeed,gust,humidity,seaLevel,visibility,windDirection,windSpeed,seaLevel,swellDirection,swellHeight,swellPeriod,secondarySwellDirection,secondarySwellHeight,secondarySwellPeriod,waveDirection,waveHeight,wavePeriod,windWaveDirection,windWaveHeight,windWavePeriod";
    private static final String astroRequest = "astronomicalDawn,astronomicalDusk,civilDawn,civilDusk,moonFraction,moonPhase,moonrise,moonset,sunrise,sunset,time";
    private static final String[] requestTypes = {"weather", "tide", "astro"};
    private final Instant parsedTime;
    private final Double latitude;
    private final Double longitude;
    public StormGlassApiService(Instant parsedTime,
                                Double latitude,
                                Double longitude) {
        this.parsedTime = parsedTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public List<String> obtainData() {
        HttpResponse<String> response = null;
        List<String> responses = new ArrayList<>();
        for (String request : requestTypes) {
            HttpRequest httpRequest;
            try {
                httpRequest = HttpRequest.newBuilder()
                        .uri(URI.create(createUrl(request)))
                        .header("Authorization", ENVVariables.getStormGlassApiKey())
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();
                response = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
                if (response == null || response.body() == null || response.statusCode() != 200 || responses.contains("errors")) throw new IOException();
            } catch (IOException | InterruptedException e) {
                System.out.println(response.body());
                e.printStackTrace();
            }
            responses.add(response.body());
        }
        return responses;
    }
    public String createUrl(String request) throws IOException {
        return switch (request) {
            case "weather" -> getBaseUrl("weather/point")
                    .append("&start=").append(this.getStartHour())
                    .append("&end=").append(this.getEndHour())
                    .append("&source=sg&params=").append(weatherRequest).toString();
            case "tide" -> getBaseUrl("tide/extremes/point")
                    .append("&start=").append(this.getStartDay())
                    .append("&end=").append(this.getEndDay()).toString();
            case "astro" -> getBaseUrl("astronomy/point")
                    .append("&start=").append(this.getStartDay())
                    .append("&end=").append(this.getEndDay())
                    .append("&params=").append(astroRequest).toString();
            default ->
                    throw new IOException("Error:_Unknown_URL=see_'ApiCall.java'_'createUrl'; attempted request to " + request);
        };
    }
    private StringBuilder getBaseUrl(String base) {
        return new StringBuilder("https://api.stormglass.io/v2/")
                .append(base)
                .append("?lat=").append(this.latitude)
                .append("&lng=").append(this.longitude);
    }
    private Long getStartHour() {
        return this.parsedTime.atZone(ZoneOffset.UTC).toEpochSecond();
    }
    private Long getEndHour() {
        return this.parsedTime.atZone(ZoneOffset.UTC).toEpochSecond();
    }
    private Long getStartDay() {
        Instant instant = Instant.parse(getDayString("00:00:00.00z"));
        return instant.atZone(ZoneOffset.UTC).toEpochSecond();
    }
    private Long getEndDay() {
        Instant instant = Instant.parse(getDayString("23:59:59.00z"));
        return instant.atZone(ZoneOffset.UTC).toEpochSecond();
    }
    private String getDayString(String timeString) {
        ZonedDateTime zonedDateTime = parsedTime.atZone(ZoneOffset.UTC);
        return zonedDateTime.getYear() + "-" +
                String.format("%02d", zonedDateTime.getMonthValue()) + "-" +
                String.format("%02d", zonedDateTime.getDayOfMonth()) +
                "T" + timeString;
    }
}
