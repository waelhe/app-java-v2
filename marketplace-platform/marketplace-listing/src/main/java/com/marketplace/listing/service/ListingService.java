package com.marketplace.listing.service;

import com.marketplace.common.dto.PagedResponse;
import com.marketplace.common.exception.ResourceNotFoundException;
import com.marketplace.listing.dto.CreateListingRequest;
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
    private final ListingDescriptionGenerator descriptionGenerator;

    public ListingService(ListingRepository listingRepository,
                          ListingCategoryRepository categoryRepository,
                          ListingDescriptionGenerator descriptionGenerator) {
        this.listingRepository = listingRepository;
        this.categoryRepository = categoryRepository;
        this.descriptionGenerator = descriptionGenerator;
    }

    @Transactional
    public Listing generateAndSetDescription(UUID listingId) {
        Listing listing = getListingById(listingId);
        String description = descriptionGenerator.generateDescription(listing);
        listing.setDescription(description);
        return listingRepository.save(listing);
    }

    @Transactional
    public Listing createListing(CreateListingRequest request) {
        ListingCategory category = categoryRepository.findByIdAndDeletedFalse(request.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Category", request.getCategoryId()));

        Listing listing = new Listing(request.getProviderId(), category, request.getTitle(),
            request.getDescription(), request.getPricingModel(), request.getBasePrice(), request.getCurrency());
        
        listing.setType(request.getType());
        listing.setAddress(request.getAddress());
        listing.setLatitude(request.getLatitude());
        listing.setLongitude(request.getLongitude());
        listing.setDynamicFields(request.getDynamicFields());
        
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
        listing.softDelete();
        listingRepository.save(listing);
    }
}