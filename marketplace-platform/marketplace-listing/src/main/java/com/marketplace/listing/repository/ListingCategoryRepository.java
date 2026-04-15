package com.marketplace.listing.repository;

import com.marketplace.listing.entity.ListingCategory;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingCategoryRepository extends JpaRepository<ListingCategory, UUID> {

    Optional<ListingCategory> findBySlugAndDeletedFalse(String slug);

    Optional<ListingCategory> findByIdAndDeletedFalse(UUID id);
}