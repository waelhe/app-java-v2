package com.marketplace.common.dto;

import java.util.List;
import org.springframework.data.domain.Page;

/**
 * Generic paged response DTO wrapping Spring Data's Page object.
 * Provides consistent pagination metadata across all API endpoints.
 *
 * @param <T> the type of the content items
 */
public record PagedResponse<T>(
    List<T> content,
    int pageNumber,
    int pageSize,
    long totalElements,
    int totalPages,
    boolean first,
    boolean last
) {

    public static <T> PagedResponse<T> from(Page<T> page) {
        return new PagedResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isFirst(),
            page.isLast()
        );
    }
}