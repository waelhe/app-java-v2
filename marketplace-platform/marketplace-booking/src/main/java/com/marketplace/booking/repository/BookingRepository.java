package com.marketplace.booking.repository;

import com.marketplace.booking.entity.Booking;
import com.marketplace.booking.entity.BookingStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    Optional<Booking> findByIdAndDeletedFalse(UUID id);

    Page<Booking> findByConsumerIdAndDeletedFalse(UUID consumerId, Pageable pageable);

    Page<Booking> findByProviderIdAndDeletedFalse(UUID providerId, Pageable pageable);

    Page<Booking> findByListingIdAndDeletedFalse(UUID listingId, Pageable pageable);

    Page<Booking> findByStatusAndDeletedFalse(BookingStatus status, Pageable pageable);

    boolean existsByListingIdAndStatusInAndDeletedFalse(UUID listingId, List<BookingStatus> activeStatuses);
}