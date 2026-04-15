package com.marketplace.listing.dto;

import com.marketplace.listing.entity.ListingType;
import com.marketplace.listing.entity.PricingModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import lombok.Data;

@Data
public class CreateListingRequest {

    @NotNull(message = "Provider ID is required")
    private UUID providerId;

    @NotNull(message = "Category ID is required")
    private UUID categoryId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Listing type is required")
    private ListingType type;

    @NotNull(message = "Pricing model is required")
    private PricingModel pricingModel;

    @NotNull(message = "Base price is required")
    @PositiveOrZero(message = "Base price must be positive or zero")
    private BigDecimal basePrice;

    @NotBlank(message = "Currency is required")
    private String currency = "USD";

    private String address;

    private Double latitude;

    private Double longitude;

    private Map<String, Object> dynamicFields;
}
