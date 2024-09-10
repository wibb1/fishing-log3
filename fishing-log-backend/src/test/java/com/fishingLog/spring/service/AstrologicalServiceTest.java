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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = FishingLogApplication.class)
@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class AstrologicalServiceTest {
    @Mock
    public AstrologicalRepository repository;

    @InjectMocks
    AstrologicalService service;

    ResponseDataForTest responseDataForTest = new ResponseDataForTest();

    private Astrological astrological;

    @BeforeEach
    public void setup() throws IOException {
        JsonNode actualObj = responseDataForTest.getData();
        JsonNode dataJson = actualObj.get(2).get("data").get(0);
        JsonNode metaJson = actualObj.get(1).get("meta").get("station");

        astrological = new Astrological(dataJson, metaJson);
    }

    @AfterEach
    public void teardown() {
        repository.deleteAll();
        astrological = null;
    }

    @DisplayName("JUnit test for saveAstrological method")
    @Test
    public void testAstrologicalIsReturnedAfterSaving() {
        given(repository.save(astrological)).willReturn(astrological);

        Astrological savedAstrological = service.saveAstrological(astrological);

        assertThat(savedAstrological).isNotNull();
    }

    @DisplayName("JUnit test for saveAstrological method when duplicate it returns existing Astrological")
    @Test
    public void testAstrologicalIsDuplicateDoesNotSaveDuplicate() {
        given(repository.findOne(Example.of(astrological)))
                .willReturn(Optional.of(astrological));

        Astrological newAstrological = service.saveAstrological(astrological);

        assertEquals(astrological, newAstrological);

        verify(repository, never()).save(any(Astrological.class));
    }
}
