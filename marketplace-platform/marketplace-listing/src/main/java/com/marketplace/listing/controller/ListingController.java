package com.marketplace.listing.controller;

import com.marketplace.common.dto.ApiResponse;
import com.marketplace.common.dto.PagedResponse;
import com.marketplace.listing.dto.CreateListingRequest;
import com.marketplace.listing.entity.Listing;
import com.marketplace.listing.entity.ListingStatus;
import com.marketplace.listing.service.ListingService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/listings")
public class ListingController {

    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Listing>> createListing(@RequestBody @Valid CreateListingRequest request) {
        Listing listing = listingService.createListing(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Listing created successfully", listing));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Listing>> getListing(@PathVariable UUID id) {
        Listing listing = listingService.getListingById(id);
        return ResponseEntity.ok(ApiResponse.success(listing));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<Listing>>> getActiveListings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PagedResponse<Listing> listings = listingService.getActiveListings(page, size);
        return ResponseEntity.ok(ApiResponse.success(listings));
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<ApiResponse<PagedResponse<Listing>>> getListingsByProvider(
            @PathVariable UUID providerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PagedResponse<Listing> listings = listingService.getListingsByProvider(providerId, page, size);
        return ResponseEntity.ok(ApiResponse.success(listings));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PagedResponse<Listing>>> searchListings(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PagedResponse<Listing> listings = listingService.searchListings(keyword, page, size);
        return ResponseEntity.ok(ApiResponse.success(listings));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<PagedResponse<Listing>>> getListingsByCategory(
            @PathVariable UUID categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PagedResponse<Listing> listings = listingService.getListingsByCategory(categoryId, page, size);
        return ResponseEntity.ok(ApiResponse.success(listings));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Listing>> updateStatus(
            @PathVariable UUID id,
            @RequestParam ListingStatus status) {
        Listing listing = listingService.updateListingStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Listing status updated", listing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteListing(@PathVariable UUID id) {
        listingService.deleteListing(id);
        return ResponseEntity.ok(ApiResponse.success("Listing deleted successfully"));
    }
}