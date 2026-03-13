package com.ecommerce;

import com.ecommerce.dto.CustomerRequest;
import com.ecommerce.dto.ReviewRequest;
import com.ecommerce.dto.ProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReviewControllerTest extends AbstractPostgresTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private Long createProduct(String sku) throws Exception {
        ProductRequest req = new ProductRequest("Product", null, null,
            new BigDecimal("100"), null, sku, null, null, null, null, null, true);
        MvcResult r = mockMvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))).andReturn();
        return objectMapper.readTree(r.getResponse().getContentAsString()).get("id").asLong();
    }

    private Long createCustomer(String email) throws Exception {
        CustomerRequest req = new CustomerRequest("Reviewer", email, null, null, null);
        MvcResult r = mockMvc.perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))).andReturn();
        return objectMapper.readTree(r.getResponse().getContentAsString()).get("id").asLong();
    }

    @Test
    void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/reviews"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldCreateReview() throws Exception {
        Long productId = createProduct("REV-SKU-001");
        Long customerId = createCustomer("rev1@example.com");
        ReviewRequest req = new ReviewRequest(productId, customerId, 5, "Great!", "Love it", true);
        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    void shouldGetReviewById() throws Exception {
        Long productId = createProduct("REV-SKU-002");
        Long customerId = createCustomer("rev2@example.com");
        ReviewRequest req = new ReviewRequest(productId, customerId, 4, "Good", null, false);
        MvcResult result = mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(get("/api/reviews/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.rating").value(4));
    }

    @Test
    void shouldDeleteReview() throws Exception {
        Long productId = createProduct("REV-SKU-003");
        Long customerId = createCustomer("rev3@example.com");
        ReviewRequest req = new ReviewRequest(productId, customerId, 3, null, null, null);
        MvcResult result = mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(delete("/api/reviews/" + id))
            .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/reviews/" + id))
            .andExpect(status().isNotFound());
    }
}
