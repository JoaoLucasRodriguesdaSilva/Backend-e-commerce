package com.ecommerce;

import com.ecommerce.dto.CouponApplyRequest;
import com.ecommerce.dto.CouponRequest;
import com.ecommerce.dto.ProductRequest;
import com.ecommerce.enums.CouponType;
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
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
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

    @Test
    void shouldApplyPercentageCouponToProduct() throws Exception {
        ProductRequest productReq = new ProductRequest("Phone", null, null,
            new BigDecimal("100.00"), null, "SKU-COUPON-1", null, null, null, null, null, true);
        MvcResult productResult = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productReq)))
            .andReturn();
        Long productId = objectMapper.readTree(productResult.getResponse().getContentAsString()).get("id").asLong();

        CouponRequest couponReq = new CouponRequest("PCT10", CouponType.PERCENTAGE, new BigDecimal("10"),
            5, 0, null, null);
        mockMvc.perform(post("/api/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(couponReq)))
            .andExpect(status().isCreated());

        CouponApplyRequest applyReq = new CouponApplyRequest("PCT10", productId);
        mockMvc.perform(post("/api/coupons/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(applyReq)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.originalPrice").value(100.00))
            .andExpect(jsonPath("$.discountAmount").value(10.00))
            .andExpect(jsonPath("$.finalPrice").value(90.00));
    }

    @Test
    void shouldApplyFixedCouponToProduct() throws Exception {
        ProductRequest productReq = new ProductRequest("Keyboard", null, null,
            new BigDecimal("50.00"), null, "SKU-COUPON-2", null, null, null, null, null, true);
        MvcResult productResult = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productReq)))
            .andReturn();
        Long productId = objectMapper.readTree(productResult.getResponse().getContentAsString()).get("id").asLong();

        CouponRequest couponReq = new CouponRequest("FIXED20", CouponType.FIXED, new BigDecimal("20"),
            null, null, null, null);
        mockMvc.perform(post("/api/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(couponReq)))
            .andExpect(status().isCreated());

        CouponApplyRequest applyReq = new CouponApplyRequest("FIXED20", productId);
        mockMvc.perform(post("/api/coupons/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(applyReq)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.originalPrice").value(50.00))
            .andExpect(jsonPath("$.discountAmount").value(20.00))
            .andExpect(jsonPath("$.finalPrice").value(30.00));
    }

    @Test
    void shouldRejectExpiredCoupon() throws Exception {
        ProductRequest productReq = new ProductRequest("Monitor", null, null,
            new BigDecimal("200.00"), null, "SKU-COUPON-3", null, null, null, null, null, true);
        MvcResult productResult = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productReq)))
            .andReturn();
        Long productId = objectMapper.readTree(productResult.getResponse().getContentAsString()).get("id").asLong();

        CouponRequest couponReq = new CouponRequest("EXPIRED", CouponType.PERCENTAGE, new BigDecimal("15"),
            10, 0, LocalDateTime.now().minusDays(1), null);
        mockMvc.perform(post("/api/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(couponReq)))
            .andExpect(status().isCreated());

        CouponApplyRequest applyReq = new CouponApplyRequest("EXPIRED", productId);
        mockMvc.perform(post("/api/coupons/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(applyReq)))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void shouldRejectCouponWhenUsageLimitReached() throws Exception {
        ProductRequest productReq = new ProductRequest("Tablet", null, null,
            new BigDecimal("300.00"), null, "SKU-COUPON-4", null, null, null, null, null, true);
        MvcResult productResult = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productReq)))
            .andReturn();
        Long productId = objectMapper.readTree(productResult.getResponse().getContentAsString()).get("id").asLong();

        CouponRequest couponReq = new CouponRequest("MAXED", CouponType.FIXED, new BigDecimal("10"),
            1, 1, null, null);
        mockMvc.perform(post("/api/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(couponReq)))
            .andExpect(status().isCreated());

        CouponApplyRequest applyReq = new CouponApplyRequest("MAXED", productId);
        mockMvc.perform(post("/api/coupons/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(applyReq)))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void shouldRejectCouponWhenMinimumOrderNotMet() throws Exception {
        ProductRequest productReq = new ProductRequest("Cable", null, null,
            new BigDecimal("30.00"), null, "SKU-COUPON-5", null, null, null, null, null, true);
        MvcResult productResult = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productReq)))
            .andReturn();
        Long productId = objectMapper.readTree(productResult.getResponse().getContentAsString()).get("id").asLong();

        CouponRequest couponReq = new CouponRequest("MINORD", CouponType.FIXED, new BigDecimal("10"),
            null, null, null, new BigDecimal("50"));
        mockMvc.perform(post("/api/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(couponReq)))
            .andExpect(status().isCreated());

        CouponApplyRequest applyReq = new CouponApplyRequest("MINORD", productId);
        mockMvc.perform(post("/api/coupons/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(applyReq)))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(roles = "USER")
    void userShouldBeAbleToApplyCoupon() throws Exception {
        CouponApplyRequest applyReq = new CouponApplyRequest("NONEXISTENT", 999L);
        mockMvc.perform(post("/api/coupons/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(applyReq)))
            .andExpect(status().isNotFound()); // 404, not 403
    }

    @Test
    @WithMockUser(roles = "USER")
    void userShouldBeAbleToGetCoupons() throws Exception {
        mockMvc.perform(get("/api/coupons"))
            .andExpect(status().isOk());
    }
}
