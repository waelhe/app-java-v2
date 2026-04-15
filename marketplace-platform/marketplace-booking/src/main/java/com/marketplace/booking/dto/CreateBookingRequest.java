package com.marketplace.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateBookingRequest(
    @NotNull(message = "Listing ID is required")
    UUID listingId,

    @NotNull(message = "Consumer ID is required")
    UUID consumerId,

    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Check-in date must be in the present or future")
    LocalDateTime checkIn,

    @NotNull(message = "Check-out date is required")
    @Future(message = "Check-out date must be in the future")
    LocalDateTime checkOut
) {}
