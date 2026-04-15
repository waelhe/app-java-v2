package com.marketplace.payment.service;

import com.marketplace.common.exception.ResourceNotFoundException;
import com.marketplace.payment.entity.Escrow;
import com.marketplace.payment.entity.Payment;
import com.marketplace.payment.entity.PaymentStatus;
import com.marketplace.payment.repository.EscrowRepository;
import com.marketplace.payment.repository.PaymentRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EscrowService {

    private final EscrowRepository escrowRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public Escrow holdInEscrow(UUID paymentId, LocalDateTime heldUntil) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        payment.setStatus(PaymentStatus.HELD_IN_ESCROW);
        paymentRepository.save(payment);

        Escrow escrow = new Escrow(payment, payment.getAmount(), heldUntil);
        return escrowRepository.save(escrow);
    }

    @Transactional
    public void releaseFromEscrow(UUID paymentId) {
        Escrow escrow = escrowRepository.findByPaymentId(paymentId);
        if (escrow == null) {
            throw new ResourceNotFoundException("Escrow not found for payment ID: " + paymentId);
        }

        escrow.release();
        escrowRepository.save(escrow);
    }
}
