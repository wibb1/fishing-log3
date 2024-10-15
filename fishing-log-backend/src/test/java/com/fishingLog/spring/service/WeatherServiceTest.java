package com.fishingLog.spring.service;

import com.fishingLog.FishingLogApplication;
import com.fishingLog.spring.model.Weather;
import com.fishingLog.spring.repository.WeatherRepository;
import com.fishingLog.spring.utils.ResponseDataForTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = FishingLogApplication.class)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class WeatherServiceTest {
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

    @DisplayName("JUnit test for saveWeather method")
    @Test
    public void testWeatherIsReturnedAfterSaving() {
        Weather savedWeather = service.saveWeather(weather);
        assertThat(savedWeather).isNotNull();

        Optional<Weather> retrieved = service.findWeather(savedWeather.getId());
        assertTrue(retrieved.isPresent());
        assertEquals(savedWeather, retrieved.get());
    }

    @DisplayName("JUnit test for saveWeather method when duplicate it returns existing Weather")
    @Test
    public void testWeatherIsDuplicateDoesNotSaveDuplicate() {
        Weather savedWeather = service.saveWeather(weather);
        assertEquals(weather, savedWeather);

        Optional<Weather> retrieved = service.findWeather(savedWeather.getId());
        assertTrue(retrieved.isPresent());
        assertEquals(savedWeather, retrieved.get());
    }
}
