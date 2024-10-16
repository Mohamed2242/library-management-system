package com.mo.librarymanagement.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.mo.librarymanagement.entity.Patron;

@SpringBootTest
public class PatronRepositoryTest {

    @Autowired
    private PatronRepository patronRepository;

    @Test
    public void testSaveAndFindById() {
        Patron patron = new Patron();
        patron.setName("John Doe");
        patron.setEmail("johndoe@example.com");

        Patron savedPatron = patronRepository.save(patron);

        Optional<Patron> foundPatron = patronRepository.findById(savedPatron.getId());

        Assertions.assertTrue(foundPatron.isPresent());
        Assertions.assertEquals("John Doe", foundPatron.get().getName());
        Assertions.assertEquals("johndoe@example.com", foundPatron.get().getEmail());
    }

    @Test
    public void testUpdatePatron() {
        Patron patron = new Patron();
        patron.setName("Jane Doe");
        patron.setEmail("janedoe@example.com");

        Patron savedPatron = patronRepository.save(patron);

        savedPatron.setName("Jane Smith");
        savedPatron.setEmail("janesmith@example.com");

        Patron updatedPatron = patronRepository.save(savedPatron);

        Optional<Patron> foundPatron = patronRepository.findById(updatedPatron.getId());

        Assertions.assertTrue(foundPatron.isPresent());
        Assertions.assertEquals("Jane Smith", foundPatron.get().getName());
        Assertions.assertEquals("janesmith@example.com", foundPatron.get().getEmail());
    }

    @Test
    public void testDeletePatron() {
        Patron patron = new Patron();
        patron.setName("Delete Me");
        patron.setEmail("deleteme@example.com");

        Patron savedPatron = patronRepository.save(patron);

        patronRepository.delete(savedPatron);

        Optional<Patron> foundPatron = patronRepository.findById(savedPatron.getId());

        Assertions.assertFalse(foundPatron.isPresent());
    }
}
