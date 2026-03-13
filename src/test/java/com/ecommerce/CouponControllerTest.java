package com.ecommerce;

import com.ecommerce.dto.CouponRequest;
import com.ecommerce.enums.CouponType;
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
class CouponControllerTest extends AbstractPostgresTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/coupons"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldCreateCoupon() throws Exception {
        CouponRequest req = new CouponRequest("SAVE10", CouponType.PERCENTAGE, new BigDecimal("10"),
            100, 0, null, new BigDecimal("50"));
        mockMvc.perform(post("/api/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.code").value("SAVE10"));
    }

    @Test
    void shouldGetCouponById() throws Exception {
        CouponRequest req = new CouponRequest("FLAT20", CouponType.FIXED, new BigDecimal("20"),
            50, 0, null, null);
        MvcResult result = mockMvc.perform(post("/api/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(get("/api/coupons/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("FLAT20"));
    }

    @Test
    void shouldDeleteCoupon() throws Exception {
        CouponRequest req = new CouponRequest("DEL10", CouponType.FIXED, new BigDecimal("10"),
            10, 0, null, null);
        MvcResult result = mockMvc.perform(post("/api/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(delete("/api/coupons/" + id))
            .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/coupons/" + id))
            .andExpect(status().isNotFound());
    }
}
