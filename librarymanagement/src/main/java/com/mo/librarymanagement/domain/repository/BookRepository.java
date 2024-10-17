package com.mo.librarymanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mo.librarymanagement.domain.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}