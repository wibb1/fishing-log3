package com.fishingLog.spring.service;

import com.fishingLog.FishingLogApplication;
import com.fishingLog.spring.model.Angler;
import com.fishingLog.spring.repository.AnglerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = FishingLogApplication.class)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class AnglerServiceIntegrationIntegrationTest extends BaseIntegrationIntegrationTest {

    @Autowired
    private AnglerRepository repository;

    @Autowired
    private AnglerService service;

    private Angler angler;

    @BeforeEach
    public void start() {
        angler = new Angler(1L, "Samwise", "Gamgee", "SamwiseGamgee",
                "SamWizeGamGee@noplace.com", "USER","password", "", Instant.now(), Instant.now());
    }

    @AfterEach
    public void stop() {
        repository.deleteAll();
        angler = null;
    }

    @DisplayName("Angler Table should exist")
    @Test
    void testTableExists() throws Exception {
        try (Connection connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword())) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT to_regclass('public.angler')");
            if (rs.next()) {
                assertNotNull(rs.getString(1), "Angler table should exist.");
            }
        }
    }

    @DisplayName("JUnit test for saveAngler method")
    @Test
    public void testAnglerIsReturnedAfterSaving() {
        Angler savedAngler = service.saveAngler(angler);

        assertThat(savedAngler).isNotNull();
        assertThat(savedAngler.getEmail()).isEqualTo(angler.getEmail());
    }

    @DisplayName("JUnit test for saveAngler method which throws exception")
    @Test
    public void testAnglerIsDuplicateThrowsExceptionOnSave() {
        // Save the angler first
        service.saveAngler(angler);

        // Attempt to save the same angler again
        assertThrows(DataIntegrityViolationException.class, () -> service.saveAngler(angler));
    }

    @DisplayName("JUnit test for updateAngler method")
    @Test
    public void testAnglerIsUpdatedSuccessfully() {
        Angler savedAngler = service.saveAngler(angler);
        savedAngler.setLastName("GamgeeUpdated");
        Angler updatedAngler = service.updateAngler(savedAngler);

        assertThat(updatedAngler).isNotNull();
        assertThat(updatedAngler.getLastName()).isEqualTo("GamgeeUpdated");
    }

    @DisplayName("JUnit test for deleteAngler method")
    @Test
    public void testAnglerIsDeletedSuccessfully() {
        Angler savedAngler = service.saveAngler(angler);
        service.deleteAngler(savedAngler.getId());

        assertThat(repository.findById(savedAngler.getId())).isEmpty();
    }

    @DisplayName("JUnit test for findAnglerById method")
    @Test
    public void testFindAngler() {
        Angler savedAngler = service.saveAngler(angler);
        Optional<Angler> foundAngler = service.findAnglerById(savedAngler.getId());

        assertTrue(foundAngler.isPresent());
        assertThat(foundAngler.get().getId()).isEqualTo(savedAngler.getId());
    }

    @DisplayName("JUnit test for findAllAnglers method")
    @Test
    public void testFindAllAnglers() {
        service.saveAngler(angler);
        Angler anotherAngler = new Angler(2L, "Frodo", "Baggins", "FrodoBaggins",
                "FrodoBaggins@noplace.com", "USER", "password", "", Instant.now(), Instant.now());
        service.saveAngler(anotherAngler);

        Iterable<Angler> anglers = service.findAllAnglers();

        assertThat(anglers).hasSize(2);
    }

}
