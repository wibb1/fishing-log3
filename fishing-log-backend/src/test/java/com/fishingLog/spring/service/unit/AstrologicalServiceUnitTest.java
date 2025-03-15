package com.fishingLog.spring.service.unit;

import com.fishingLog.spring.exception.DataConversionException;
import com.fishingLog.spring.model.Astrological;
import com.fishingLog.spring.repository.AstrologicalRepository;
import com.fishingLog.spring.service.AstrologicalService;
import com.fishingLog.spring.utils.ResponseDataForTest;
import com.fishingLog.spring.utils.StormGlassAstrologicalConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("UnitTest")
@Tag("unit")
public class AstrologicalServiceUnitTest {
    private AstrologicalService astrologicalService;
    private AstrologicalRepository astrologicalRepository;
    private StormGlassAstrologicalConverter astrologicalConverter;

    @BeforeEach
    void setUp() {
        astrologicalRepository = mock(AstrologicalRepository.class);
        astrologicalConverter = mock(StormGlassAstrologicalConverter.class);
        astrologicalService = new AstrologicalService(astrologicalRepository, astrologicalConverter);

    }

    @Test
    void findAllAstrological_ShouldReturnAllAstrologicalRecords() {
        Astrological astrological = getResponseDataForTest();
        Astrological astrological1 = getResponseDataForTest();
        astrological1.setAstronomicalDawn(astrological1.getAstronomicalDawn().minus(Duration.ofDays(1L)));
        List<Astrological> astrologicalList = Arrays.asList(astrological, astrological1);
        when(astrologicalRepository.findAll()).thenReturn(astrologicalList);
        List<Astrological> result = astrologicalService.findAllAstrological();
        assertEquals(2, result.size());
        verify(astrologicalRepository, times(1)).findAll();
    }

    @Test
    void findAstrologicalById_WhenExists_ShouldReturnAstrological() {
        Astrological astrological = getResponseDataForTest();

        when(astrologicalRepository.findById(1L)).thenReturn(Optional.of(astrological));
        Optional<Astrological> result = astrologicalService.findAstrologicalById(1L);
        assertTrue(result.isPresent());
        assertEquals(astrological, result.get());
        verify(astrologicalRepository, times(1)).findById(1L);
    }

    @Test
    void findAstrologicalById_WhenNotExists_ShouldReturnEmpty() {
        when(astrologicalRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Astrological> result = astrologicalService.findAstrologicalById(1L);
        assertFalse(result.isPresent());
        verify(astrologicalRepository, times(1)).findById(1L);
    }

    @Test
    void saveAstrological_WhenValid_ShouldSaveAstrological() {
        Astrological astrological = getResponseDataForTest();
        when(astrologicalRepository.findOne(any())).thenReturn(Optional.empty());
        when(astrologicalRepository.save(astrological)).thenReturn(astrological);
        Astrological result = astrologicalService.saveAstrological(astrological);
        assertEquals(astrological, result);
        verify(astrologicalRepository, times(1)).save(astrological);
    }

    @Test
    void saveAstrological_WhenDuplicate_ShouldReturnExistingAstrological() {
        Astrological astrological = getResponseDataForTest();
        when(astrologicalRepository.findOne(any())).thenReturn(Optional.of(astrological));
        Astrological result = astrologicalService.saveAstrological(astrological);
        assertEquals(astrological, result);
        verify(astrologicalRepository, never()).save(any());
    }

    @Test
    void deleteAstrological_ShouldDeleteById() {
        Long id = 1L;
        astrologicalService.deleteAstrological(id);
        verify(astrologicalRepository, times(1)).deleteById(id);
    }

    @Test
    void updateAstrological_WhenValid_ShouldUpdateAstrological() {
        Astrological existingAstrological = getResponseDataForTest();
        existingAstrological.setId(1L);
        Astrological updatedAstrological = getResponseDataForTest();
        Instant updatedAstronomicalDawn = updatedAstrological.getAstronomicalDawn().minus(Duration.ofDays(3L));
        updatedAstrological.setAstronomicalDawn(updatedAstronomicalDawn);
        updatedAstrological.setId(1L);
        when(astrologicalRepository.findById(1L)).thenReturn(Optional.of(existingAstrological));
        when(astrologicalRepository.save(existingAstrological)).thenReturn(existingAstrological);
        Astrological result = astrologicalService.updateAstrological(updatedAstrological);
        assertEquals(updatedAstronomicalDawn, result.getAstronomicalDawn());
        verify(astrologicalRepository, times(1)).save(existingAstrological);
    }

    @Test
    void updateAstrological_WhenNotFound_ShouldThrowException() {
        Astrological updatedAstrological = getResponseDataForTest();
        updatedAstrological.setId(1L);
        when(astrologicalRepository.findById(1L)).thenReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            astrologicalService.updateAstrological(updatedAstrological);
        });
        assertEquals("Astrological not found with id: 1", exception.getMessage());
        verify(astrologicalRepository, never()).save(any());
    }

    @Test
    void createAstrological_WhenValid_ShouldConvertAndSave() throws IOException {
        ResponseDataForTest responseDataForTest = new ResponseDataForTest();
        String rawData = responseDataForTest.getAstrologicalDataString();
        Map<String, Object> convertedData = responseDataForTest.getAstroMapTest();
        Astrological astrological = new Astrological(convertedData);
        when(astrologicalConverter.dataConverter(rawData)).thenReturn(convertedData);
        when(astrologicalRepository.save(astrological)).thenReturn(astrological);
        Astrological result = astrologicalService.createAstrological(rawData);
        assertEquals(convertedData.get("astronomicalDawn"), result.getAstronomicalDawn());
        verify(astrologicalConverter, times(1)).dataConverter(rawData);
        verify(astrologicalRepository, times(1)).save(astrological);
    }

    @Test
    void createAstrological_WhenConversionFails_ShouldThrowException() throws IOException {
        String rawData = "rawDataThatErrors";
        when(astrologicalConverter.dataConverter(rawData)).thenThrow(new IOException("Conversion failed"));
        DataConversionException exception = assertThrows(DataConversionException.class, () -> {
            astrologicalService.createAstrological(rawData);
        });

        assertEquals("Failed to convert astrological data", exception.getMessage());
        verify(astrologicalRepository, never()).save(any());
    }

    private Astrological getResponseDataForTest() {
        ResponseDataForTest responseDataForTest = new ResponseDataForTest();
        Map<String, Object> astrologicalData;
        astrologicalData = responseDataForTest.getAstroMapTest();
        return new Astrological(astrologicalData);
    }
}
