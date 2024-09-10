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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = FishingLogApplication.class)
@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class WeatherServiceTest {
    @Mock
    public WeatherRepository repository;

    @InjectMocks
    WeatherService service;

    ResponseDataForTest responseDataForTest = new ResponseDataForTest();

    private Weather weather;

    @BeforeEach
    public void setup() {
        weather = new Weather(responseDataForTest.getWeatherMapTest());
    }

    @AfterEach
    public void teardown() {
        repository.deleteAll();
        weather = null;
    }

    @DisplayName("JUnit test for saveWeather method")
    @Test
    public void testWeatherIsReturnedAfterSaving() {
        given(repository.save(weather)).willReturn(weather);

        Weather savedWeather = service.saveWeather(weather);

        assertThat(savedWeather).isNotNull();
    }

    @DisplayName("JUnit test for saveWeather method when duplicate it returns existing Weather")
    @Test
    public void testWeatherIsDuplicateDoesNotSaveDuplicate() {
        given(repository.findOne(Example.of(weather)))
                .willReturn(Optional.of(weather));

        Weather newWeather = service.saveWeather(weather);

        assertEquals(weather, newWeather);

        verify(repository, never()).save(any(Weather.class));
    }
}
