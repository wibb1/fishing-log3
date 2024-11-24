package com.fishingLog.spring.service.integration;

import com.fishingLog.FishingLogApplication;
import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.repository.TideRepository;
import com.fishingLog.spring.service.TideService;
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

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = FishingLogApplication.class)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@Tag("integration")
public class TideServiceIntegrationTest extends BaseIntegrationIntegrationTest {
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

    @DisplayName("JUnit test for updateTide method")
    @Test
    public void testTideIsUpdatedSuccessfully() {
        Tide savedTide = service.saveTide(tide);
        savedTide.setHeight(1.2);
        Tide updatedTide = service.saveTide(savedTide);

        assertThat(updatedTide).isNotNull();
        assertThat(updatedTide.getHeight()).isEqualTo(1.2);
    }

    @DisplayName("JUnit test for deleteTide method")
    @Test
    public void testTideIsDeletedSuccessfully() {
        Tide savedTide = service.saveTide(tide);
        service.deleteTide(savedTide.getId());

        assertThat(repository.findById(savedTide.getId())).isEmpty();
    }

    @DisplayName("JUnit test for findTideById method")
    @Test
    public void testFindTideById() {
        Tide savedTide = service.saveTide(tide);
        Optional<Tide> foundTide = service.findTideById(savedTide.getId());

        assertThat(foundTide).isPresent();
        assertThat(foundTide.get().getId()).isEqualTo(savedTide.getId());
    }

    @DisplayName("JUnit test for findAllTide method")
    @Test
    public void testFindAllTide() {
        service.saveTide(tide);
        Tide anotherTide = new Tide();
        anotherTide.setHeight(10D);
        anotherTide.setTime(Instant.now());
        anotherTide.setType("Nothing Used");
        service.saveTide(anotherTide);

        List<Tide> astrologicalList = service.findAllTide();

        assertThat(astrologicalList.size()).isEqualTo(2);
    }
}
