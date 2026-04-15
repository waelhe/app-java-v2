package com.marketplace.payment.repository;

import com.marketplace.payment.entity.Payment;
import com.marketplace.payment.entity.PaymentStatus;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByIdAndDeletedFalse(UUID id);
    Page<Payment> findByPayerIdAndDeletedFalse(UUID payerId, Pageable pageable);
    Page<Payment> findByPayeeIdAndDeletedFalse(UUID payeeId, Pageable pageable);
    Optional<Payment> findByBookingIdAndDeletedFalse(UUID bookingId);
    boolean existsByBookingIdAndStatusAndDeletedFalse(UUID bookingId, PaymentStatus status);
}