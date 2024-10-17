package com.mo.librarymanagement.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mo.librarymanagement.domain.entity.Book;
import com.mo.librarymanagement.domain.entity.BorrowingRecord;
import com.mo.librarymanagement.domain.entity.Patron;

@Repository
public interface BorrowingRepository extends JpaRepository<BorrowingRecord, Long> {
    Optional<BorrowingRecord> findByBookAndReturnDateIsNull(Book book);
    Optional<BorrowingRecord> findByBookAndPatronAndReturnDateIsNull(Book book, Patron patron);
}
