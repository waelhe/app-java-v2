package com.marketplace.review.controller;

import com.marketplace.common.dto.ApiResponse;
import com.marketplace.common.dto.PagedResponse;
import com.marketplace.review.entity.Review;
import com.marketplace.review.service.ReviewService;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Review>> createReview(
            @RequestParam UUID reviewerId,
            @RequestParam UUID revieweeId,
            @RequestParam UUID bookingId,
            @RequestParam UUID listingId,
            @RequestParam Integer rating,
            @RequestParam(required = false) String comment) {
        Review review = reviewService.createReview(reviewerId, revieweeId, bookingId, listingId, rating, comment);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Review created successfully", review));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Review>> getReview(@PathVariable UUID id) {
        Review review = reviewService.getReviewById(id);
        return ResponseEntity.ok(ApiResponse.success(review));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<PagedResponse<Review>>> getReviewsByUser(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PagedResponse<Review> reviews = reviewService.getReviewsByUser(userId, page, size);
        return ResponseEntity.ok(ApiResponse.success(reviews));
    }

    @GetMapping("/listing/{listingId}")
    public ResponseEntity<ApiResponse<PagedResponse<Review>>> getReviewsByListing(
            @PathVariable UUID listingId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PagedResponse<Review> reviews = reviewService.getReviewsByListing(listingId, page, size);
        return ResponseEntity.ok(ApiResponse.success(reviews));
    }

    @GetMapping("/user/{userId}/average-rating")
    public ResponseEntity<ApiResponse<Double>> getAverageRatingForUser(@PathVariable UUID userId) {
        Double avg = reviewService.getAverageRatingForUser(userId);
        return ResponseEntity.ok(ApiResponse.success(avg));
    }

    @GetMapping("/listing/{listingId}/average-rating")
    public ResponseEntity<ApiResponse<Double>> getAverageRatingForListing(@PathVariable UUID listingId) {
        Double avg = reviewService.getAverageRatingForListing(listingId);
        return ResponseEntity.ok(ApiResponse.success(avg));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok(ApiResponse.success("Review deleted successfully"));
    }
}