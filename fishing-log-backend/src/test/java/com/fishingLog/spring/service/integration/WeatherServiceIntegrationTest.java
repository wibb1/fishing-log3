package com.fishingLog.spring.service.integration;

import com.fishingLog.FishingLogApplication;
import com.fishingLog.spring.model.Weather;
import com.fishingLog.spring.repository.WeatherRepository;
import com.fishingLog.spring.service.WeatherService;
import com.fishingLog.spring.utils.ResponseDataForTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = FishingLogApplication.class)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@Tag("integration")
public class WeatherServiceIntegrationTest extends BaseIntegrationIntegrationTest {
    @Autowired
    public WeatherRepository repository;

    @Autowired
    WeatherService service;

    ResponseDataForTest responseDataForTest = new ResponseDataForTest();

    private Weather weather;

    @BeforeEach
    public void start() {
        weather = new Weather(responseDataForTest.getWeatherMapTest());
    }

    @AfterEach
    public void stop() {
        repository.deleteAll();
        weather = null;
    }

    @DisplayName("Angler Table should exist")
    @Test
    void testTableExists() throws Exception {
        try (Connection connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword())) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT to_regclass('public.weather')");
            if (rs.next()) {
                assertNotNull(rs.getString(1), "Angler table should exist.");
            }
        }
    }

    @DisplayName("JUnit test for saveWeather method")
    @Test
    public void testWeatherIsReturnedAfterSaving() {
        Weather savedWeather = service.saveWeather(weather);
        assertThat(savedWeather).isNotNull();

        Optional<Weather> retrieved = service.findWeatherById(savedWeather.getId());
        assertTrue(retrieved.isPresent());
        assertEquals(savedWeather, retrieved.get());
    }

    @DisplayName("JUnit test for saveWeather method when duplicate it returns existing Weather")
    @Test
    public void testWeatherIsDuplicateDoesNotSaveDuplicate() {
        Weather savedWeather = service.saveWeather(weather);
        assertEquals(weather, savedWeather);

        Optional<Weather> retrieved = service.findWeatherById(savedWeather.getId());
        assertTrue(retrieved.isPresent());
        assertEquals(savedWeather, retrieved.get());
    }

    @DisplayName("JUnit test for updateWeather method")
    @Test
    public void testWeatherIsUpdatedSuccessfully() {
        Weather savedWeather = service.saveWeather(weather);
        savedWeather.setPressure(1.2);
        Weather updatedWeather = service.saveWeather(savedWeather);

        assertThat(updatedWeather).isNotNull();
        assertThat(updatedWeather.getPressure()).isEqualTo(1.2);


    }

    @DisplayName("JUnit test for deleteWeather method")
    @Test
    public void testWeatherIsDeletedSuccessfully() {
        Weather savedWeather = service.saveWeather(weather);
        service.deleteWeather(savedWeather.getId());

        assertThat(repository.findById(savedWeather.getId())).isEmpty();
    }

    @DisplayName("JUnit test for findWeatherById method")
    @Test
    public void testFindWeatherById() {
        Weather savedWeather = service.saveWeather(weather);
        Optional<Weather> foundWeather = service.findWeatherById(savedWeather.getId());

        assertThat(foundWeather).isPresent();
        assertThat(foundWeather.get().getId()).isEqualTo(savedWeather.getId());
    }

    @DisplayName("JUnit test for findAllWeather method")
    @Test
    public void testFindAllWeather() {
        service.saveWeather(weather);
        Weather anotherWeather = new Weather();
        anotherWeather.setPressure(10D);
        anotherWeather.setTime(Instant.now());
        service.saveWeather(anotherWeather);

        List<Weather> astrologicalList = service.findAllWeather();

        assertThat(astrologicalList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for createWeather method")
    @Test
    public void testCreateWeather() {
        ResponseDataForTest dataForTest = new ResponseDataForTest();
        String weatherData;
        try {
            weatherData = dataForTest.getWeatherDataString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Weather createWeather = service.createWeather(weatherData);
        assertThat(createWeather).isEqualTo(weather);
    }
}
