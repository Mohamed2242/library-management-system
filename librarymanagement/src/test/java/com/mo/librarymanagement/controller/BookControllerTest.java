package com.mo.librarymanagement.controller;

import com.mo.librarymanagement.entity.Book;
import com.mo.librarymanagement.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;  // Mock the BookService

    private Book mockBook;

    @BeforeEach
    public void setup() throws Exception {
        // Initialize mock Book
        mockBook = new Book();
        mockBook.setId(1L);
        mockBook.setTitle("Test Book");
        mockBook.setAuthor("Test Author");
        mockBook.setPublicationYear(2022);
        mockBook.setIsbn("123-456-789");

        // Mocking the service methods
        when(bookService.getBookById(1L)).thenReturn(mockBook);
        when(bookService.addBook(any(Book.class))).thenReturn(mockBook);
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(mockBook);
        doNothing().when(bookService).deleteBook(1L);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books")
                .with(SecurityMockMvcRequestPostProcessors.jwt()))  // Mock JWT authentication
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBookById() throws Exception {
        mockMvc.perform(get("/api/books/1")
                .with(SecurityMockMvcRequestPostProcessors.jwt()))  // Mock JWT authentication
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockBook.getId()))
                .andExpect(jsonPath("$.title").value(mockBook.getTitle()))
                .andExpect(jsonPath("$.author").value(mockBook.getAuthor()))
                .andExpect(jsonPath("$.publicationYear").value(mockBook.getPublicationYear()))
                .andExpect(jsonPath("$.isbn").value(mockBook.getIsbn()));
    }

    @Test
    public void testAddBook() throws Exception {
        String bookJson = "{\"title\":\"Test Book\", \"author\":\"Test Author\", \"publicationYear\":2022, \"isbn\":\"123-456-789\"}";

        mockMvc.perform(post("/api/books")
                .with(SecurityMockMvcRequestPostProcessors.jwt())  // Mock JWT authentication
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateBook() throws Exception {
        String bookJson = "{\"title\":\"Updated Book\", \"author\":\"Test Author\", \"publicationYear\":2022, \"isbn\":\"123-456-789\"}";

        mockMvc.perform(put("/api/books/1")
                .with(SecurityMockMvcRequestPostProcessors.jwt())  // Mock JWT authentication
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/1")
                .with(SecurityMockMvcRequestPostProcessors.jwt()))  // Mock JWT authentication
                .andExpect(status().isOk());
    }
}
