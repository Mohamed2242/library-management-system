package com.mo.librarymanagement.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import com.mo.librarymanagement.application.service.PatronService;
import com.mo.librarymanagement.domain.entity.Patron;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;  // Mock the PatronService

    private Patron mockPatron;

    @BeforeEach
    public void setup() {
    	// Initialize mockPatron before each test
        mockPatron = new Patron();
        mockPatron.setId(1L);
        mockPatron.setName("John Doe");
        mockPatron.setEmail("john@example.com");

        // Mocking the service methods
        when(patronService.getPatronById(1L)).thenReturn(mockPatron);
        when(patronService.updatePatron(eq(1L), any(Patron.class))).thenReturn(mockPatron);
        doNothing().when(patronService).deletePatron(1L);
    }

    @Test
    public void testGetAllPatrons() throws Exception {
        mockMvc.perform(get("/api/patrons")
        		.with(SecurityMockMvcRequestPostProcessors.jwt()))  // Mock JWT authentication
        		.andExpect(status().isOk());
    }

    @Test
    public void testGetPatronById() throws Exception {
        mockMvc.perform(get("/api/patrons/1")
        		.with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockPatron.getId()))
                .andExpect(jsonPath("$.name").value(mockPatron.getName()))
                .andExpect(jsonPath("$.email").value(mockPatron.getEmail()));
    }
    
    @Test
    public void testAddPatron() throws Exception {
        String patronJson = "{\"name\":\"John Doe\", \"email\":\"john@example.com\"}";

        mockMvc.perform(post("/api/patrons")
        		.with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(patronJson))
                .andExpect(status().isOk());
    }
    
    @Test
    public void testUpdatePatron() throws Exception {
        String patronJson = "{\"name\":\"Updated Patron\", \"email\":\"john@example.com\"}";

        mockMvc.perform(put("/api/patrons/1")
        		.with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(patronJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePatron() throws Exception {
        mockMvc.perform(delete("/api/patrons/1")
        		.with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk());
    }
}
