package com.ecommerce;

import com.ecommerce.dto.*;
import com.ecommerce.enums.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserProfileAccessControlTest extends AbstractPostgresTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    // -------------------------------------------------------------------------
    // Shared post-processors
    // -------------------------------------------------------------------------
    private static final UserRequestPostProcessor ADMIN = user("admin@test.com").roles("ADMIN");
    private static final UserRequestPostProcessor USER_A = user("usera@test.com").roles("USER");
    private static final UserRequestPostProcessor USER_B = user("userb@test.com").roles("USER");

    // -------------------------------------------------------------------------
    // Setup helpers (always run as ADMIN)
    // -------------------------------------------------------------------------

    private Long createCustomer(String email) throws Exception {
        CustomerRequest req = new CustomerRequest("User " + email, email, null, null, null);
        MvcResult r = mockMvc.perform(post("/api/customers").with(ADMIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andReturn();
        return objectMapper.readTree(r.getResponse().getContentAsString()).get("id").asLong();
    }

    private Long createProduct(String sku) throws Exception {
        ProductRequest req = new ProductRequest("Product", null, null,
            new BigDecimal("50"), null, sku, null, null, null, null, null, true);
        MvcResult r = mockMvc.perform(post("/api/products").with(ADMIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andReturn();
        return objectMapper.readTree(r.getResponse().getContentAsString()).get("id").asLong();
    }

    private Long createSupportTicket(Long customerId, String subject) throws Exception {
        SupportTicketRequest req = new SupportTicketRequest(customerId, null,
            subject, TicketStatus.OPEN, SupportChannel.EMAIL);
        MvcResult r = mockMvc.perform(post("/api/support-tickets").with(ADMIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andReturn();
        return objectMapper.readTree(r.getResponse().getContentAsString()).get("id").asLong();
    }

    private Long createReview(Long productId, Long customerId, int rating) throws Exception {
        ReviewRequest req = new ReviewRequest(productId, customerId, rating, null, null, null);
        MvcResult r = mockMvc.perform(post("/api/reviews").with(ADMIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andReturn();
        return objectMapper.readTree(r.getResponse().getContentAsString()).get("id").asLong();
    }

    // =========================================================================
    // CustomerController /my tests
    // =========================================================================

    @Test
    void shouldUserGetOwnCustomerProfile() throws Exception {
        createCustomer("usera@test.com");

        mockMvc.perform(get("/api/customers/my").with(USER_A))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("usera@test.com"));
    }

    @Test
    void shouldUserGetNotFoundWhenNoCustomerProfile() throws Exception {
        // usera@test.com has no customer record yet
        mockMvc.perform(get("/api/customers/my").with(USER_A))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldUserCreateOwnCustomerProfile() throws Exception {
        CustomerRequest req = new CustomerRequest("User A", null, "123.456.789-00", "+1-555-0001", null);
        mockMvc.perform(post("/api/customers/my").with(USER_A)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email").value("usera@test.com"))
            .andExpect(jsonPath("$.name").value("User A"));
    }

    @Test
    void shouldUserUpdateOwnCustomerProfile() throws Exception {
        createCustomer("usera@test.com");

        CustomerRequest update = new CustomerRequest("Updated Name", null, "111.222.333-44", null, null);
        mockMvc.perform(put("/api/customers/my").with(USER_A)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated Name"))
            // email must remain unchanged (locked to auth email)
            .andExpect(jsonPath("$.email").value("usera@test.com"));
    }

    @Test
    void shouldUserUpdateNotFoundWhenNoCustomerProfile() throws Exception {
        CustomerRequest update = new CustomerRequest("Ghost", null, null, null, null);
        mockMvc.perform(put("/api/customers/my").with(USER_A)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldUserBeForbiddenFromGlobalCustomerList() throws Exception {
        mockMvc.perform(get("/api/customers").with(USER_A))
            .andExpect(status().isForbidden());
    }

    @Test
    void shouldUserBeForbiddenFromGlobalCustomerGetById() throws Exception {
        Long customerId = createCustomer("usera@test.com");
        mockMvc.perform(get("/api/customers/" + customerId).with(USER_A))
            .andExpect(status().isForbidden());
    }

    // =========================================================================
    // SupportTicketController /my tests
    // =========================================================================

    @Test
    void shouldUserListOwnSupportTickets() throws Exception {
        Long custAId = createCustomer("usera@test.com");
        Long ticketId = createSupportTicket(custAId, "My issue");

        mockMvc.perform(get("/api/support-tickets/my").with(USER_A))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(ticketId))
            .andExpect(jsonPath("$[0].subject").value("My issue"));
    }

    @Test
    void shouldUserGetOwnSupportTicketById() throws Exception {
        Long custAId = createCustomer("usera@test.com");
        Long ticketId = createSupportTicket(custAId, "Specific issue");

        mockMvc.perform(get("/api/support-tickets/my/" + ticketId).with(USER_A))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(ticketId));
    }

    @Test
    void shouldUserGetNotFoundForOtherUsersSupportTicket() throws Exception {
        Long custBId = createCustomer("userb@test.com");
        Long ticketBId = createSupportTicket(custBId, "User B issue");

        mockMvc.perform(get("/api/support-tickets/my/" + ticketBId).with(USER_A))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldUserCreateOwnSupportTicket() throws Exception {
        createCustomer("usera@test.com");

        SupportTicketRequest req = new SupportTicketRequest(null, null,
            "New complaint", TicketStatus.OPEN, SupportChannel.CHAT);
        mockMvc.perform(post("/api/support-tickets/my").with(USER_A)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.subject").value("New complaint"));
    }

    @Test
    void shouldUserCreateSupportTicketFailsWithNoCustomerRecord() throws Exception {
        // usera@test.com has no customer record
        SupportTicketRequest req = new SupportTicketRequest(null, null,
            "Ghost ticket", TicketStatus.OPEN, SupportChannel.EMAIL);
        mockMvc.perform(post("/api/support-tickets/my").with(USER_A)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldUserDeleteOwnSupportTicket() throws Exception {
        Long custAId = createCustomer("usera@test.com");
        Long ticketId = createSupportTicket(custAId, "To delete");

        mockMvc.perform(delete("/api/support-tickets/my/" + ticketId).with(USER_A))
            .andExpect(status().isNoContent());

        // Verify it's gone (admin check)
        mockMvc.perform(get("/api/support-tickets/" + ticketId).with(ADMIN))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldUserDeleteNotFoundForOtherUsersSupportTicket() throws Exception {
        Long custBId = createCustomer("userb@test.com");
        Long ticketBId = createSupportTicket(custBId, "User B ticket");

        mockMvc.perform(delete("/api/support-tickets/my/" + ticketBId).with(USER_A))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldUserBeForbiddenFromGlobalSupportTicketList() throws Exception {
        mockMvc.perform(get("/api/support-tickets").with(USER_A))
            .andExpect(status().isForbidden());
    }

    // =========================================================================
    // ReviewController /my tests
    // =========================================================================

    @Test
    void shouldUserReadAllReviews() throws Exception {
        Long productId = createProduct("RSKU-001");
        Long custAId = createCustomer("usera@test.com");
        createReview(productId, custAId, 5);

        mockMvc.perform(get("/api/reviews").with(USER_A))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].rating").value(5));
    }

    @Test
    void shouldUserReadReviewById() throws Exception {
        Long productId = createProduct("RSKU-002");
        Long custBId = createCustomer("userb@test.com");
        Long reviewId = createReview(productId, custBId, 4);

        // User A can read any review (global GET is open to USER role)
        mockMvc.perform(get("/api/reviews/" + reviewId).with(USER_A))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.rating").value(4));
    }

    @Test
    void shouldUserCreateOwnReview() throws Exception {
        Long productId = createProduct("RSKU-003");
        createCustomer("usera@test.com");

        ReviewRequest req = new ReviewRequest(productId, null, 5, "Excellent!", "Really loved it", true);
        mockMvc.perform(post("/api/reviews/my").with(USER_A)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.rating").value(5))
            .andExpect(jsonPath("$.title").value("Excellent!"));
    }

    @Test
    void shouldUserCreateReviewFailsWithNoCustomerRecord() throws Exception {
        Long productId = createProduct("RSKU-004");

        ReviewRequest req = new ReviewRequest(productId, null, 3, null, null, null);
        mockMvc.perform(post("/api/reviews/my").with(USER_A)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldUserUpdateOwnReview() throws Exception {
        Long productId = createProduct("RSKU-005");
        Long custAId = createCustomer("usera@test.com");
        Long reviewId = createReview(productId, custAId, 3);

        ReviewRequest update = new ReviewRequest(productId, null, 5, "Changed mind", "Actually great", true);
        mockMvc.perform(put("/api/reviews/my/" + reviewId).with(USER_A)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.rating").value(5))
            .andExpect(jsonPath("$.title").value("Changed mind"));
    }

    @Test
    void shouldUserGetNotFoundWhenUpdatingOtherUsersReview() throws Exception {
        Long productId = createProduct("RSKU-006");
        Long custBId = createCustomer("userb@test.com");
        Long reviewBId = createReview(productId, custBId, 4);

        ReviewRequest update = new ReviewRequest(productId, null, 1, "Trying to hijack", null, false);
        mockMvc.perform(put("/api/reviews/my/" + reviewBId).with(USER_A)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldUserBeForbiddenFromCreatingReviewOnGlobalEndpoint() throws Exception {
        Long productId = createProduct("RSKU-007");
        Long custAId = createCustomer("usera@test.com");

        ReviewRequest req = new ReviewRequest(productId, custAId, 4, null, null, null);
        mockMvc.perform(post("/api/reviews").with(USER_A)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isForbidden());
    }
}
