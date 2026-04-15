package com.marketplace.booking.entity;

import com.marketplace.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "consumer_id", nullable = false)
    private UUID consumerId;

    @Column(name = "provider_id", nullable = false)
    private UUID providerId;

    @Column(name = "listing_id", nullable = false)
    private UUID listingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private BookingStatus status = BookingStatus.PENDING;

    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @Column(name = "total_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency = "USD";

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    protected Booking() {}

    public Booking(UUID consumerId, UUID providerId, UUID listingId,
                   Instant startDate, Instant endDate, BigDecimal totalPrice, String currency) {
        this.consumerId = consumerId;
        this.providerId = providerId;
        this.listingId = listingId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.currency = currency;
    }

    public UUID getId() { return id; }
    public UUID getConsumerId() { return consumerId; }
    public UUID getProviderId() { return providerId; }
    public UUID getListingId() { return listingId; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public Instant getStartDate() { return startDate; }
    public void setStartDate(Instant startDate) { this.startDate = startDate; }
    public Instant getEndDate() { return endDate; }
    public void setEndDate(Instant endDate) { this.endDate = endDate; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public boolean canTransitionTo(BookingStatus newStatus) {
        return switch (this.status) {
            case PENDING -> newStatus == BookingStatus.CONFIRMED || newStatus == BookingStatus.CANCELLED;
            case CONFIRMED -> newStatus == BookingStatus.IN_PROGRESS || newStatus == BookingStatus.CANCELLED;
            case IN_PROGRESS -> newStatus == BookingStatus.COMPLETED || newStatus == BookingStatus.CANCELLED;
            case COMPLETED, CANCELLED -> false;
        };
    }
}