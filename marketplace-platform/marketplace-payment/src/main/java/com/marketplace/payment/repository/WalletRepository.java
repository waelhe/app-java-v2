package com.marketplace.payment.repository;

import com.marketplace.payment.entity.Wallet;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Optional<Wallet> findByUserIdAndDeletedFalse(UUID userId);
    boolean existsByUserIdAndDeletedFalse(UUID userId);
}