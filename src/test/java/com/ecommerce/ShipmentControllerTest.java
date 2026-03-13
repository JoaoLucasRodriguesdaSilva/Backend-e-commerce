package com.ecommerce;

import com.ecommerce.dto.CustomerRequest;
import com.ecommerce.dto.OrderRequest;
import com.ecommerce.dto.ShipmentRequest;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.ShipmentService;
import com.ecommerce.enums.ShipmentStatus;
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
class ShipmentControllerTest extends AbstractPostgresTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private Long createOrder() throws Exception {
        CustomerRequest cr = new CustomerRequest("Ship User", "shipuser@example.com", null, null, null);
        MvcResult rc = mockMvc.perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cr))).andReturn();
        Long cid = objectMapper.readTree(rc.getResponse().getContentAsString()).get("id").asLong();
        OrderRequest or = new OrderRequest(cid, OrderStatus.PAID,
            new BigDecimal("100"), null, new BigDecimal("15"), new BigDecimal("115"));
        MvcResult ro = mockMvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(or))).andReturn();
        return objectMapper.readTree(ro.getResponse().getContentAsString()).get("id").asLong();
    }

    @Test
    void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/shipments"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldCreateShipment() throws Exception {
        Long orderId = createOrder();
        ShipmentRequest req = new ShipmentRequest(orderId, "FedEx", ShipmentService.EXPRESS,
            "TRK123", ShipmentStatus.PENDING, new BigDecimal("15"), 3, null, null);
        mockMvc.perform(post("/api/shipments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.carrier").value("FedEx"));
    }

    @Test
    void shouldGetShipmentById() throws Exception {
        Long orderId = createOrder();
        ShipmentRequest req = new ShipmentRequest(orderId, "UPS", ShipmentService.STANDARD,
            "TRK456", ShipmentStatus.SHIPPED, new BigDecimal("10"), 5, null, null);
        MvcResult result = mockMvc.perform(post("/api/shipments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(get("/api/shipments/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void shouldDeleteShipment() throws Exception {
        Long orderId = createOrder();
        ShipmentRequest req = new ShipmentRequest(orderId, "DHL", null, null,
            ShipmentStatus.PENDING, null, null, null, null);
        MvcResult result = mockMvc.perform(post("/api/shipments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(delete("/api/shipments/" + id))
            .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/shipments/" + id))
            .andExpect(status().isNotFound());
    }
}
