package com.mo.librarymanagement.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
public class BorrowingRecord {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "Book is required.")
    private Book book;

    @ManyToOne
    @NotNull(message = "Patron is required.")
    private Patron patron;

    @NotNull(message = "Borrow date is required.")
    private LocalDate borrowDate;

    private LocalDate returnDate; // Optional, as this will be null until the book is returned

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    // Getter for the Book object
    public Book getBook() {
        return book;
    }

    // Getter for the Patron object
    public Patron getPatron() {
        return patron;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
