package com.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulithic;

/**
 * Main entry point for the Marketplace Platform application.
 *
 * <p>Uses Spring Modulith's {@code @Modulithic} annotation to enable
 * modular monolith architecture with automatic module detection.</p>
 */
@SpringBootApplication
@Modulithic
public class MarketplaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketplaceApplication.class, args);
    }
}