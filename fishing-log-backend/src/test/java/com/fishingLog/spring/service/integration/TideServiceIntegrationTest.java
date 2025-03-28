package com.fishingLog.spring.service.integration;

import com.fishingLog.FishingLogApplication;
import com.fishingLog.spring.model.Record;
import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.repository.RecordRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    public RecordRepository recordRepository;
    @Autowired
    TideService service;

    private Tide tide;

    private Record record;

    @BeforeEach
    public void start() {
        Instant instant = Instant.now();
        tide = new Tide(1.2, instant, "Gamgee");
        tide = repository.save(tide);

        Set<Tide> tides = new HashSet<>();
        tides.add(tide);

        // Use the RecordBuilder to create a Record instance
        record = new Record.RecordBuilder()
                .setName("Expected Record Name")
                .setSuccess("Success")
                .setAnglerId(1L)
                .setCreatedAt(Instant.now())
                .setUpdatedAt(Instant.now())
                .setBody("Test Body")
                .setLatitude(41.6)
                .setLongitude(-70.8)
                .setDatetime(Instant.parse("2024-07-12T21:00:00+00:00"))
                .setTimezone("UTC")
                .setTides(tides)
                .build();

        record = recordRepository.save(record);
        Set<Record> records = new HashSet<>();
        records.add(record);
        tide.setRecords(records);
        tide = repository.save(tide);
    }

    @AfterEach
    public void stop() {
        repository.deleteAll();
        recordRepository.deleteAll();
        tide = null;
        record = null;
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

    @DisplayName("JUnit test for many-to-many relationship between Tide and Record")
    @Test
    @Transactional
    public void testTideAndRecordRelationship() {
        List<Record> records = recordRepository.findAll();
        assertFalse(records.isEmpty());

        Tide recordTide = records.get(0).getTides().iterator().next();
        assertEquals(tide, recordTide);

        List<Tide> tides = repository.findAll();
        assertFalse(tides.isEmpty());
        assertTrue(tides.get(0).getRecords().contains(record));
    }

    @DisplayName("JUnit test for saveTide method when duplicate it returns existing Tide")
    @Test
    @Transactional
    public void testTideIsDuplicateDoesNotSaveDuplicate() {
        Tide savedTide = service.saveTide(tide);
        assertEquals(tide, savedTide);

        Optional<Tide> retrieved = service.findEqualTide(savedTide);
        assertTrue(retrieved.isPresent());
        assertEquals(savedTide.getHeight(), retrieved.get().getHeight());
        assertEquals(savedTide.getRecords(), retrieved.get().getRecords());
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
