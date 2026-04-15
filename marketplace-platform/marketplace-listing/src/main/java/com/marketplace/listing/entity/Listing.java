package com.marketplace.listing.entity;

import com.marketplace.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "listings")
public class Listing extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "provider_id", nullable = false)
    private UUID providerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private ListingCategory category;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private ListingType type = ListingType.PRODUCT;

    @Enumerated(EnumType.STRING)
    @Column(name = "pricing_model", nullable = false, length = 20)
    private PricingModel pricingModel = PricingModel.FIXED;

    @Column(name = "base_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice = BigDecimal.ZERO;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency = "USD";

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ListingStatus status = ListingStatus.DRAFT;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dynamic_fields", columnDefinition = "jsonb")
    private String dynamicFields = "{}";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "listing")
    private List<ListingMedia> media = new ArrayList<>();

    protected Listing() {}

    public Listing(UUID providerId, ListingCategory category, String title, String description,
                   PricingModel pricingModel, BigDecimal basePrice, String currency) {
        this.providerId = providerId;
        this.category = category;
        this.title = title;
        this.description = description;
        this.pricingModel = pricingModel;
        this.basePrice = basePrice;
        this.currency = currency;
    }

    public UUID getId() { return id; }
    public UUID getProviderId() { return providerId; }
    public void setProviderId(UUID providerId) { this.providerId = providerId; }
    public ListingCategory getCategory() { return category; }
    public void setCategory(ListingCategory category) { this.category = category; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public ListingType getType() { return type; }
    public void setType(ListingType type) { this.type = type; }
    public PricingModel getPricingModel() { return pricingModel; }
    public void setPricingModel(PricingModel pricingModel) { this.pricingModel = pricingModel; }
    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public ListingStatus getStatus() { return status; }
    public void setStatus(ListingStatus status) { this.status = status; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public String getDynamicFields() { return dynamicFields; }
    public void setDynamicFields(String dynamicFields) { this.dynamicFields = dynamicFields; }
    public List<ListingMedia> getMedia() { return media; }
    public void setMedia(List<ListingMedia> media) { this.media = media; }

    public void addMedia(ListingMedia mediaItem) {
        mediaItem.setListing(this);
        this.media.add(mediaItem);
    }
}