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
class UserOrderAccessControlTest extends AbstractPostgresTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private static final UserRequestPostProcessor ADMIN = user("admin@test.com").roles("ADMIN");
    private static final UserRequestPostProcessor USER_A = user("usera@test.com").roles("USER");
    private static final UserRequestPostProcessor USER_B = user("userb@test.com").roles("USER");

    // -------------------------------------------------------------------------
    // Setup helpers (always run as ADMIN so they are not affected by the test's
    // mock user, which is set via the RequestPostProcessor on each perform() call)
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

    private Long createOrder(Long customerId) throws Exception {
        OrderRequest req = new OrderRequest(customerId, OrderStatus.PENDING,
            new BigDecimal("100"), null, new BigDecimal("10"), new BigDecimal("110"));
        MvcResult r = mockMvc.perform(post("/api/orders").with(ADMIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andReturn();
        return objectMapper.readTree(r.getResponse().getContentAsString()).get("id").asLong();
    }

    private Long createVariant(String sku) throws Exception {
        ProductRequest pr = new ProductRequest("Product", null, null,
            new BigDecimal("100"), null, sku, null, null, null, null, null, true);
        MvcResult rp = mockMvc.perform(post("/api/products").with(ADMIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pr)))
            .andExpect(status().isCreated())
            .andReturn();
        Long productId = objectMapper.readTree(rp.getResponse().getContentAsString()).get("id").asLong();
        VariantRequest vr = new VariantRequest(productId, VariantAttribute.COLOR, "Black", null, sku + "-V");
        MvcResult rv = mockMvc.perform(post("/api/variants").with(ADMIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vr)))
            .andExpect(status().isCreated())
            .andReturn();
        return objectMapper.readTree(rv.getResponse().getContentAsString()).get("id").asLong();
    }

    private Long createOrderItem(Long orderId, Long variantId) throws Exception {
        OrderItemRequest req = new OrderItemRequest(orderId, variantId, 1,
            new BigDecimal("100"), "Product", "SKU-V");
        MvcResult r = mockMvc.perform(post("/api/order-items").with(ADMIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andReturn();
        return objectMapper.readTree(r.getResponse().getContentAsString()).get("id").asLong();
    }

    private Long createShipment(Long orderId) throws Exception {
        ShipmentRequest req = new ShipmentRequest(orderId, "FedEx", ShipmentService.STANDARD,
            "TRK001", ShipmentStatus.PENDING, new BigDecimal("10"), 5, null, null);
        MvcResult r = mockMvc.perform(post("/api/shipments").with(ADMIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andReturn();
        return objectMapper.readTree(r.getResponse().getContentAsString()).get("id").asLong();
    }

    private Long createPayment(Long orderId) throws Exception {
        PaymentRequest req = new PaymentRequest(orderId, PaymentMethod.PIX, PaymentStatus.PENDING,
            new BigDecimal("110"), null, null, null, null);
        MvcResult r = mockMvc.perform(post("/api/payments").with(ADMIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andReturn();
        return objectMapper.readTree(r.getResponse().getContentAsString()).get("id").asLong();
    }

    private Long createOrderReturn(Long orderId) throws Exception {
        OrderReturnRequest req = new OrderReturnRequest(orderId, "Defective item",
            ReturnStatus.REQUESTED, ReturnType.REFUND, null);
        MvcResult r = mockMvc.perform(post("/api/order-returns").with(ADMIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andReturn();
        return objectMapper.readTree(r.getResponse().getContentAsString()).get("id").asLong();
    }

    // -------------------------------------------------------------------------
    // Order /my tests
    // -------------------------------------------------------------------------

    @Test
    void shouldUserListOwnOrders() throws Exception {
        Long customerAId = createCustomer("usera@test.com");
        Long orderAId = createOrder(customerAId);

        mockMvc.perform(get("/api/orders/my").with(USER_A))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(orderAId))
            .andExpect(jsonPath("$[0].customerId").value(customerAId));
    }

    @Test
    void shouldUserGetOwnOrderById() throws Exception {
        Long customerAId = createCustomer("usera@test.com");
        Long orderAId = createOrder(customerAId);

        mockMvc.perform(get("/api/orders/my/" + orderAId).with(USER_A))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(orderAId));
    }

    @Test
    void shouldUserGetNotFoundForOtherUsersOrder() throws Exception {
        Long customerAId = createCustomer("usera@test.com");
        Long customerBId = createCustomer("userb@test.com");
        Long orderBId = createOrder(customerBId);

        // User A tries to access User B's order via /my → 404
        mockMvc.perform(get("/api/orders/my/" + orderBId).with(USER_A))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldUserBeForbiddenFromGlobalOrderList() throws Exception {
        mockMvc.perform(get("/api/orders").with(USER_A))
            .andExpect(status().isForbidden());
    }

    // -------------------------------------------------------------------------
    // OrderItem /my tests
    // -------------------------------------------------------------------------

    @Test
    void shouldUserListOwnOrderItems() throws Exception {
        Long customerAId = createCustomer("usera@test.com");
        Long orderAId = createOrder(customerAId);
        Long variantId = createVariant("UA-SKU-001");
        Long itemAId = createOrderItem(orderAId, variantId);

        mockMvc.perform(get("/api/order-items/my").with(USER_A))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(itemAId));
    }

    @Test
    void shouldUserGetOwnOrderItemById() throws Exception {
        Long customerAId = createCustomer("usera@test.com");
        Long orderAId = createOrder(customerAId);
        Long variantId = createVariant("UA-SKU-002");
        Long itemAId = createOrderItem(orderAId, variantId);

        mockMvc.perform(get("/api/order-items/my/" + itemAId).with(USER_A))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(itemAId));
    }

    @Test
    void shouldUserGetNotFoundForOtherUsersOrderItem() throws Exception {
        Long customerAId = createCustomer("usera@test.com");
        Long customerBId = createCustomer("userb@test.com");
        Long orderBId = createOrder(customerBId);
        Long variantId = createVariant("UB-SKU-001");
        Long itemBId = createOrderItem(orderBId, variantId);

        mockMvc.perform(get("/api/order-items/my/" + itemBId).with(USER_A))
            .andExpect(status().isNotFound());
    }

    // -------------------------------------------------------------------------
    // Shipment /my tests
    // -------------------------------------------------------------------------

    @Test
    void shouldUserListOwnShipments() throws Exception {
        Long customerAId = createCustomer("usera@test.com");
        Long orderAId = createOrder(customerAId);
        Long shipmentAId = createShipment(orderAId);

        mockMvc.perform(get("/api/shipments/my").with(USER_A))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(shipmentAId));
    }

    @Test
    void shouldUserGetOwnShipmentById() throws Exception {
        Long customerAId = createCustomer("usera@test.com");
        Long orderAId = createOrder(customerAId);
        Long shipmentAId = createShipment(orderAId);

        mockMvc.perform(get("/api/shipments/my/" + shipmentAId).with(USER_A))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(shipmentAId));
    }

    @Test
    void shouldUserGetNotFoundForOtherUsersShipment() throws Exception {
        Long customerAId = createCustomer("usera@test.com");
        Long customerBId = createCustomer("userb@test.com");
        Long orderBId = createOrder(customerBId);
        Long shipmentBId = createShipment(orderBId);

        mockMvc.perform(get("/api/shipments/my/" + shipmentBId).with(USER_A))
            .andExpect(status().isNotFound());
    }

    // -------------------------------------------------------------------------
    // Payment /my tests
    // -------------------------------------------------------------------------

    @Test
    void shouldUserListOwnPayments() throws Exception {
        Long customerAId = createCustomer("usera@test.com");
        Long orderAId = createOrder(customerAId);
        Long paymentAId = createPayment(orderAId);

        mockMvc.perform(get("/api/payments/my").with(USER_A))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(paymentAId));
    }

    @Test
    void shouldUserGetOwnPaymentById() throws Exception {
        Long customerAId = createCustomer("usera@test.com");
        Long orderAId = createOrder(customerAId);
        Long paymentAId = createPayment(orderAId);

        mockMvc.perform(get("/api/payments/my/" + paymentAId).with(USER_A))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(paymentAId));
    }

    @Test
    void shouldUserCreatePaymentForOwnOrder() throws Exception {
        Long customerAId = createCustomer("usera@test.com");
        Long orderAId = createOrder(customerAId);

        PaymentRequest req = new PaymentRequest(orderAId, PaymentMethod.PIX, PaymentStatus.PENDING,
            new BigDecimal("110"), null, null, null, null);
        mockMvc.perform(post("/api/payments/my").with(USER_A)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.orderId").value(orderAId))
            .andExpect(jsonPath("$.method").value("PIX"));
    }

    @Test
    void shouldUserBeBlockedFromCreatingPaymentForOtherUsersOrder() throws Exception {
        Long customerBId = createCustomer("userb@test.com");
        Long orderBId = createOrder(customerBId);

        PaymentRequest req = new PaymentRequest(orderBId, PaymentMethod.PIX, PaymentStatus.PENDING,
            new BigDecimal("110"), null, null, null, null);
        mockMvc.perform(post("/api/payments/my").with(USER_A)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isForbidden());
    }

    @Test
    void shouldUserBeForbiddenFromGlobalPaymentList() throws Exception {
        mockMvc.perform(get("/api/payments").with(USER_A))
            .andExpect(status().isForbidden());
    }

    // -------------------------------------------------------------------------
    // OrderReturn /my tests
    // -------------------------------------------------------------------------

    @Test
    void shouldUserListOwnOrderReturns() throws Exception {
        Long customerAId = createCustomer("usera@test.com");
        Long orderAId = createOrder(customerAId);
        Long returnAId = createOrderReturn(orderAId);

        mockMvc.perform(get("/api/order-returns/my").with(USER_A))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(returnAId));
    }

    @Test
    void shouldUserGetOwnOrderReturnById() throws Exception {
        Long customerAId = createCustomer("usera@test.com");
        Long orderAId = createOrder(customerAId);
        Long returnAId = createOrderReturn(orderAId);

        mockMvc.perform(get("/api/order-returns/my/" + returnAId).with(USER_A))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(returnAId));
    }

    @Test
    void shouldUserCreateOrderReturnForOwnOrder() throws Exception {
        Long customerAId = createCustomer("usera@test.com");
        Long orderAId = createOrder(customerAId);

        OrderReturnRequest req = new OrderReturnRequest(orderAId, "Damaged product",
            ReturnStatus.REQUESTED, ReturnType.REFUND, null);
        mockMvc.perform(post("/api/order-returns/my").with(USER_A)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.orderId").value(orderAId))
            .andExpect(jsonPath("$.reason").value("Damaged product"));
    }

    @Test
    void shouldUserBeBlockedFromCreatingOrderReturnForOtherUsersOrder() throws Exception {
        Long customerBId = createCustomer("userb@test.com");
        Long orderBId = createOrder(customerBId);

        OrderReturnRequest req = new OrderReturnRequest(orderBId, "Wrong item",
            ReturnStatus.REQUESTED, ReturnType.EXCHANGE, null);
        mockMvc.perform(post("/api/order-returns/my").with(USER_A)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isForbidden());
    }

    @Test
    void shouldUserBeForbiddenFromGlobalOrderReturnList() throws Exception {
        mockMvc.perform(get("/api/order-returns").with(USER_A))
            .andExpect(status().isForbidden());
    }
}
