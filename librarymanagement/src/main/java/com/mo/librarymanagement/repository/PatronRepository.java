package com.mo.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mo.librarymanagement.entity.Patron;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {
}