package com.fishingLog.spring.service;

import com.fishingLog.FishingLogApplication;
import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.repository.TideRepository;
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
public class TideServiceTest extends BaseIntegrationTest {
    @Autowired
    public TideRepository repository;

    @Autowired
    TideService service;

    private Tide tide;

    @BeforeEach
    public void start() {
        Instant instant = Instant.now();
        tide = new Tide(1.2, instant , "Gamgee");
    }

    @AfterEach
    public void stop() {
        repository.deleteAll();
        tide = null;
    }

    @DisplayName("JUnit test for saveTide method")
    @Test
    public void testTideIsReturnedAfterSaving() {
        Tide savedTide = service.saveTide(tide);
        assertThat(savedTide).isNotNull();

        Optional<Tide> retrieved = service.findEqualTide(savedTide);
        assertTrue(retrieved.isPresent());
        assertEquals(savedTide, retrieved.get());
    }

    @DisplayName("JUnit test for saveTide method when duplicate it returns existing Tide")
    @Test
    public void testTideIsDuplicateDoesNotSaveDuplicate() {
        Tide savedTide = service.saveTide(tide);
        assertEquals(tide, savedTide);

        Optional<Tide> retrieved = service.findEqualTide(savedTide);
        assertTrue(retrieved.isPresent());
        assertEquals(savedTide.getHeight(), retrieved.get().getHeight());
        assertEquals(savedTide.getRecord(), retrieved.get().getRecord());
        assertEquals(savedTide.getTideStation(), retrieved.get().getTideStation());
        assertEquals(savedTide.getType(), retrieved.get().getType());

        Instant expectedTime = savedTide.getTime();
        Instant actualTime = retrieved.get().getTime();

        assertTrue(expectedTime.minusMillis(1).isBefore(actualTime) &&
                        expectedTime.plusMillis(1).isAfter(actualTime),
                "createdAt timestamps do not match within tolerance.");
    }
}
