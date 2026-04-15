package com.marketplace.payment.repository;

import com.marketplace.payment.entity.Escrow;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscrowRepository extends JpaRepository<Escrow, UUID> {
    Escrow findByPaymentId(UUID paymentId);
}
