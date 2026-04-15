package com.marketplace.booking.repository;

import com.marketplace.booking.entity.Booking;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    
    Page<Booking> findByConsumerId(UUID consumerId, Pageable pageable);
    
    Page<Booking> findByProviderId(UUID providerId, Pageable pageable);
}
