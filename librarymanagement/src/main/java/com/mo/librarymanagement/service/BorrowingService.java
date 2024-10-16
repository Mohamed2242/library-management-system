package com.mo.librarymanagement.service;

import java.awt.dnd.InvalidDnDOperationException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mo.librarymanagement.entity.Book;
import com.mo.librarymanagement.entity.BorrowingRecord;
import com.mo.librarymanagement.entity.Patron;
import com.mo.librarymanagement.repository.BorrowingRepository;

import jakarta.transaction.Transactional;

@Service
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final BookService bookService;
    private final PatronService patronService;

    @Autowired
    public BorrowingService(BorrowingRepository borrowingRepository, BookService bookService, PatronService patronService) {
        this.borrowingRepository = borrowingRepository;
        this.bookService = bookService;
        this.patronService = patronService;
    }

    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        Book book = bookService.getBookById(bookId);
        Patron patron = patronService.getPatronById(patronId);
        
        // Check if the book is already borrowed
        Optional<BorrowingRecord> existingRecord = borrowingRepository.findByBookAndReturnDateIsNull(book);
        if (existingRecord.isPresent()) {
            throw new InvalidDnDOperationException("The book is already borrowed.");
        }

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());

        return borrowingRepository.save(borrowingRecord);
    }

    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        Book book = bookService.getBookById(bookId);
        Patron patron = patronService.getPatronById(patronId);

        BorrowingRecord borrowingRecord = borrowingRepository
            .findByBookAndPatronAndReturnDateIsNull(book, patron)
            .orElseThrow(() -> new InvalidDnDOperationException("No active borrowing record found for this book and patron."));

        borrowingRecord.setReturnDate(LocalDate.now());
        return borrowingRepository.save(borrowingRecord);
    }    
}