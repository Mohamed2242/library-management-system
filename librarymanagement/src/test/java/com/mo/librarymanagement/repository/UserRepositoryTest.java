package com.mo.librarymanagement.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.mo.librarymanagement.domain.entity.User;
import com.mo.librarymanagement.domain.repository.UserRepository;

import jakarta.transaction.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED) // Ensure embedded DB
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional // Ensure this test runs in a transaction
    @Rollback // Rollback changes after the test to keep the database clean
    public void testFindByUsername_UserExists() {
        // Use the custom insert method instead of save()
        userRepository.insertUser("user", "password");

        User foundUser = userRepository.findByUsername("user");

        assertNotNull(foundUser);
        assertEquals("user", foundUser.getUsername());
    }

    @Test
    @Transactional // Ensure this test runs in a transaction
    @Rollback // Rollback changes after the test to keep the database clean
    public void testFindByUsername_UserDoesNotExist() {
        User foundUser = userRepository.findByUsername("nonexistent");

        assertNull(foundUser);
    }
}
