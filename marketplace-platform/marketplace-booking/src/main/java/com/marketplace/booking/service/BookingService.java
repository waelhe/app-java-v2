package com.marketplace.booking.service;

import com.marketplace.booking.entity.Booking;
import com.marketplace.booking.entity.BookingStatus;
import com.marketplace.booking.repository.BookingRepository;
import com.marketplace.common.dto.PagedResponse;
import com.marketplace.common.exception.ResourceNotFoundException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
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
                                Instant startDate, Instant endDate, BigDecimal totalPrice, 
                                String currency, String notes) {
        // Here you would normally check for overlapping bookings (RAG/Airbnb Logic)
        Booking booking = new Booking(consumerId, providerId, listingId, startDate, endDate, totalPrice, currency, notes);
        return bookingRepository.save(booking);
    }

    public Booking getBookingById(UUID id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", id));
    }

    public PagedResponse<Booking> getBookingsByConsumer(UUID consumerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return PagedResponse.from(bookingRepository.findByConsumerId(consumerId, pageable));
    }

    public PagedResponse<Booking> getBookingsByProvider(UUID providerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return PagedResponse.from(bookingRepository.findByProviderId(providerId, pageable));
    }

    @Transactional
    public Booking updateBookingStatus(UUID id, BookingStatus status) {
        Booking booking = getBookingById(id);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    @Transactional
    public void cancelBooking(UUID id) {
        Booking booking = getBookingById(id);
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}
