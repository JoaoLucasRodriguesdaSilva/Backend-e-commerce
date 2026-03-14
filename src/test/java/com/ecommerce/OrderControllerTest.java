package com.ecommerce;

import com.ecommerce.dto.CustomerRequest;
import com.ecommerce.dto.OrderRequest;
import com.ecommerce.enums.OrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderControllerTest extends AbstractPostgresTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private Long createCustomer() throws Exception {
        CustomerRequest req = new CustomerRequest("Test User", "testuser@example.com", null, null, null);
        MvcResult result = mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        return objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
    }

    @Test
    void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/orders"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldCreateOrder() throws Exception {
        Long customerId = createCustomer();
        OrderRequest req = new OrderRequest(customerId, OrderStatus.PENDING,
            new BigDecimal("100"), null, new BigDecimal("10"), new BigDecimal("110"));
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void shouldGetOrderById() throws Exception {
        Long customerId = createCustomer();
        OrderRequest req = new OrderRequest(customerId, OrderStatus.PENDING,
            new BigDecimal("200"), null, new BigDecimal("15"), new BigDecimal("215"));
        MvcResult result = mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(get("/api/orders/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void shouldUpdateOrder() throws Exception {
        Long customerId = createCustomer();
        OrderRequest req = new OrderRequest(customerId, OrderStatus.PENDING,
            new BigDecimal("100"), null, null, new BigDecimal("100"));
        MvcResult result = mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        OrderRequest update = new OrderRequest(customerId, OrderStatus.PAID,
            new BigDecimal("100"), null, null, new BigDecimal("100"));
        mockMvc.perform(put("/api/orders/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("PAID"));
    }

    @Test
    void shouldDeleteOrder() throws Exception {
        Long customerId = createCustomer();
        OrderRequest req = new OrderRequest(customerId, OrderStatus.PENDING,
            new BigDecimal("50"), null, null, new BigDecimal("50"));
        MvcResult result = mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(delete("/api/orders/" + id))
            .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/orders/" + id))
            .andExpect(status().isNotFound());
    }
}
