package com.marketplace.user.entity;

import com.marketplace.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "provider_profiles")
public class ProviderProfile extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private ProviderType type = ProviderType.SELLER;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "specialties", columnDefinition = "jsonb")
    private String specialties = "[]";

    @Column(name = "license_number", length = 100)
    private String licenseNumber;

    @Column(name = "is_verified", nullable = false)
    private boolean verified = false;

    protected ProviderProfile() {}

    public ProviderProfile(UUID userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public ProviderType getType() { return type; }
    public void setType(ProviderType type) { this.type = type; }
    public String getSpecialties() { return specialties; }
    public void setSpecialties(String specialties) { this.specialties = specialties; }
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
}