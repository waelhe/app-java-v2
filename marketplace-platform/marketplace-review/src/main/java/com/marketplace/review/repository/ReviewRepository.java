package com.marketplace.review.repository;

import com.marketplace.review.entity.Review;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    Optional<Review> findByIdAndDeletedFalse(UUID id);

    Page<Review> findByRevieweeIdAndDeletedFalse(UUID revieweeId, Pageable pageable);

    Page<Review> findByListingIdAndDeletedFalse(UUID listingId, Pageable pageable);

    boolean existsByBookingIdAndReviewerIdAndDeletedFalse(UUID bookingId, UUID reviewerId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.revieweeId = :userId AND r.deleted = false")
    Double getAverageRatingForUser(@Param("userId") UUID userId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.listingId = :listingId AND r.deleted = false")
    Double getAverageRatingForListing(@Param("listingId") UUID listingId);

    long countByRevieweeIdAndDeletedFalse(UUID revieweeId);
}