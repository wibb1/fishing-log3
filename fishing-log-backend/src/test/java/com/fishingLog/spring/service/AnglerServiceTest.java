package com.fishingLog.spring.service;

import com.fishingLog.FishingLogApplication;
import com.fishingLog.spring.model.Angler;
import com.fishingLog.spring.repository.AnglerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = FishingLogApplication.class)
@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class AnglerServiceTest {
    @Mock
    private AnglerRepository repository;

    @InjectMocks
    AnglerService service;

    private Angler angler;

    @BeforeEach
    public void setup() {
        angler = new Angler(1L, "Samwise" , "Gamgee","SamwiseGamgee",
                "SamWizeGamGee@noplace.com", "password", "", Instant.now(), Instant.now());
    }

    @AfterEach
    public void teardown() {
        repository.deleteAll();
        angler = null;
    }

    @DisplayName("JUnit test for saveAngler method")
    @Test
    public void testAnglerIsReturnedAfterSaving() {
        given(repository.save(angler)).willReturn(angler);

        Angler savedAngler = service.saveAngler(angler);

        assertThat(savedAngler).isNotNull();
    }

    @DisplayName("JUnit test for saveAngler method which throws exception")
    @Test
    public void testAnglerIsDuplicateThrowsExceptionOnSave() {
        given(repository.findByEmail(angler.getEmail()))
                .willReturn(Optional.of(angler));

        org.junit.jupiter.api.Assertions.assertThrows(DataIntegrityViolationException.class, () -> service.saveAngler(angler));

        verify(repository, never()).save(any(Angler.class));
    }
}
