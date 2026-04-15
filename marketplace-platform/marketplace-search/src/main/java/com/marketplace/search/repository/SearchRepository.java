package com.marketplace.search.repository;

import com.marketplace.search.dto.SearchCriteria;
import com.marketplace.search.dto.SearchResult;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class SearchRepository {

    private final EntityManager entityManager;

    public SearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<SearchResult> search(SearchCriteria criteria, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT l.id, l.title, l.description, l.provider_id, ");
        sql.append("pp.display_name AS provider_name, l.category_id, ");
        sql.append("lc.name AS category_name, l.price, l.pricing_model, ");
        sql.append("COALESCE(AVG(r.rating), 0) AS avg_rating, l.created_at ");
        sql.append("FROM listings l ");
        sql.append("LEFT JOIN provider_profiles pp ON l.provider_id = pp.user_id AND pp.is_deleted = false ");
        sql.append("LEFT JOIN listing_categories lc ON l.category_id = lc.id AND lc.is_deleted = false ");
        sql.append("LEFT JOIN reviews r ON l.id = r.listing_id AND r.is_deleted = false ");
        sql.append("WHERE l.is_deleted = false AND l.status = 'ACTIVE' ");

        if (criteria.getQuery() != null && !criteria.getQuery().isBlank()) {
            sql.append("AND (to_tsvector('english', l.title || ' ' || COALESCE(l.description, '')) ");
            sql.append("     @@ plainto_tsquery('english', :query)) ");
        }
        if (criteria.getCategoryId() != null) {
            sql.append("AND l.category_id = :categoryId ");
        }
        if (criteria.getProviderId() != null) {
            sql.append("AND l.provider_id = :providerId ");
        }
        if (criteria.getMinPrice() != null) {
            sql.append("AND l.price >= :minPrice ");
        }
        if (criteria.getMaxPrice() != null) {
            sql.append("AND l.price <= :maxPrice ");
        }

        sql.append("GROUP BY l.id, l.title, l.description, l.provider_id, ");
        sql.append("pp.display_name, l.category_id, lc.name, l.price, l.pricing_model, l.created_at ");

        if (criteria.getMinRating() != null) {
            sql.append("HAVING COALESCE(AVG(r.rating), 0) >= :minRating ");
        }

        String sortBy = switch (criteria.getSortBy()) {
            case "price" -> "l.price";
            case "rating" -> "avg_rating";
            case "newest" -> "l.created_at";
            default -> "ts_rank(to_tsvector('english', l.title), plainto_tsquery('english', :query))";
        };
        String direction = "asc".equalsIgnoreCase(criteria.getSortDirection()) ? "ASC" : "DESC";
        sql.append("ORDER BY ").append(sortBy).append(" ").append(direction).append(" ");

        var query = entityManager.createNativeQuery(sql.toString(), "SearchResultMapping");

        if (criteria.getQuery() != null && !criteria.getQuery().isBlank()) {
            query.setParameter("query", criteria.getQuery());
        }
        if (criteria.getCategoryId() != null) {
            query.setParameter("categoryId", criteria.getCategoryId());
        }
        if (criteria.getProviderId() != null) {
            query.setParameter("providerId", criteria.getProviderId());
        }
        if (criteria.getMinPrice() != null) {
            query.setParameter("minPrice", criteria.getMinPrice());
        }
        if (criteria.getMaxPrice() != null) {
            query.setParameter("maxPrice", criteria.getMaxPrice());
        }
        if (criteria.getMinRating() != null) {
            query.setParameter("minRating", criteria.getMinRating());
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked")
        List<SearchResult> results = query.getResultList();
        return new PageImpl<>(results, pageable, results.size());
    }
}