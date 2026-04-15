package com.marketplace.listing.entity;

import com.marketplace.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "listing_categories")
public class ListingCategory extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "slug", nullable = false, length = 100, unique = true)
    private String slug;

    @Column(name = "description", length = 500)
    private String description;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "schema_definition", columnDefinition = "jsonb")
    private String schemaDefinition = "{}";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ListingCategory parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<ListingCategory> children = new ArrayList<>();

    protected ListingCategory() {}

    public ListingCategory(String name, String slug, String description) {
        this.name = name;
        this.slug = slug;
        this.description = description;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getSchemaDefinition() { return schemaDefinition; }
    public void setSchemaDefinition(String schemaDefinition) { this.schemaDefinition = schemaDefinition; }
    public ListingCategory getParent() { return parent; }
    public void setParent(ListingCategory parent) { this.parent = parent; }
    public List<ListingCategory> getChildren() { return children; }
    public void setChildren(List<ListingCategory> children) { this.children = children; }
}