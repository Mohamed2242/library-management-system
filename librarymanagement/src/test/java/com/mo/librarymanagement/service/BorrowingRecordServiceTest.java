package com.mo.librarymanagement.service;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mo.librarymanagement.entity.Book;
import com.mo.librarymanagement.entity.BorrowingRecord;
import com.mo.librarymanagement.entity.Patron;
import com.mo.librarymanagement.repository.BorrowingRepository;
import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class BorrowingRecordServiceTest {

    @Autowired
    private BorrowingService borrowingService;

    @MockBean
    private BorrowingRepository borrowingRepository;

    @MockBean
    private BookService bookService;

    @MockBean
    private PatronService patronService;

    @Test
    public void testBorrowBook() {
        Long bookId = 1L;
        Long patronId = 1L;
        
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Test Book");
        
        Patron patron = new Patron();
        patron.setId(patronId);
        patron.setName("Test Patron");

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());

        Mockito.when(bookService.getBookById(bookId)).thenReturn(book);
        Mockito.when(patronService.getPatronById(patronId)).thenReturn(patron);
        Mockito.when(borrowingRepository.findByBookAndReturnDateIsNull(book)).thenReturn(Optional.empty());
        Mockito.when(borrowingRepository.save(Mockito.any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecord result = borrowingService.borrowBook(bookId, patronId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(book, result.getBook());
        Assertions.assertEquals(patron, result.getPatron());
    }

    @Test
    public void testReturnBook() {
        Long bookId = 1L;
        Long patronId = 1L;
        
        Book book = new Book();
        book.setId(bookId);
        Patron patron = new Patron();
        patron.setId(patronId);

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());

        Mockito.when(bookService.getBookById(bookId)).thenReturn(book);
        Mockito.when(patronService.getPatronById(patronId)).thenReturn(patron);
        Mockito.when(borrowingRepository.findByBookAndPatronAndReturnDateIsNull(book, patron))
               .thenReturn(Optional.of(borrowingRecord));
        Mockito.when(borrowingRepository.save(Mockito.any(BorrowingRecord.class)))
               .thenReturn(borrowingRecord);

        BorrowingRecord result = borrowingService.returnBook(bookId, patronId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(book, result.getBook());
        Assertions.assertEquals(patron, result.getPatron());
        Assertions.assertNotNull(result.getReturnDate());
    }
}
