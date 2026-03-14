package com.ecommerce;

import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.VariantRequest;
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
class VariantControllerTest extends AbstractPostgresTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private Long createProduct() throws Exception {
        ProductRequest req = new ProductRequest("Phone", null, null,
            new BigDecimal("500"), null, "SKU-V1", null, null, null, null, null, true);
        MvcResult result = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        return objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
    }

    @Test
    void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/variants"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldCreateVariant() throws Exception {
        Long productId = createProduct();
        VariantRequest req = new VariantRequest(productId, VariantAttribute.COLOR, "Red", null, "VAR-001");
        mockMvc.perform(post("/api/variants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.value").value("Red"));
    }

    @Test
    void shouldGetVariantById() throws Exception {
        Long productId = createProduct();
        VariantRequest req = new VariantRequest(productId, VariantAttribute.STORAGE, "128GB", null, "VAR-002");
        MvcResult result = mockMvc.perform(post("/api/variants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(get("/api/variants/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.value").value("128GB"));
    }

    @Test
    void shouldDeleteVariant() throws Exception {
        Long productId = createProduct();
        VariantRequest req = new VariantRequest(productId, VariantAttribute.COLOR, "Blue", null, "VAR-003");
        MvcResult result = mockMvc.perform(post("/api/variants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(delete("/api/variants/" + id))
            .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/variants/" + id))
            .andExpect(status().isNotFound());
    }
}
