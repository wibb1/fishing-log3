package com.fishingLog.spring.service;

import com.fishingLog.FishingLogApplication;
import com.fishingLog.spring.model.Species;
import com.fishingLog.spring.repository.SpeciesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
public class SpeciesServiceTest {
    @Mock
    public SpeciesRepository repository;

    @InjectMocks
    SpeciesService service;

    private Species species;

    @BeforeEach
    public void setup() {
        species = new Species(1L, "Samwise" , "Gamgee", 10,
                20,  Instant.now(), Instant.now());
    }

    @AfterEach
    public void teardown() {
        repository.deleteAll();
        species = null;
    }

    @DisplayName("JUnit test for saveSpecies method")
    @Test
    public void testSpeciesIsReturnedAfterSaving() {
        given(repository.save(species)).willReturn(species);

        Species savedSpecies = service.saveSpecies(species);

        assertThat(savedSpecies).isNotNull();
    }

    @DisplayName("JUnit test for saveSpecies method when duplicate it returns existing Species")
    @Test
    public void testSpeciesIsDuplicateDoesNotSaveDuplicate() {
        given(repository.findByScientificName(species.getScientificName()))
                .willReturn(Optional.of(species));

        Species newSpecies = service.saveSpecies(species);

        assertEquals(species, newSpecies);

        verify(repository, never()).save(any(Species.class));
    }

}
