package com.marketplace.search.service;

import com.marketplace.search.dto.SearchCriteria;
import com.marketplace.search.dto.SearchResult;
import com.marketplace.search.repository.SearchRepository;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SearchService {

    private final SearchRepository searchRepository;

    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public Page<SearchResult> search(SearchCriteria criteria, Pageable pageable) {
        return searchRepository.search(criteria, pageable);
    }

    public Page<SearchResult> searchByCategory(UUID categoryId, Pageable pageable) {
        SearchCriteria criteria = new SearchCriteria();
        criteria.setCategoryId(categoryId);
        return searchRepository.search(criteria, pageable);
    }

    public Page<SearchResult> searchByProvider(UUID providerId, Pageable pageable) {
        SearchCriteria criteria = new SearchCriteria();
        criteria.setProviderId(providerId);
        return searchRepository.search(criteria, pageable);
    }
}