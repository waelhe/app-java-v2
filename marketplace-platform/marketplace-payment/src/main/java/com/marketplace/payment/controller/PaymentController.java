package com.marketplace.payment.controller;

import com.marketplace.common.dto.ApiResponse;
import com.marketplace.payment.entity.Payment;
import com.marketplace.payment.entity.Wallet;
import com.marketplace.payment.service.PaymentService;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Payment>> createPayment(
            @RequestParam UUID bookingId,
            @RequestParam UUID payerId,
            @RequestParam UUID payeeId,
            @RequestParam BigDecimal amount,
            @RequestParam String currency) {
        Payment payment = paymentService.createPayment(bookingId, payerId, payeeId, amount, currency);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Payment created", payment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Payment>> getPayment(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.getPaymentById(id)));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<ApiResponse<Payment>> getPaymentByBooking(@PathVariable UUID bookingId) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.getPaymentByBooking(bookingId)));
    }

    @PatchMapping("/{id}/process")
    public ResponseEntity<ApiResponse<Payment>> processPayment(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Payment processed", paymentService.processPayment(id)));
    }

    @PatchMapping("/{id}/refund")
    public ResponseEntity<ApiResponse<Payment>> refundPayment(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Payment refunded", paymentService.refundPayment(id)));
    }

    @GetMapping("/wallet/{userId}")
    public ResponseEntity<ApiResponse<Wallet>> getWallet(@PathVariable UUID userId) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.getWalletByUserId(userId)));
    }
}