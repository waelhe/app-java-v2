package com.marketplace.common.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Enables JPA Auditing for automatic population of
 * @CreatedBy and @LastModifiedBy fields.
 *
 * <p>Follows Spring Data JPA's recommended pattern for
 * auditor awareness via AuditorAware.</p>
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // TODO: Integrate with Spring Security to get current authenticated user
        return () -> Optional.of("system");
    }
}