package com.mo.librarymanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mo.librarymanagement.entity.Book;
import com.mo.librarymanagement.entity.BorrowingRecord;
import com.mo.librarymanagement.entity.Patron;

@Repository
public interface BorrowingRepository extends JpaRepository<BorrowingRecord, Long> {
    Optional<BorrowingRecord> findByBookAndReturnDateIsNull(Book book);
    Optional<BorrowingRecord> findByBookAndPatronAndReturnDateIsNull(Book book, Patron patron);
}
