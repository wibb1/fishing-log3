package com.fishingLog.spring.service;

import com.fishingLog.FishingLogApplication;
import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.repository.TideRepository;
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

import java.time.Instant;
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
public class TideServiceTest {
    @Mock
    public TideRepository repository;

    @InjectMocks
    TideService service;

    private Tide tide;

    @BeforeEach
    public void setup() {
        tide = new Tide(1.2, Instant.now() , "Gamgee");
    }

    @AfterEach
    public void teardown() {
        repository.deleteAll();
        tide = null;
    }

    @DisplayName("JUnit test for saveTide method")
    @Test
    public void testTideIsReturnedAfterSaving() {
        given(repository.save(tide)).willReturn(tide);

        Tide savedTide = service.saveTide(tide);

        assertThat(savedTide).isNotNull();
    }

    @DisplayName("JUnit test for saveTide method when duplicate it returns existing Tide")
    @Test
    public void testTideIsDuplicateDoesNotSaveDuplicate() {
        given(repository.findOne(Example.of(tide)))
                .willReturn(Optional.of(tide));

        Tide newTide = service.saveTide(tide);

        assertEquals(tide, newTide);

        verify(repository, never()).save(any(Tide.class));
    }
}
