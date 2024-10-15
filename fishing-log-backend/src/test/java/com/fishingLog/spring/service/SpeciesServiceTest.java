package com.fishingLog.spring.service;

import com.fishingLog.FishingLogApplication;
import com.fishingLog.spring.model.Species;
import com.fishingLog.spring.repository.SpeciesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = FishingLogApplication.class)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class SpeciesServiceTest extends BaseIntegrationTest {
    @Autowired
    public SpeciesRepository repository;

    @Autowired
    SpeciesService service;

    private Species species;

    @BeforeEach
    public void start() {
        Instant fixedInstant = Instant.parse("2024-10-15T00:33:20.060972Z");
        species = new Species(1L, "Samwise", "Gamgee", 10,
                20, fixedInstant, fixedInstant);
    }

    @AfterEach
    public void stop() {
        repository.deleteAll();
        species = null;
    }

    @DisplayName("JUnit test for saveSpecies method")
    @Test
    public void testSpeciesIsReturnedAfterSaving() {
        Species savedSpecies = service.saveSpecies(species);
        assertThat(savedSpecies).isNotNull();

        Optional<Species> retrieved = service.findSpeciesById(savedSpecies.getId());
        assertTrue(retrieved.isPresent());
        assertEquals(savedSpecies, retrieved.get());
    }

    @DisplayName("JUnit test for saveSpecies method when duplicate it returns existing Species")
    @Test
    public void testSpeciesIsDuplicateDoesNotSaveDuplicate() {
        Species savedSpecies = service.saveSpecies(species);
        assertEquals(species, savedSpecies);

        Optional<Species> retrieved = service.findSpeciesById(savedSpecies.getId());
        assertTrue(retrieved.isPresent());
        assertEquals(savedSpecies.getId(), retrieved.get().getId());
        assertEquals(savedSpecies.getCommonName(), retrieved.get().getCommonName());
        assertEquals(savedSpecies.getScientificName(), retrieved.get().getScientificName());
        assertEquals(savedSpecies.getShallowDepth(), retrieved.get().getShallowDepth());
        assertEquals(savedSpecies.getDeepDepth(), retrieved.get().getDeepDepth());

        Instant expectedCreatedAt = savedSpecies.getCreatedAt();
        Instant actualCreatedAt = retrieved.get().getCreatedAt();
        Instant expectedUpdatedAt = savedSpecies.getUpdatedAt();
        Instant actualUpdatedAt = retrieved.get().getUpdatedAt();

        assertTrue(expectedCreatedAt.minusMillis(1).isBefore(actualCreatedAt) &&
                        expectedCreatedAt.plusMillis(1).isAfter(actualCreatedAt),
                "createdAt timestamps do not match within tolerance.");

        assertTrue(expectedUpdatedAt.minusMillis(1).isBefore(actualUpdatedAt) &&
                        expectedUpdatedAt.plusMillis(1).isAfter(actualUpdatedAt),
                "updatedAt timestamps do not match within tolerance.");
    }
}
