package com.fishingLog.spring.service.unit;

import com.fishingLog.spring.exception.EmailAlreadyExistsException;
import com.fishingLog.spring.exception.ResourceNotFoundException;
import com.fishingLog.spring.model.Angler;
import com.fishingLog.spring.repository.AnglerRepository;
import com.fishingLog.spring.service.AnglerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("UnitTest")
@Tag("unit")
public class AnglerServiceUnitTest {
    private AnglerRepository anglerRepository;
    private AnglerService anglerService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        anglerRepository = mock(AnglerRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        anglerService = new AnglerService(anglerRepository, passwordEncoder);
    }

    @Test
    void testFindAllAnglers() {
        Angler angler = new Angler(1L, "Samwise", "Gamgee", "SamwiseGamgee",
                "SamWizeGamGee@noplace.com", Collections.singletonList("USER"), "password", Instant.now(), Instant.now());
        List<Angler> anglers = List.of(angler);
        when(anglerRepository.findAll()).thenReturn(anglers);

        List<Angler> result = anglerService.findAllAnglers();

        assertEquals(1, result.size());
        verify(anglerRepository, times(1)).findAll();
    }

    @Test
    void testFindAnglerById_Success() {
        Angler angler = new Angler(1L, "Samwise", "Gamgee", "SamwiseGamgee",
                "SamWizeGamGee@noplace.com", Collections.singletonList("USER"), "password", Instant.now(), Instant.now());

        when(anglerRepository.findById(1L)).thenReturn(Optional.of(angler));

        Optional<Angler> result = anglerService.findAnglerById(1L);

        assertTrue(result.isPresent());
        assertEquals("Samwise", result.get().getFirstName());
        verify(anglerRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAnglerById_NotFound() {
        when(anglerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Angler> result = anglerService.findAnglerById(1L);

        assertFalse(result.isPresent());
        verify(anglerRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveAngler_Success() {
        Angler angler = new Angler(1L, "Samwise", "Gamgee", "SamwiseGamgee",
                "SamWizeGamGee@noplace.com", Collections.singletonList("USER"), "password", Instant.now(), Instant.now());
        String encodedPassword = "encodedPassword";

        when(anglerRepository.findByEmail("SamWizeGamGee@noplace.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn(encodedPassword);
        when(anglerRepository.save(any(Angler.class))).thenAnswer(invocation -> {
            Angler savedAngler = invocation.getArgument(0);
            savedAngler.setPassword(encodedPassword);
            return savedAngler;
        });

        Angler result = anglerService.saveAngler(angler);

        assertNotNull(result);
        assertEquals(encodedPassword, result.getPassword());
        verify(anglerRepository, times(1)).findByEmail("SamWizeGamGee@noplace.com");
        verify(passwordEncoder, times(1)).encode("password");
        verify(anglerRepository, times(1)).save(any(Angler.class));
    }

    @Test
    void testSaveAngler_EmailAlreadyExists() {
        Angler angler = new Angler(1L, "Samwise", "Gamgee", "SamwiseGamgee",
                "SamWizeGamGee@noplace.com", Collections.singletonList("USER"), "password", Instant.now(), Instant.now());

        when(anglerRepository.findByEmail("SamWizeGamGee@noplace.com")).thenReturn(Optional.of(angler));

        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class, () -> anglerService.saveAngler(angler));

        assertEquals("Angler already exists with email: SamWizeGamGee@noplace.com", exception.getMessage());
        verify(anglerRepository, times(1)).findByEmail("SamWizeGamGee@noplace.com");
        verify(anglerRepository, times(0)).save(any());
    }

    @Test
    void testUpdateAngler_Success() {
        Angler existingAngler = new Angler(1L, "Samwise", "Gamgee", "SamwiseGamgee", "SamWizeGamGee@noplace.com", Collections.singletonList("USER"), "password", Instant.now(), Instant.now());
        Angler updatedAngler = new Angler(1L, "Sam", "Gamgee", "SamGamgee", "SamGamGee@noplace.com", Collections.singletonList("USER"), "newpassword", Instant.now(), Instant.now());
        String encodedPassword = "encodedNewPassword";

        when(anglerRepository.findById(1L)).thenReturn(Optional.of(existingAngler));
        when(anglerRepository.findByEmail("SamGamGee@noplace.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("newpassword")).thenReturn(encodedPassword);
        when(anglerRepository.save(any(Angler.class))).thenAnswer(invocation -> {
            Angler savedAngler = invocation.getArgument(0);
            savedAngler.setPassword(encodedPassword);
            return savedAngler;
        });

        Angler result = anglerService.updateAngler(updatedAngler);

        assertEquals("Sam", result.getFirstName());
        assertEquals(encodedPassword, result.getPassword());
        verify(anglerRepository, times(1)).findById(1L);
        verify(anglerRepository, times(1)).findByEmail("SamGamGee@noplace.com");
        verify(passwordEncoder, times(1)).encode("newpassword");
        verify(anglerRepository, times(1)).save(any(Angler.class));
    }

    @Test
    void testUpdateAngler_EmailConflict() {
        Angler existingAngler = new Angler(1L, "Samwise", "Gamgee", "SamwiseGamgee", "SamWizeGamGee@noplace.com", Collections.singletonList("USER"), "password", Instant.now(), Instant.now());
        Angler conflictingAngler = new Angler(2L, "Sammy", "Gamgee", "SammyGamgee", "SammyGamGee@noplace.com", Collections.singletonList("USER"), "password", Instant.now(), Instant.now());
        Angler updatedAngler = new Angler(1L, "Sam", "Gamgee", "SamGamgee", "SamGamGee@noplace.com", Collections.singletonList("USER"), "newpassword", Instant.now(), Instant.now());

        when(anglerRepository.findById(1L)).thenReturn(Optional.of(existingAngler));
        when(anglerRepository.findByEmail("SamGamGee@noplace.com")).thenReturn(Optional.of(conflictingAngler));

        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class, () -> anglerService.updateAngler(updatedAngler));

        assertEquals("Email already in use by another angler: SamGamGee@noplace.com", exception.getMessage());
        verify(anglerRepository, times(1)).findById(1L);
        verify(anglerRepository, times(1)).findByEmail("SamGamGee@noplace.com");
        verify(anglerRepository, times(0)).save(any());
    }

    @Test
    void testDeleteAngler_Success() {
        when(anglerRepository.existsById(1L)).thenReturn(true);

        anglerService.deleteAngler(1L);

        verify(anglerRepository, times(1)).existsById(1L);
        verify(anglerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAngler_NotFound() {
        when(anglerRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> anglerService.deleteAngler(1L));

        assertEquals("Angler not found with id: 1", exception.getMessage());
        verify(anglerRepository, times(1)).existsById(1L);
        verify(anglerRepository, times(0)).deleteById(any());
    }
}
