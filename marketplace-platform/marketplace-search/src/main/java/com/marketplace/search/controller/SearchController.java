package com.marketplace.search.controller;

import com.marketplace.common.dto.ApiResponse;
import com.marketplace.search.dto.SearchCriteria;
import com.marketplace.search.dto.SearchResult;
import com.marketplace.search.service.SearchService;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<SearchResult>>> search(SearchCriteria criteria, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(searchService.search(criteria, pageable)));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<Page<SearchResult>>> searchByCategory(
            @PathVariable UUID categoryId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(searchService.searchByCategory(categoryId, pageable)));
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<ApiResponse<Page<SearchResult>>> searchByProvider(
            @PathVariable UUID providerId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(searchService.searchByProvider(providerId, pageable)));
    }
}