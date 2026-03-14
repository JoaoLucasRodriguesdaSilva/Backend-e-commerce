package com.ecommerce;

import com.ecommerce.dto.ProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductControllerTest extends AbstractPostgresTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void shouldReturnEmptyListWhenNoProducts() throws Exception {
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest req = new ProductRequest("Laptop", "Desc", null,
            new BigDecimal("999.99"), null, "SKU-001", "Brand", "Model", 12,
            new BigDecimal("1.5"), "30x20x5", true);
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void shouldGetProductById() throws Exception {
        ProductRequest req = new ProductRequest("Laptop", null, null,
            new BigDecimal("999.99"), null, "SKU-002", null, null, null, null, null, true);
        MvcResult result = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        String body = result.getResponse().getContentAsString();
        Long id = objectMapper.readTree(body).get("id").asLong();
        mockMvc.perform(get("/api/products/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        ProductRequest req = new ProductRequest("Laptop", null, null,
            new BigDecimal("999.99"), null, "SKU-003", null, null, null, null, null, true);
        MvcResult result = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        ProductRequest update = new ProductRequest("Updated Laptop", null, null,
            new BigDecimal("899.99"), null, "SKU-003", null, null, null, null, null, true);
        mockMvc.perform(put("/api/products/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated Laptop"));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        ProductRequest req = new ProductRequest("Laptop", null, null,
            new BigDecimal("999.99"), null, "SKU-004", null, null, null, null, null, true);
        MvcResult result = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(delete("/api/products/" + id))
            .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/products/" + id))
            .andExpect(status().isNotFound());
    }
}
