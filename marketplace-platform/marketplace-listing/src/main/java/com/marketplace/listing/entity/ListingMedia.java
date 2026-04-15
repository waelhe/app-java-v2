package com.marketplace.listing.entity;

import com.marketplace.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "listing_media")
public class ListingMedia extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;

    @Column(name = "media_type", nullable = false, length = 20)
    private String mediaType = "IMAGE";

    @Column(name = "url", nullable = false, length = 1000)
    private String url;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    protected ListingMedia() {}

    public ListingMedia(Listing listing, String mediaType, String url, Integer displayOrder) {
        this.listing = listing;
        this.mediaType = mediaType;
        this.url = url;
        this.displayOrder = displayOrder;
    }

    public UUID getId() { return id; }
    public Listing getListing() { return listing; }
    public void setListing(Listing listing) { this.listing = listing; }
    public String getMediaType() { return mediaType; }
    public void setMediaType(String mediaType) { this.mediaType = mediaType; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}