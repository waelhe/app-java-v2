package com.marketplace.listing.service;

import com.marketplace.common.dto.PagedResponse;
import com.marketplace.common.exception.ResourceNotFoundException;
import com.marketplace.listing.entity.Listing;
import com.marketplace.listing.entity.ListingCategory;
import com.marketplace.listing.entity.ListingStatus;
import com.marketplace.listing.entity.PricingModel;
import com.marketplace.listing.repository.ListingCategoryRepository;
import com.marketplace.listing.repository.ListingRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ListingService {

    private final ListingRepository listingRepository;
    private final ListingCategoryRepository categoryRepository;

    public ListingService(ListingRepository listingRepository,
                          ListingCategoryRepository categoryRepository) {
        this.listingRepository = listingRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Listing createListing(UUID providerId, UUID categoryId, String title,
                                  String description, String pricingModel,
                                  BigDecimal basePrice, String currency, String address) {
        ListingCategory category = categoryRepository.findByIdAndDeletedFalse(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category", categoryId));

        Listing listing = new Listing(providerId, category, title, description,
            PricingModel.valueOf(pricingModel), basePrice, currency);
        listing.setAddress(address);
        return listingRepository.save(listing);
    }

    public Listing getListingById(UUID id) {
        return listingRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new ResourceNotFoundException("Listing", id));
    }

    public PagedResponse<Listing> getListingsByProvider(UUID providerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Listing> result = listingRepository.findByProviderIdAndDeletedFalse(providerId, pageable);
        return PagedResponse.from(result);
    }

    public PagedResponse<Listing> getActiveListings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Listing> result = listingRepository.findAllActive(pageable);
        return PagedResponse.from(result);
    }

    public PagedResponse<Listing> searchListings(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Listing> result = listingRepository.searchByKeyword(keyword, pageable);
        return PagedResponse.from(result);
    }

    public PagedResponse<Listing> getListingsByCategory(UUID categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Listing> result = listingRepository.findActiveByCategory(categoryId, pageable);
        return PagedResponse.from(result);
    }

    @Transactional
    public Listing updateListingStatus(UUID listingId, ListingStatus status) {
        Listing listing = getListingById(listingId);
        listing.setStatus(status);
        return listingRepository.save(listing);
    }

    @Transactional
    public void deleteListing(UUID listingId) {
        Listing listing = getListingById(listingId);
        listing.setDeleted(true);
        listingRepository.save(listing);
    }
}