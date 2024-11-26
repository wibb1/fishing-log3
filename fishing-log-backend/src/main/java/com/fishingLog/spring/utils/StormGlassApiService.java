package com.fishingLog.spring.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

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
import java.util.Map;

@Component
public class StormGlassApiService {
    private static final String weatherRequest = "airTemperature,pressure,cloudCover,currentDirection,currentSpeed,gust,humidity,seaLevel,visibility,windDirection,windSpeed,seaLevel,swellDirection,swellHeight,swellPeriod,secondarySwellDirection,secondarySwellHeight,secondarySwellPeriod,waveDirection,waveHeight,wavePeriod,windWaveDirection,windWaveHeight,windWavePeriod";
    private static final String astroRequest = "astronomicalDawn,astronomicalDusk,civilDawn,civilDusk,moonFraction,moonPhase,moonrise,moonset,sunrise,sunset,time";
    private static final String[] requestTypes = {"weather", "tide", "astro"};

    public StormGlassApiService() {
    }

    public List<ApiResponse> obtainData(Instant parsedTime, Double latitude, Double longitude) {
        List<ApiResponse> responses = new ArrayList<>();
        Dotenv dotenv = Dotenv.load();
        String STORMGLASS_API_KEY = dotenv.get("STORMGLASS_API_KEY");
        for (String request : requestTypes) {
            try {
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(URI.create(createUrl(request, parsedTime, latitude, longitude)))
                        .header("Authorization", STORMGLASS_API_KEY)
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
                assert response != null;
                if (response.body() == null || response.statusCode() != 200) {
                    ApiResponse apiResponse = new ApiResponse(response.statusCode(), response.headers().map(), response.body());
                    apiResponse.setErrors(Map.of("Error in "+ request, "Error fetching data for " + request));
                    responses.add(apiResponse);
                } else {
                    responses.add(new ApiResponse(response.statusCode(), response.headers().map(), response.body()));
                }
            } catch(IOException | InterruptedException e){
                e.printStackTrace();
                ApiResponse apiResponse = new ApiResponse(500, null, null);
                apiResponse.setErrors(Map.of("exception", "Exception occurred: " + e.getMessage()));
                responses.add(apiResponse);
            }
        }
        return responses;
    }
    public String createUrl(String request, Instant parsedTime, Double latitude, Double longitude) throws IOException {
        return switch (request) {
            case "weather" -> getBaseUrl("weather/point", latitude, longitude)
                    .append("&start=").append(this.getStartHour(parsedTime))
                    .append("&end=").append(this.getEndHour(parsedTime))
                    .append("&source=sg&params=").append(weatherRequest).toString();
            case "tide" -> getBaseUrl("tide/extremes/point", latitude, longitude)
                    .append("&start=").append(this.getStartDay(parsedTime))
                    .append("&end=").append(this.getEndDay(parsedTime)).toString();
            case "astro" -> getBaseUrl("astronomy/point", latitude, longitude)
                    .append("&start=").append(this.getStartDay(parsedTime))
                    .append("&end=").append(this.getEndDay(parsedTime))
                    .append("&params=").append(astroRequest).toString();
            default ->
                    throw new IOException("Error:_Unknown_URL=see_'ApiCall.java'_'createUrl'; attempted request to " + request);
        };
    }
    private StringBuilder getBaseUrl(String base, Double latitude, Double longitude) {
        return new StringBuilder("https://api.stormglass.io/v2/")
                .append(base)
                .append("?lat=").append(latitude)
                .append("&lng=").append(longitude);
    }
    private Long getStartHour(Instant parsedTime) {
        return parsedTime.atZone(ZoneOffset.UTC).toEpochSecond();
    }
    private Long getEndHour(Instant parsedTime) {
        return parsedTime.atZone(ZoneOffset.UTC).toEpochSecond();
    }
    private Long getStartDay(Instant parsedTime) {
        Instant instant = Instant.parse(getDayString("00:00:00.00z", parsedTime));
        return instant.atZone(ZoneOffset.UTC).toEpochSecond();
    }
    private Long getEndDay(Instant parsedTime) {
        Instant instant = Instant.parse(getDayString("23:59:59.00z", parsedTime));
        return instant.atZone(ZoneOffset.UTC).toEpochSecond();
    }
    private String getDayString(String timeString, Instant parsedTime) {
        ZonedDateTime zonedDateTime = parsedTime.atZone(ZoneOffset.UTC);
        return zonedDateTime.getYear() + "-" +
                String.format("%02d", zonedDateTime.getMonthValue()) + "-" +
                String.format("%02d", zonedDateTime.getDayOfMonth()) +
                "T" + timeString;
    }
}
