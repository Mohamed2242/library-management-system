package com.mo.librarymanagement.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mo.librarymanagement.entity.Book;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSaveAndFindById() {
        Book book = new Book();
        book.setTitle("Spring Boot Book");
        book.setAuthor("John Smith");
        book.setPublicationYear(2020);
        book.setIsbn("123-4567890123");

        Book savedBook = bookRepository.save(book);

        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        Assertions.assertTrue(foundBook.isPresent());
        Assertions.assertEquals("Spring Boot Book", foundBook.get().getTitle());
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book();
        book.setTitle("Old Title");
        book.setAuthor("Old Author");
        book.setPublicationYear(2020);
        book.setIsbn("123-4567890123");

        Book savedBook = bookRepository.save(book);

        savedBook.setTitle("New Title");
        savedBook.setAuthor("New Author");

        Book updatedBook = bookRepository.save(savedBook);

        Optional<Book> foundBook = bookRepository.findById(updatedBook.getId());

        Assertions.assertTrue(foundBook.isPresent());
        Assertions.assertEquals("New Title", foundBook.get().getTitle());
        Assertions.assertEquals("New Author", foundBook.get().getAuthor());
    }

    @Test
    public void testDeleteBook() {
        Book book = new Book();
        book.setTitle("Delete Book");
        book.setAuthor("Author");
        book.setPublicationYear(2020);
        book.setIsbn("123-4567890123");

        Book savedBook = bookRepository.save(book);

        bookRepository.delete(savedBook);

        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        Assertions.assertFalse(foundBook.isPresent());
    }
}
