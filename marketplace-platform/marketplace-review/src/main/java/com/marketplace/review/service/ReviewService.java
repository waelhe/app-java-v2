package com.marketplace.review.service;

import com.marketplace.common.dto.PagedResponse;
import com.marketplace.common.exception.BusinessException;
import com.marketplace.common.exception.ResourceNotFoundException;
import com.marketplace.review.entity.Review;
import com.marketplace.review.repository.ReviewRepository;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public Review createReview(UUID reviewerId, UUID revieweeId, UUID bookingId,
                               UUID listingId, Integer rating, String comment) {
        if (reviewerId.equals(revieweeId)) {
            throw new BusinessException("Users cannot review themselves");
        }
        if (rating < 1 || rating > 5) {
            throw new BusinessException("Rating must be between 1 and 5");
        }
        boolean alreadyReviewed = reviewRepository
            .existsByBookingIdAndReviewerIdAndDeletedFalse(bookingId, reviewerId);
        if (alreadyReviewed) {
            throw new BusinessException("You have already reviewed this booking");
        }
        Review review = new Review(reviewerId, revieweeId, bookingId, listingId, rating, comment);
        return reviewRepository.save(review);
    }

    public Review getReviewById(UUID id) {
        return reviewRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new ResourceNotFoundException("Review", id));
    }

    public PagedResponse<Review> getReviewsByUser(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Review> result = reviewRepository.findByRevieweeIdAndDeletedFalse(userId, pageable);
        return PagedResponse.from(result);
    }

    public PagedResponse<Review> getReviewsByListing(UUID listingId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Review> result = reviewRepository.findByListingIdAndDeletedFalse(listingId, pageable);
        return PagedResponse.from(result);
    }

    public Double getAverageRatingForUser(UUID userId) {
        return reviewRepository.getAverageRatingForUser(userId);
    }

    public Double getAverageRatingForListing(UUID listingId) {
        return reviewRepository.getAverageRatingForListing(listingId);
    }

    @Transactional
    public void deleteReview(UUID id) {
        Review review = getReviewById(id);
        review.setDeleted(true);
        reviewRepository.save(review);
    }
}