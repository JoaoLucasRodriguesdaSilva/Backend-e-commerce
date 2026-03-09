package com.ecommerce;

import com.ecommerce.dto.CustomerRequest;
import com.ecommerce.dto.OrderRequest;
import com.ecommerce.dto.PaymentRequest;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.PaymentMethod;
import com.ecommerce.enums.PaymentStatus;
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
class PaymentControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private Long createOrder() throws Exception {
        CustomerRequest cr = new CustomerRequest("Pay User", "payuser@example.com", null, null, null);
        MvcResult rc = mockMvc.perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cr))).andReturn();
        Long cid = objectMapper.readTree(rc.getResponse().getContentAsString()).get("id").asLong();
        OrderRequest or = new OrderRequest(cid, OrderStatus.PENDING,
            new BigDecimal("100"), null, null, new BigDecimal("100"));
        MvcResult ro = mockMvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(or))).andReturn();
        return objectMapper.readTree(ro.getResponse().getContentAsString()).get("id").asLong();
    }

    @Test
    void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/payments"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldCreatePayment() throws Exception {
        Long orderId = createOrder();
        PaymentRequest req = new PaymentRequest(orderId, PaymentMethod.PIX, PaymentStatus.PENDING,
            new BigDecimal("100"), null, null, null, null);
        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.method").value("PIX"));
    }

    @Test
    void shouldGetPaymentById() throws Exception {
        Long orderId = createOrder();
        PaymentRequest req = new PaymentRequest(orderId, PaymentMethod.CREDIT_CARD, PaymentStatus.PAID,
            new BigDecimal("200"), 3, "stripe", "txn123", null);
        MvcResult result = mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(get("/api/payments/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void shouldDeletePayment() throws Exception {
        Long orderId = createOrder();
        PaymentRequest req = new PaymentRequest(orderId, PaymentMethod.BANK_SLIP, PaymentStatus.PENDING,
            new BigDecimal("50"), null, null, null, null);
        MvcResult result = mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(delete("/api/payments/" + id))
            .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/payments/" + id))
            .andExpect(status().isNotFound());
    }
}
