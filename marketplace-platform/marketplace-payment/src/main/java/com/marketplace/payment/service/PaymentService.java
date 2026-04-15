package com.marketplace.payment.service;

import com.marketplace.common.exception.BusinessException;
import com.marketplace.common.exception.ResourceNotFoundException;
import com.marketplace.payment.entity.Payment;
import com.marketplace.payment.entity.PaymentStatus;
import com.marketplace.payment.entity.Wallet;
import com.marketplace.payment.repository.PaymentRepository;
import com.marketplace.payment.repository.WalletRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final WalletRepository walletRepository;

    public PaymentService(PaymentRepository paymentRepository, WalletRepository walletRepository) {
        this.paymentRepository = paymentRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Payment createPayment(UUID bookingId, UUID payerId, UUID payeeId,
                                  BigDecimal amount, String currency) {
        if (paymentRepository.existsByBookingIdAndStatusAndDeletedFalse(bookingId, PaymentStatus.COMPLETED)) {
            throw new BusinessException("Payment already completed for this booking");
        }
        Payment payment = new Payment(bookingId, payerId, payeeId, amount, currency);
        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(UUID id) {
        return paymentRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new ResourceNotFoundException("Payment", id));
    }

    public Payment getPaymentByBooking(UUID bookingId) {
        return paymentRepository.findByBookingIdAndDeletedFalse(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Payment for booking", bookingId));
    }

    @Transactional
    public Payment processPayment(UUID paymentId) {
        Payment payment = getPaymentById(paymentId);
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new BusinessException("Payment is not in PENDING status");
        }
        payment.setStatus(PaymentStatus.PROCESSING);
        paymentRepository.save(payment);

        // Simulate gateway processing
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setGatewayName("INTERNAL");
        payment.setGatewayRef("PAY-" + paymentId.toString().substring(0, 8));

        // Update wallets
        Wallet payerWallet = getOrCreateWallet(payment.getPayerId(), payment.getCurrency());
        Wallet payeeWallet = getOrCreateWallet(payment.getPayeeId(), payment.getCurrency());
        payerWallet.withdraw(payment.getAmount());
        payeeWallet.deposit(payment.getAmount());

        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment refundPayment(UUID paymentId) {
        Payment payment = getPaymentById(paymentId);
        if (payment.getStatus() != PaymentStatus.COMPLETED) {
            throw new BusinessException("Only completed payments can be refunded");
        }

        Wallet payerWallet = getOrCreateWallet(payment.getPayerId(), payment.getCurrency());
        Wallet payeeWallet = getOrCreateWallet(payment.getPayeeId(), payment.getCurrency());
        payeeWallet.withdraw(payment.getAmount());
        payerWallet.deposit(payment.getAmount());

        payment.setStatus(PaymentStatus.REFUNDED);
        return paymentRepository.save(payment);
    }

    @Transactional
    public Wallet getOrCreateWallet(UUID userId, String currency) {
        return walletRepository.findByUserIdAndDeletedFalse(userId)
            .orElseGet(() -> walletRepository.save(new Wallet(userId, currency)));
    }

    public Wallet getWalletByUserId(UUID userId) {
        return walletRepository.findByUserIdAndDeletedFalse(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Wallet for user", userId));
    }
}