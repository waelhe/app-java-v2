package com.marketplace.user.repository;

import com.marketplace.user.entity.ProviderProfile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderProfileRepository extends JpaRepository<ProviderProfile, UUID> {
    Optional<ProviderProfile> findByUserIdAndDeletedFalse(UUID userId);
    boolean existsByUserIdAndDeletedFalse(UUID userId);
}