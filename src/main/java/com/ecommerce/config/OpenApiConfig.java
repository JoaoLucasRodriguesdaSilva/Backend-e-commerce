package com.ecommerce.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        OpenAPI openAPI = new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("E-commerce API")
                        .version("0.0.1-SNAPSHOT")
                        .description("REST API for the Backend E-commerce application")
                        .contact(new Contact()
                                .name("João Lucas Rodrigues da Silva")
                                .url("https://github.com/JoaoLucasRodriguesdaSilva/Backend-e-commerce"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .tags(List.of(
                        new Tag().name("Auth"),
                        new Tag().name("Customer"),
                        new Tag().name("Address"),
                        new Tag().name("Session"),
                        new Tag().name("SavedCardToken"),
                        new Tag().name("Category"),
                        new Tag().name("Product"),
                        new Tag().name("Variant"),
                        new Tag().name("ProductImage"),
                        new Tag().name("TechnicalSpecification"),
                        new Tag().name("Cart"),
                        new Tag().name("CartItem"),
                        new Tag().name("Order"),
                        new Tag().name("OrderItem"),
                        new Tag().name("Payment"),
                        new Tag().name("Inventory"),
                        new Tag().name("InventoryMovement"),
                        new Tag().name("Shipment"),
                        new Tag().name("Coupon"),
                        new Tag().name("Promotion"),
                        new Tag().name("Review"),
                        new Tag().name("SupportTicket"),
                        new Tag().name("OrderReturn"),
                        new Tag().name("Warranty")
                ));

        openAPI.addExtension("x-tagGroups", List.of(
                Map.of("name", "Authentication",
                        "tags", List.of("Auth")),
                Map.of("name", "Customer Domain",
                        "tags", List.of("Customer", "Address", "Session", "SavedCardToken")),
                Map.of("name", "Catalog Domain",
                        "tags", List.of("Category", "Product", "Variant", "ProductImage", "TechnicalSpecification")),
                Map.of("name", "Sales Domain",
                        "tags", List.of("Cart", "CartItem", "Order", "OrderItem", "Payment")),
                Map.of("name", "Logistics & Inventory",
                        "tags", List.of("Inventory", "InventoryMovement", "Shipment")),
                Map.of("name", "Marketing Domain",
                        "tags", List.of("Coupon", "Promotion")),
                Map.of("name", "Post-Sales Domain",
                        "tags", List.of("Review", "SupportTicket", "OrderReturn", "Warranty"))
        ));

        return openAPI;
    }
}
