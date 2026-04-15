package com.marketplace.user.repository;

import com.marketplace.user.entity.ConsumerProfile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerProfileRepository extends JpaRepository<ConsumerProfile, UUID> {
    Optional<ConsumerProfile> findByUserIdAndDeletedFalse(UUID userId);
    boolean existsByUserIdAndDeletedFalse(UUID userId);
}