package com.mo.librarymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mo.librarymanagement.entity.Patron;
import com.mo.librarymanagement.service.PatronService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
@Validated
public class PatronController {

    private final PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) { this.patronService = patronService; }

    @GetMapping
    public List<Patron> getAllPatrons() {
        return patronService.getAllPatrons();
    }

    @GetMapping("/{id}")
    public Patron getPatronById(@PathVariable @NotNull Long id) {
        return patronService.getPatronById(id);
    }

    @PostMapping
    public Patron addPatron(@Valid @RequestBody Patron patron) {
        return patronService.addPatron(patron);
    }

    @PutMapping("/{id}")
    public Patron updatePatron(@PathVariable @NotNull Long id, @Valid @RequestBody Patron patron) {
        return patronService.updatePatron(id, patron);
    }

    @DeleteMapping("/{id}")
    public void deletePatron(@PathVariable @NotNull Long id) {
        patronService.deletePatron(id);
    }
}