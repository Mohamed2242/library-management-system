package com.mo.librarymanagement.domain.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mo.librarymanagement.domain.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    // Existing methods
    User findByUsername(String username);

    // Add this custom insert method
    @Modifying
    @Transactional  // Ensure the method is transactional
    @Query(value = "INSERT INTO users (username, password) VALUES (:username, :password)", nativeQuery = true)
    void insertUser(@Param("username") String username, @Param("password") String password);
}