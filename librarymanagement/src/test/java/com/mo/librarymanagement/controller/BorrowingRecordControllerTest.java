package com.mo.librarymanagement.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import com.mo.librarymanagement.entity.Book;
import com.mo.librarymanagement.entity.BorrowingRecord;
import com.mo.librarymanagement.entity.Patron;
import com.mo.librarymanagement.service.BorrowingService;

@SpringBootTest
@AutoConfigureMockMvc
public class BorrowingRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // Change to @MockBean
    private BorrowingService borrowingService; // No need for @InjectMocks

    private BorrowingRecord borrowingRecord;

    @BeforeEach
    public void setUp() {
        // Initialize the borrowingRecord
        Book book = new Book();
        book.setId(1L);
        
        Patron patron = new Patron();
        patron.setId(1L);

        borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());
    }

    @Test
    public void testBorrowBook() throws Exception {
        when(borrowingService.borrowBook(anyLong(), anyLong())).thenReturn(borrowingRecord);

        mockMvc.perform(post("/api/borrow/1/patron/1")
        		.with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(borrowingRecord.getId()));
    }

    @Test
    public void testReturnBook() throws Exception {
        when(borrowingService.returnBook(anyLong(), anyLong())).thenReturn(borrowingRecord);

        mockMvc.perform(put("/api/return/1/patron/1")
        		.with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.returnDate").value(borrowingRecord.getReturnDate()));
    }
}
