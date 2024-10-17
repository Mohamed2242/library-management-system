package com.mo.librarymanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.mo.librarymanagement.application.service.PatronService;
import com.mo.librarymanagement.domain.entity.Patron;
import com.mo.librarymanagement.domain.repository.PatronRepository;

@SpringBootTest
public class PatronServiceTest {

    @InjectMocks
    private PatronService patronService;

    @Mock
    private PatronRepository patronRepository;

    private Patron patron;

    @BeforeEach
    public void setUp() {
        patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");
        patron.setEmail("john@example.com");
    }

    @Test
    public void testAddPatron() {
        when(patronRepository.save(any(Patron.class))).thenReturn(patron);
        Patron savedPatron = patronService.addPatron(patron);
        assertThat(savedPatron.getName()).isEqualTo("John Doe");
    }

    @Test
    public void testGetPatronById() {
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        Patron foundPatron = patronService.getPatronById(1L);
        assertThat(foundPatron.getId()).isEqualTo(1L);
    }

    @Test
    public void testUpdatePatron() {
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        patron.setName("Updated Patron");
        when(patronRepository.save(any(Patron.class))).thenReturn(patron);
        Patron updatedPatron = patronService.updatePatron(1L, patron);
        assertThat(updatedPatron.getName()).isEqualTo("Updated Patron");
    }

    @Test
    public void testDeletePatron() {
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        doNothing().when(patronRepository).delete(patron);
        patronService.deletePatron(1L);
        verify(patronRepository, times(1)).delete(patron);
    }
}