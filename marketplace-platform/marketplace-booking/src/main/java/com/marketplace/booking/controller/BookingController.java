package com.marketplace.booking.controller;

import com.marketplace.booking.entity.Booking;
import com.marketplace.booking.entity.BookingStatus;
import com.marketplace.booking.service.BookingService;
import com.marketplace.common.dto.ApiResponse;
import com.marketplace.common.dto.PagedResponse;
import java.math.BigDecimal;
import java.time.Instant;
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
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Booking>> createBooking(
            @RequestParam UUID consumerId,
            @RequestParam UUID providerId,
            @RequestParam UUID listingId,
            @RequestParam Instant startDate,
            @RequestParam Instant endDate,
            @RequestParam BigDecimal basePrice,
            @RequestParam String currency,
            @RequestParam(required = false) String notes) {
        Booking booking = bookingService.createBooking(
            consumerId, providerId, listingId, startDate, endDate, basePrice, currency, notes);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Booking created successfully with dynamic pricing", booking));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Booking>> getBooking(@PathVariable UUID id) {
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(ApiResponse.success(booking));
    }

    @GetMapping("/consumer/{consumerId}")
    public ResponseEntity<ApiResponse<PagedResponse<Booking>>> getBookingsByConsumer(
            @PathVariable UUID consumerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PagedResponse<Booking> bookings = bookingService.getBookingsByConsumer(consumerId, page, size);
        return ResponseEntity.ok(ApiResponse.success(bookings));
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<ApiResponse<PagedResponse<Booking>>> getBookingsByProvider(
            @PathVariable UUID providerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PagedResponse<Booking> bookings = bookingService.getBookingsByProvider(providerId, page, size);
        return ResponseEntity.ok(ApiResponse.success(bookings));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Booking>> updateStatus(
            @PathVariable UUID id,
            @RequestParam BookingStatus status) {
        Booking booking = bookingService.updateBookingStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Booking status updated", booking));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelBooking(@PathVariable UUID id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok(ApiResponse.success("Booking cancelled successfully"));
    }
}