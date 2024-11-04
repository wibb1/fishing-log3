package com.fishingLog.spring.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fishingLog.FishingLogApplication;
import com.fishingLog.spring.model.Astrological;
import com.fishingLog.spring.repository.AstrologicalRepository;
import com.fishingLog.spring.utils.ResponseDataForTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = FishingLogApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class AstrologicalServiceTest extends BaseIntegrationTest {
    @Autowired
    public AstrologicalRepository repository;

    @Autowired
    AstrologicalService service;

    ResponseDataForTest responseDataForTest = new ResponseDataForTest();

    private Astrological astrological;

    @BeforeEach
    public void start() throws IOException {
        JsonNode actualObj = responseDataForTest.getData();
        JsonNode dataJson = actualObj.get(2).get("data").get(0);
        JsonNode metaJson = actualObj.get(1).get("meta").get("station");

        astrological = new Astrological(dataJson, metaJson);
    }

    @AfterEach
    public void stop() {
        repository.deleteAll();
        astrological = null;
    }

    @DisplayName("Astrological Table should exist")
    @Test
    void testTableExists() throws Exception {
        try (Connection connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword())) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT to_regclass('public.astrological')");
            assertTrue(rs.next(), "Should return a value for the astrological table.");
            assertNotNull(rs.getString(1), "Astrological table should exist.");
        }
    }

    @DisplayName("JUnit test for saveAstrological method")
    @Test
    public void testAstrologicalIsReturnedAfterSaving() {
        Astrological savedAstrological = service.saveAstrological(astrological);
        assertThat(savedAstrological).isNotNull();

        Optional<Astrological> retrieved = service.findEqualAstrological(savedAstrological);
        assertTrue(retrieved.isPresent());
        assertEquals(savedAstrological, retrieved.get());
    }

    @DisplayName("JUnit test for saveAstrological method when duplicate it returns existing Astrological")
    @Test
    public void testAstrologicalIsDuplicateDoesNotSaveDuplicate() {
        Astrological newAstrological = service.saveAstrological(astrological);
        assertThat(newAstrological).isEqualTo(astrological);
    }

    @DisplayName("JUnit test for updateAstrological method")
    @Test
    public void testAstrologicalIsUpdatedSuccessfully() {
        Instant testInstant = Instant.now();
        Astrological savedAstrological = service.saveAstrological(astrological);
        savedAstrological.setSunrise(testInstant);
        Astrological updatedAstrological = service.updateAstrological(savedAstrological);

        assertThat(updatedAstrological).isNotNull();
        assertThat(updatedAstrological.getSunrise()).isEqualTo(testInstant);
    }

    @DisplayName("JUnit test for deleteAstrological method")
    @Test
    public void testAstrologicalIsDeletedSuccessfully() {
        Astrological savedAstrological = service.saveAstrological(astrological);
        service.deleteAstrological(savedAstrological.getId());

        assertThat(repository.findById(savedAstrological.getId())).isEmpty();
    }

    @DisplayName("JUnit test for findAstrologicalById method")
    @Test
    public void testFindAstrologicalById() {
        Astrological savedAstrological = service.saveAstrological(astrological);
        Optional<Astrological> foundAstrological = service.findAstrologicalById(savedAstrological.getId());

        assertThat(foundAstrological).isPresent();
        assertThat(foundAstrological.get().getId()).isEqualTo(savedAstrological.getId());
    }

    @DisplayName("JUnit test for findAllAstrological method")
    @Test
    public void testFindAllAstrological() {
        service.saveAstrological(astrological);
        Instant time = Instant.parse("2024-11-01T18:35:24.00Z");
        Astrological anotherAstrological = new Astrological(/* initialize with different data */);
        anotherAstrological.setAstronomicalDawn(time);
        anotherAstrological.setAstronomicalDusk(time);
        anotherAstrological.setCivilDawn(time);
        anotherAstrological.setCivilDusk(time);
        anotherAstrological.setMoonrise(time);
        anotherAstrological.setMoonset(time);
        anotherAstrological.setSunrise(time);
        anotherAstrological.setSunset(time);
        anotherAstrological.setTime(time);
        service.saveAstrological(anotherAstrological);

        List<Astrological> astrologicalList = service.findAllAstrological();

        assertThat(astrologicalList.size()).isEqualTo(2);
    }

}
