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
    private static String weatherRequest = "airTemperature,pressure,cloudCover,currentDirection,currentSpeed,gust,humidity,seaLevel,visibility,windDirection,windSpeed,seaLevel,swellDirection,swellHeight,swellPeriod,secondarySwellDirection,secondarySwellHeight,secondarySwellPeriod,waveDirection,waveHeight,wavePeriod,windWaveDirection,windWaveHeight,windWavePeriod";
    private static String astroRequest = "astronomicalDawn,astronomicalDusk,civilDawn,civilDusk,moonFraction,moonPhase,moonrise,moonset,sunrise,sunset,time";
    private static String tideRequest = "first_type,first_height,first_time,second_type,second_height,second_type,third_type,third_time,third_height,fourth_type,fourth_time,fourth_height";
    private static String[] requestTypes = {"weather", "tide", "astro"};
    private Instant parsedTime;
    private Double latitude;
    private Double longitude;
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
            HttpRequest httpRequest = null;
            try {
                httpRequest = HttpRequest.newBuilder()
                        .uri(URI.create(createUrl(request)))
                        .header("Authorization", ENVVariables.getStormGlassApiKey())
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();
                response = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
                if (response == null || response.statusCode() != 200 || responses.contains("errors")) throw new AssertionError();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (AssertionError e) {
                System.out.println(response);
                e.printStackTrace();
            }
            responses.add(response.body());
        }
        return responses;
    }
    public String createUrl(String request) throws IOException {
        switch (request) {
            case "weather":
                return getBaseUrl("weather/point")
                        .append("&start=").append(this.getStartHour())
                        .append("&end=").append(this.getEndHour())
                        .append("&source=sg&params=").append(this.weatherRequest).toString();
            case "tide":
                return getBaseUrl("tide/extremes/point")
                        .append("&start=").append(this.getStartDay())
                        .append("&end=").append(this.getEndDay()).toString();
            case "astro":
                return getBaseUrl("astronomy/point")
                        .append("&start=").append(this.getStartDay())
                        .append("&end=").append(this.getEndDay())
                        .append("&params=").append(this.astroRequest).toString();
            default:
                throw new IOException("Error:_Unknown_URL=see_'ApiCall.java'_'createUrl'; attempted request to " + request);
        }
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
        StringBuilder sb = new StringBuilder();
        sb.append(zonedDateTime.getYear() + "-");
        sb.append(String.format("%02d", zonedDateTime.getMonthValue()) + "-");
        sb.append(String.format("%02d", zonedDateTime.getDayOfMonth()));
        sb.append("T" + timeString);
        return sb.toString();
    }
}
