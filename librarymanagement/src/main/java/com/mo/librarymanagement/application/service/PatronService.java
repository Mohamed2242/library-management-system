package com.mo.librarymanagement.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mo.librarymanagement.adapter.exception.ResourceNotFoundException;
import com.mo.librarymanagement.domain.entity.Patron;
import com.mo.librarymanagement.domain.repository.PatronRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PatronService {

    private final PatronRepository patronRepository;

    @Autowired
    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    @Cacheable("patrons")
    @Transactional(readOnly = true)
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Cacheable(value = "patron", key = "#id")
    @Transactional(readOnly = true)
    public Patron getPatronById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id));
    }

    @Transactional
    @CacheEvict(value = {"patrons", "patron"}, allEntries = true)
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Transactional
    @CacheEvict(value = {"patrons", "patron"}, allEntries = true)
    public Patron updatePatron(Long id, Patron patronDetails) {
        Patron existingPatron = getPatronById(id);
        existingPatron.setName(patronDetails.getName());
        existingPatron.setEmail(patronDetails.getEmail());
        return patronRepository.save(existingPatron);
    }

    @Transactional
    @CacheEvict(value = {"patrons", "patron"}, allEntries = true)
    public void deletePatron(Long id) {
        Patron patron = getPatronById(id);
        patronRepository.delete(patron);
    }
}