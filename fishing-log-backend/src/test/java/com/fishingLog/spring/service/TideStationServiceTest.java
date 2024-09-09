package com.fishingLog.spring.service;

import com.fishingLog.FishingLogApplication;
import com.fishingLog.spring.model.TideStation;
import com.fishingLog.spring.repository.TideStationRepository;
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
public class TideStationServiceTest {
    @Mock
    public TideStationRepository repository;

    @InjectMocks
    TideStationService service;

    private TideStation tideStation;

    @BeforeEach
    public void setup() {
        tideStation = new TideStation(70.2, 70.2, "Gamgee", "SamGamgee");
    }

    @AfterEach
    public void teardown() {
        repository.deleteAll();
        tideStation = null;
    }

    @DisplayName("JUnit test for saveTideStation method")
    @Test
    public void testTideStationIsReturnedAfterSaving() {
        given(repository.save(tideStation)).willReturn(tideStation);

        TideStation savedTideStation = service.saveTideStation(tideStation);

        assertThat(savedTideStation).isNotNull();
    }

    @DisplayName("JUnit test for saveTideStation method when duplicate it returns existing TideStation")
    @Test
    public void testTideStationIsDuplicateDoesNotSaveDuplicate() {
        given(repository.findOne(Example.of(tideStation)))
                .willReturn(Optional.of(tideStation));

        TideStation newTideStation = service.saveTideStation(tideStation);

        assertEquals(tideStation, newTideStation);

        verify(repository, never()).save(any(TideStation.class));
    }
}
