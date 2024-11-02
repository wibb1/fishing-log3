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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
}
