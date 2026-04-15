package com.marketplace.search.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class SearchCriteria {
    private String query;
    private UUID categoryId;
    private UUID providerId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Double minRating;
    private String sortBy = "relevance";
    private String sortDirection = "desc";

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
    public UUID getCategoryId() { return categoryId; }
    public void setCategoryId(UUID categoryId) { this.categoryId = categoryId; }
    public UUID getProviderId() { return providerId; }
    public void setProviderId(UUID providerId) { this.providerId = providerId; }
    public BigDecimal getMinPrice() { return minPrice; }
    public void setMinPrice(BigDecimal minPrice) { this.minPrice = minPrice; }
    public BigDecimal getMaxPrice() { return maxPrice; }
    public void setMaxPrice(BigDecimal maxPrice) { this.maxPrice = maxPrice; }
    public Double getMinRating() { return minRating; }
    public void setMinRating(Double minRating) { this.minRating = minRating; }
    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }
    public String getSortDirection() { return sortDirection; }
    public void setSortDirection(String sortDirection) { this.sortDirection = sortDirection; }
}