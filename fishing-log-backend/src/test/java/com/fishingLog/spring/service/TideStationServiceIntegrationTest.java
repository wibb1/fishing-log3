package com.fishingLog.spring.service;

import com.fishingLog.FishingLogApplication;
import com.fishingLog.spring.model.TideStation;
import com.fishingLog.spring.repository.TideStationRepository;
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
public class TideStationServiceIntegrationTest extends BaseIntegrationIntegrationTest {
    @Autowired
    public TideStationRepository repository;

    @Autowired
    TideStationService service;

    private TideStation tideStation;

    @BeforeEach
    public void start() {
        tideStation = new TideStation(70.2, 70.2, "Gamgee", "SamGamgee");
    }

    @AfterEach
    public void stop() {
        repository.deleteAll();
        tideStation = null;
    }

    @DisplayName("JUnit test for saveTideStation method")
    @Test
    public void testTideStationIsReturnedAfterSaving() {
        TideStation savedTideStation = service.saveTideStation(tideStation);
        assertThat(savedTideStation).isNotNull();

        Optional<TideStation> retrieved = service.findEqualTideStation(savedTideStation);
        assertTrue(retrieved.isPresent());
        assertThat(retrieved.get()).isEqualTo(savedTideStation);
    }

    @DisplayName("JUnit test for saveTideStation method when duplicate it returns existing TideStation")
    @Test
    public void testTideStationIsDuplicateDoesNotSaveDuplicate() {
        TideStation savedTideStation = service.saveTideStation(tideStation);
        assertEquals(tideStation, savedTideStation);

        Optional<TideStation> retrieved = service.findEqualTideStation(savedTideStation);
        assertTrue(retrieved.isPresent());
        assertEquals(savedTideStation, retrieved.get());
    }

    @DisplayName("JUnit test for updateTideStation method")
    @Test
    public void testTideStationIsUpdatedSuccessfully() {
        TideStation savedTideStation = service.saveTideStation(tideStation);
        savedTideStation.setStationName("FrodoBaggins");
        TideStation updatedTideStation = service.saveTideStation(tideStation);

        assertThat(updatedTideStation).isNotNull();
        assertThat(updatedTideStation.getStationName()).isEqualTo("FrodoBaggins");


    }

    @DisplayName("JUnit test for deleteTideStation method")
    @Test
    public void testTideStationIsDeletedSuccessfully() {
        TideStation savedTideStation = service.saveTideStation(tideStation);
        service.deleteTideStation(savedTideStation.getId());

        assertThat(repository.findById(savedTideStation.getId())).isEmpty();
    }

    @DisplayName("JUnit test for findTideStationById method")
    @Test
    public void testFindTideStationById() {
        TideStation savedTideStation = service.saveTideStation(tideStation);
        Optional<TideStation> foundTideStation = service.findTideStationById(savedTideStation.getId());

        assertThat(foundTideStation).isPresent();
        assertThat(foundTideStation.get().getId()).isEqualTo(tideStation.getId());
    }

    @DisplayName("JUnit test for findAllTideStation method")
    @Test
    public void testFindAllTideStation() {
        service.saveTideStation(tideStation);
        TideStation anotherTideStation = new TideStation();
        anotherTideStation.setStationName("Nothing Used");
        anotherTideStation.setStationSource("Never Used");
        anotherTideStation.setStationLat(10.0);
        anotherTideStation.setStationLng(20.0);
        service.saveTideStation(anotherTideStation);

        List<TideStation> astrologicalList = service.findAllTideStation();

        assertThat(astrologicalList.size()).isEqualTo(2);
    }
}
