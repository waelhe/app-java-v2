package com.marketplace.booking.service;

import com.marketplace.booking.entity.Booking;
import com.marketplace.booking.entity.BookingStatus;
import com.marketplace.booking.repository.BookingRepository;
import com.marketplace.common.dto.PagedResponse;
import com.marketplace.common.exception.BusinessException;
import com.marketplace.common.exception.ResourceNotFoundException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public Booking createBooking(UUID consumerId, UUID providerId, UUID listingId,
                                  Instant startDate, Instant endDate,
                                  BigDecimal totalPrice, String currency, String notes) {
        if (startDate.isAfter(endDate)) {
            throw new BusinessException("Start date must be before end date");
        }

        List<BookingStatus> activeStatuses = List.of(
            BookingStatus.PENDING, BookingStatus.CONFIRMED, BookingStatus.IN_PROGRESS);
        boolean hasActiveBooking = bookingRepository
            .existsByListingIdAndStatusInAndDeletedFalse(listingId, activeStatuses);
        if (hasActiveBooking) {
            throw new BusinessException("Listing already has an active booking for the requested dates");
        }

        Booking booking = new Booking(consumerId, providerId, listingId,
            startDate, endDate, totalPrice, currency);
        booking.setNotes(notes);
        return bookingRepository.save(booking);
    }

    public Booking getBookingById(UUID id) {
        return bookingRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new ResourceNotFoundException("Booking", id));
    }

    public PagedResponse<Booking> getBookingsByConsumer(UUID consumerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Booking> result = bookingRepository.findByConsumerIdAndDeletedFalse(consumerId, pageable);
        return PagedResponse.from(result);
    }

    public PagedResponse<Booking> getBookingsByProvider(UUID providerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Booking> result = bookingRepository.findByProviderIdAndDeletedFalse(providerId, pageable);
        return PagedResponse.from(result);
    }

    @Transactional
    public Booking updateBookingStatus(UUID bookingId, BookingStatus newStatus) {
        Booking booking = getBookingById(bookingId);
        if (!booking.canTransitionTo(newStatus)) {
            throw new BusinessException(
                "Cannot transition booking from " + booking.getStatus() + " to " + newStatus);
        }
        booking.setStatus(newStatus);
        return bookingRepository.save(booking);
    }

    @Transactional
    public void cancelBooking(UUID bookingId) {
        Booking booking = getBookingById(bookingId);
        if (!booking.canTransitionTo(BookingStatus.CANCELLED)) {
            throw new BusinessException("Booking cannot be cancelled in status: " + booking.getStatus());
        }
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}