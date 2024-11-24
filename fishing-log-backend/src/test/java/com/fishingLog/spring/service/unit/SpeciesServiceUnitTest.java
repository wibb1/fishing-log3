package com.fishingLog.spring.service.unit;


import com.fishingLog.FishingLogApplication;
import com.fishingLog.spring.model.Species;
import com.fishingLog.spring.repository.SpeciesRepository;
import com.fishingLog.spring.service.SpeciesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = FishingLogApplication.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("UnitTest")
@Tag("unit")
public class SpeciesServiceUnitTest {

    private SpeciesRepository speciesRepository;
    private SpeciesService speciesService;

    @BeforeEach
    void setUp() {
        speciesRepository = mock(SpeciesRepository.class);
        speciesService = new SpeciesService(speciesRepository);
    }

    @Test
    void findAllSpecies_ShouldReturnAllSpecies() {
        List<Species> speciesList = Arrays.asList(
                new Species(1L, "Salmon", "Salmo salar", 10, 100, Instant.now(), Instant.now()),
                new Species(2L, "Trout", "Oncorhynchus mykiss", 10, 100, Instant.now(), Instant.now())
        );
        when(speciesRepository.findAll()).thenReturn(speciesList);
        List<Species> result = speciesService.findAllSpecies();
        assertEquals(2, result.size());
        assertEquals("Salmon", result.get(0).getCommonName());
        verify(speciesRepository, times(1)).findAll();
    }

    @Test
    void findSpeciesById_WhenSpeciesExists_ShouldReturnSpecies() {
        Species species = new Species(1L, "Salmon", "Salmo salar", 10, 100, Instant.now(), Instant.now());
        when(speciesRepository.findById(1L)).thenReturn(Optional.of(species));
        Optional<Species> result = speciesService.findSpeciesById(1L);
        assertTrue(result.isPresent());
        assertEquals("Salmo salar", result.get().getScientificName());
        verify(speciesRepository, times(1)).findById(1L);
    }

    @Test
    void findSpeciesById_WhenSpeciesDoesNotExist_ShouldReturnEmpty() {
        when(speciesRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Species> result = speciesService.findSpeciesById(1L);
        assertFalse(result.isPresent());
        verify(speciesRepository, times(1)).findById(1L);
    }

    @Test
    void saveSpecies_WhenNewSpecies_ShouldSaveSpecies() {
        Species species = new Species(1L, "Salmon", "Salmo salar", 10, 100, Instant.now(), Instant.now());
        when(speciesRepository.findOne(any())).thenReturn(Optional.empty());
        when(speciesRepository.save(species)).thenReturn(new Species(1L, "Salmon", "Salmo salar", 10, 100, Instant.now(), Instant.now()));
        Species result = speciesService.saveSpecies(species);
        assertNotNull(result.getId());
        assertEquals("Salmo salar", result.getScientificName());
        verify(speciesRepository, times(1)).save(species);
    }

    @Test
    void saveSpecies_WhenSpeciesExists_ShouldReturnExistingSpecies() {
        Species species = new Species(1L, "Salmon", "Salmo salar", 10, 100, Instant.now(), Instant.now());
        when(speciesRepository.findOne(any())).thenReturn(Optional.of(species));
        Species result = speciesService.saveSpecies(species);
        assertEquals("Salmon", result.getCommonName());
        verify(speciesRepository, never()).save(any());
    }

    @Test
    void saveSpeciesList_ShouldSaveNonExistingSpecies() {
        Species species1 = new Species(1L, "Salmon", "Salmo salar", 10, 100, Instant.now(), Instant.now());
        Species species2 = new Species(2L, "Trout", "Oncorhynchus mykiss", 10, 100, Instant.now(), Instant.now());
        when(speciesRepository.findByScientificName("Salmo salar")).thenReturn(Optional.empty());
        when(speciesRepository.findByScientificName("Oncorhynchus mykiss")).thenReturn(Optional.empty());
        when(speciesRepository.saveAll(anyList())).thenReturn(Arrays.asList(
                new Species(1L, "Salmon", "Salmo salar", 10, 100, Instant.now(), Instant.now()),
                new Species(2L, "Trout", "Oncorhynchus mykiss", 10, 100, Instant.now(), Instant.now())
        ));
        List<Species> result = speciesService.saveSpecies(Arrays.asList(species1, species2));
        assertEquals(2, result.size());
        verify(speciesRepository, times(1)).saveAll(anyList());
    }

    @Test
    void deleteSpecies_WhenSpeciesExists_ShouldDeleteSpecies() {
        when(speciesRepository.existsById(1L)).thenReturn(true);
        speciesService.deleteSpecies(1L);
        verify(speciesRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteSpecies_WhenSpeciesDoesNotExist_ShouldThrowException() {
        when(speciesRepository.existsById(1L)).thenReturn(false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            speciesService.deleteSpecies(1L);
        });
        assertEquals("Species not found with ID: 1", exception.getMessage());
        verify(speciesRepository, never()).deleteById(1L);
    }
}
