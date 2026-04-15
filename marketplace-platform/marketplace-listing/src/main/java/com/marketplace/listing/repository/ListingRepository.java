package com.marketplace.listing.repository;

import com.marketplace.listing.entity.Listing;
import com.marketplace.listing.entity.ListingStatus;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListingRepository extends JpaRepository<Listing, UUID> {

    Optional<Listing> findByIdAndDeletedFalse(UUID id);

    Page<Listing> findByProviderIdAndDeletedFalse(UUID providerId, Pageable pageable);

    Page<Listing> findByStatusAndDeletedFalse(ListingStatus status, Pageable pageable);

    @Query("SELECT l FROM Listing l WHERE l.deleted = false AND l.category.id = :categoryId AND l.status = 'ACTIVE'")
    Page<Listing> findActiveByCategory(@Param("categoryId") UUID categoryId, Pageable pageable);

    @Query("SELECT l FROM Listing l WHERE l.deleted = false AND (LOWER(l.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(l.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Listing> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT l FROM Listing l WHERE l.deleted = false AND l.status = 'ACTIVE'")
    Page<Listing> findAllActive(Pageable pageable);
}