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

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = FishingLogApplication.class) // No need for classes attribute
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class AnglerServiceTest extends BaseIntegrationTest {

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
        repository.deleteAll(); // Optional if using @Transactional
        angler = null;
    }

    @DisplayName("JUnit test for saveAngler method")
    @Test
    public void testAnglerIsReturnedAfterSaving() {
        Angler savedAngler = service.saveAngler(angler);

        assertThat(savedAngler).isNotNull();
        assertThat(savedAngler.getEmail()).isEqualTo(angler.getEmail()); // Additional assertion for validation
    }

    @DisplayName("JUnit test for saveAngler method which throws exception")
    @Test
    public void testAnglerIsDuplicateThrowsExceptionOnSave() {
        // Save the angler first
        service.saveAngler(angler);

        // Attempt to save the same angler again
        assertThrows(DataIntegrityViolationException.class, () -> service.saveAngler(angler));
    }
}
