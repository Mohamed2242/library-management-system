package com.mo.librarymanagement.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mo.librarymanagement.entity.Book;
import com.mo.librarymanagement.entity.BorrowingRecord;
import com.mo.librarymanagement.entity.Patron;

@SpringBootTest
public class BorrowingRecordRepositoryTest {

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Test
    public void testFindByBookAndReturnDateIsNull() {
        // Setup test data
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublicationYear(2020);
        book.setIsbn("123-4567890123");
        bookRepository.save(book);

        Patron patron = new Patron();
        patron.setName("Test Patron");
        patron.setEmail("testpatron@example.com");
        patronRepository.save(patron);

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRepository.save(borrowingRecord);

        // Test method findByBookAndReturnDateIsNull
        Optional<BorrowingRecord> result = borrowingRepository.findByBookAndReturnDateIsNull(book);
        
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(book.getId(), result.get().getBook().getId());
    }

    @Test
    public void testFindByBookAndPatronAndReturnDateIsNull() {
        // Setup test data
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublicationYear(2020);
        book.setIsbn("123-4567890123");
        bookRepository.save(book);

        Patron patron = new Patron();
        patron.setName("Test Patron");
        patron.setEmail("testpatron@example.com");
        patronRepository.save(patron);

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
    }
}

