package com.fishingLog.spring.service.unit;

import com.fishingLog.spring.model.TideStation;
import com.fishingLog.spring.repository.TideStationRepository;
import com.fishingLog.spring.service.TideStationService;
import com.fishingLog.spring.utils.ResponseDataForTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("UnitTest")
@Tag("unit")
class TideStationServiceUnitTest {

    private TideStationRepository tideStationRepository;
    private TideStationService tideStationService;

    private TideStation tideStation;

    @BeforeEach
    void setUp() {
        tideStationRepository = mock(TideStationRepository.class);
        tideStationService = new TideStationService(tideStationRepository);
        ResponseDataForTest responseData = new ResponseDataForTest();
        Map<String, Object> response = responseData.getTideMapTest();
        tideStation = (TideStation) response.get("station");
        tideStation.setId(1L);
    }

    @Test
    void testFindAllTideStations() {
        List<TideStation> stations = List.of(tideStation);
        when(tideStationRepository.findAll()).thenReturn(stations);
        List<TideStation> result = tideStationService.findAllTideStations();
        assertEquals(stations, result);
        verify(tideStationRepository, times(1)).findAll();
    }

    @Test
    void testFindTideStationById_WhenExists() {
        when(tideStationRepository.findById(1L)).thenReturn(Optional.of(tideStation));
        Optional<TideStation> result = tideStationService.findTideStationById(1L);
        assertTrue(result.isPresent());
        assertEquals(tideStation, result.get());
        verify(tideStationRepository, times(1)).findById(1L);
    }

    @Test
    void testFindTideStationById_WhenNotExists() {
        when(tideStationRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<TideStation> result = tideStationService.findTideStationById(1L);
        assertFalse(result.isPresent());
        verify(tideStationRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveTideStation() {
        when(tideStationRepository.save(tideStation)).thenReturn(tideStation);
        TideStation result = tideStationService.saveTideStation(tideStation);
        assertEquals(tideStation, result);
        verify(tideStationRepository, times(1)).save(tideStation);
    }

    @Test
    void testDeleteTideStation() {
        when(tideStationRepository.existsById(anyLong())).thenReturn(true);
        tideStationService.deleteTideStation(1L);
        verify(tideStationRepository, times(1)).deleteById(1L);
    }
}
