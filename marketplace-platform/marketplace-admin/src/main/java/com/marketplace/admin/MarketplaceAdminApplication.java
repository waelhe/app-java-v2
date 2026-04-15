package com.marketplace.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class MarketplaceAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketplaceAdminApplication.class, args);
    }

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        System.out.println("========================================");
        System.out.println("  Marketplace Admin (Jmix FlowUI) Started");
        System.out.println("  http://localhost:8080");
        System.out.println("========================================");
    }
}