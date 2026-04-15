package com.marketplace.search.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class SearchResult {
    private UUID id;
    private String title;
    private String description;
    private UUID providerId;
    private String providerName;
    private UUID categoryId;
    private String categoryName;
    private BigDecimal price;
    private String pricingModel;
    private Double averageRating;
    private Instant createdAt;

    public SearchResult() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public UUID getProviderId() { return providerId; }
    public void setProviderId(UUID providerId) { this.providerId = providerId; }
    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }
    public UUID getCategoryId() { return categoryId; }
    public void setCategoryId(UUID categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getPricingModel() { return pricingModel; }
    public void setPricingModel(String pricingModel) { this.pricingModel = pricingModel; }
    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}