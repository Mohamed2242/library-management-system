package com.mo.librarymanagement.adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mo.librarymanagement.application.service.BorrowingService;
import com.mo.librarymanagement.domain.entity.BorrowingRecord;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
@Validated
public class BorrowingController {

    private final BorrowingService borrowingService;

    @Autowired
    public BorrowingController(BorrowingService borrowingService) { this.borrowingService = borrowingService; }
    
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public BorrowingRecord borrowBook(@PathVariable @NotNull Long bookId, @PathVariable @NotNull Long patronId) {
        return borrowingService.borrowBook(bookId, patronId);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public BorrowingRecord returnBook(@PathVariable @NotNull Long bookId, @PathVariable @NotNull Long patronId) {
        return borrowingService.returnBook(bookId, patronId);
    }
}