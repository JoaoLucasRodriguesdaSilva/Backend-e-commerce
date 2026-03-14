package com.ecommerce;

import com.ecommerce.dto.CustomerRequest;
import com.ecommerce.dto.SupportTicketRequest;
import com.ecommerce.enums.SupportChannel;
import com.ecommerce.enums.TicketStatus;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SupportTicketControllerTest extends AbstractPostgresTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private Long createCustomer(String email) throws Exception {
        CustomerRequest req = new CustomerRequest("Support User", email, null, null, null);
        MvcResult r = mockMvc.perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))).andReturn();
        return objectMapper.readTree(r.getResponse().getContentAsString()).get("id").asLong();
    }

    @Test
    void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/support-tickets"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldCreateSupportTicket() throws Exception {
        Long customerId = createCustomer("st1@example.com");
        SupportTicketRequest req = new SupportTicketRequest(customerId, null,
            "Issue with order", TicketStatus.OPEN, SupportChannel.EMAIL);
        mockMvc.perform(post("/api/support-tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.subject").value("Issue with order"));
    }

    @Test
    void shouldGetSupportTicketById() throws Exception {
        Long customerId = createCustomer("st2@example.com");
        SupportTicketRequest req = new SupportTicketRequest(customerId, null,
            "Payment problem", TicketStatus.OPEN, SupportChannel.CHAT);
        MvcResult result = mockMvc.perform(post("/api/support-tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(get("/api/support-tickets/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.subject").value("Payment problem"));
    }

    @Test
    void shouldDeleteSupportTicket() throws Exception {
        Long customerId = createCustomer("st3@example.com");
        SupportTicketRequest req = new SupportTicketRequest(customerId, null,
            "Delivery late", TicketStatus.OPEN, SupportChannel.EMAIL);
        MvcResult result = mockMvc.perform(post("/api/support-tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();
        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        mockMvc.perform(delete("/api/support-tickets/" + id))
            .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/support-tickets/" + id))
            .andExpect(status().isNotFound());
    }
}
