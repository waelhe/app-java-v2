package com.marketplace.review.entity;

import com.marketplace.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "reviewer_id", nullable = false)
    private UUID reviewerId;

    @Column(name = "reviewee_id", nullable = false)
    private UUID revieweeId;

    @Column(name = "booking_id", nullable = false)
    private UUID bookingId;

    @Column(name = "listing_id", nullable = false)
    private UUID listingId;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    protected Review() {}

    public Review(UUID reviewerId, UUID revieweeId, UUID bookingId,
                  UUID listingId, Integer rating, String comment) {
        this.reviewerId = reviewerId;
        this.revieweeId = revieweeId;
        this.bookingId = bookingId;
        this.listingId = listingId;
        this.rating = rating;
        this.comment = comment;
    }

    public UUID getId() { return id; }
    public UUID getReviewerId() { return reviewerId; }
    public UUID getRevieweeId() { return revieweeId; }
    public UUID getBookingId() { return bookingId; }
    public UUID getListingId() { return listingId; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}