package com.marketplace.payment.entity;

import com.marketplace.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "escrows")
public class Escrow extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "held_until")
    private LocalDateTime heldUntil;

    @Column(name = "is_released", nullable = false)
    private boolean released = false;

    @Column(name = "released_at")
    private LocalDateTime releasedAt;

    protected Escrow() {}

    public Escrow(Payment payment, BigDecimal amount, LocalDateTime heldUntil) {
        this.payment = payment;
        this.amount = amount;
        this.heldUntil = heldUntil;
    }

    public UUID getId() { return id; }
    public Payment getPayment() { return payment; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getHeldUntil() { return heldUntil; }
    public void setHeldUntil(LocalDateTime heldUntil) { this.heldUntil = heldUntil; }
    public boolean isReleased() { return released; }
    public void setReleased(boolean released) { this.released = released; }
    public LocalDateTime getReleasedAt() { return releasedAt; }
    public void setReleasedAt(LocalDateTime releasedAt) { this.releasedAt = releasedAt; }

    public void release() {
        this.released = true;
        this.releasedAt = LocalDateTime.now();
        this.payment.setStatus(PaymentStatus.RELEASED);
    }
}
