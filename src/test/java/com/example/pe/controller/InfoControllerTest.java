package com.example.pe.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for InfoController.
 * 
 * Tests verify that the /info endpoint returns correct application information
 * including application name and version from properties.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "application.name=test-app",
    "build.version=1.0.0-test"
})
public class InfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test that /info endpoint returns status 200 and JSON content type.
     */
    @Test
    public void shouldReturnOkStatus() throws Exception {
        mockMvc.perform(get("/info"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Test that /info endpoint returns correct application name from properties.
     */
    @Test
    public void shouldReturnApplicationName() throws Exception {
        mockMvc.perform(get("/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.application").value("test-app"));
    }

    /**
     * Test that /info endpoint returns correct build version from properties.
     */
    @Test
    public void shouldReturnBuildVersion() throws Exception {
        mockMvc.perform(get("/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("1.0.0-test"));
    }

    /**
     * Test that /info endpoint returns both application name and version.
     * This test verifies the complete response structure.
     */
    @Test
    public void shouldReturnCompleteInfo() throws Exception {
        mockMvc.perform(get("/info"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.application").value("test-app"))
                .andExpect(jsonPath("$.version").value("1.0.0-test"));
    }

    /**
     * Test that /info endpoint response contains exactly two fields.
     * This ensures no unexpected data is returned.
     */
    @Test
    public void shouldReturnExactlyTwoFields() throws Exception {
        mockMvc.perform(get("/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.application").exists())
                .andExpect(jsonPath("$.version").exists())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*").value(org.hamcrest.Matchers.hasSize(2)));
    }
}
