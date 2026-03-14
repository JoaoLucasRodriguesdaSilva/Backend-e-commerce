package com.ecommerce;

import com.ecommerce.dto.CustomerRequest;
import com.ecommerce.dto.OrderRequest;
import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.VariantRequest;
import com.ecommerce.dto.OrderItemRequest;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.VariantAttribute;
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
class OrderItemControllerTest extends AbstractPostgresTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private Long createCustomer(String email) throws Exception {
        CustomerRequest req = new CustomerRequest("User", email, null, null, null);
        MvcResult r = mockMvc.perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))).andReturn();
        return objectMapper.readTree(r.getResponse().getContentAsString()).get("id").asLong();
    }

    private Long createOrder(Long customerId) throws Exception {
        OrderRequest req = new OrderRequest(customerId, OrderStatus.PENDING,
            new BigDecimal("100"), null, null, new BigDecimal("100"));
        MvcResult r = mockMvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))).andReturn();
        return objectMapper.readTree(r.getResponse().getContentAsString()).get("id").asLong();
    }

    private Long createVariant(String sku) throws Exception {
        ProductRequest pr = new ProductRequest("Product", null, null,
            new BigDecimal("100"), null, sku, null, null, null, null, null, true);
        MvcResult r = mockMvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(pr))).andReturn();
        Long productId = objectMapper.readTree(r.getResponse().getContentAsString()).get("id").asLong();
        VariantRequest vr = new VariantRequest(productId, VariantAttribute.COLOR, "Red", null, sku + "-V");
        MvcResult rv = mockMvc.perform(post("/api/variants").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(vr))).andReturn();
        return objectMapper.readTree(rv.getResponse().getContentAsString()).get("id").asLong();
    }

    @Test
    void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/order-items"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldCreateOrderItem() throws Exception {
        Long customerId = createCustomer("orderitem@example.com");
        Long orderId = createOrder(customerId);
        Long variantId = createVariant("OI-SKU-001");
        OrderItemRequest req = new OrderItemRequest(orderId, variantId, 2,
            new BigDecimal("50"), "Phone", "OI-SKU-001-V");
        mockMvc.perform(post("/api/order-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test
    void shouldGetOrderItemById() throws Exception {
        Long customerId = createCustomer("orderitem2@example.com");
        Long orderId = createOrder(customerId);
        Long variantId = createVariant("OI-SKU-002");
        OrderItemRequest req = new OrderItemRequest(orderId, variantId, 1,
            new BigDecimal("75"), "Tablet", "OI-SKU-002-V");
        MvcResult result = mockMvc.perform(post("/api/order-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(get("/api/order-items/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void shouldDeleteOrderItem() throws Exception {
        Long customerId = createCustomer("orderitem3@example.com");
        Long orderId = createOrder(customerId);
        Long variantId = createVariant("OI-SKU-003");
        OrderItemRequest req = new OrderItemRequest(orderId, variantId, 1,
            new BigDecimal("30"), null, null);
        MvcResult result = mockMvc.perform(post("/api/order-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(delete("/api/order-items/" + id))
            .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/order-items/" + id))
            .andExpect(status().isNotFound());
    }
}
