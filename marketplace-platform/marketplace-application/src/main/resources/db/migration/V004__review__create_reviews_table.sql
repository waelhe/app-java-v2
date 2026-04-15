-- ============================================================================
-- V004 - Review Module: Reviews table
-- ============================================================================

CREATE TABLE reviews (
    id              UUID            NOT NULL DEFAULT gen_random_uuid(),
    reviewer_id     UUID            NOT NULL,
    reviewee_id     UUID            NOT NULL,
    booking_id      UUID            NOT NULL,
    listing_id      UUID            NOT NULL,
    rating          SMALLINT        NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment         TEXT,
    is_deleted      BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100),
    version         BIGINT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_reviews PRIMARY KEY (id),
    CONSTRAINT fk_reviews_reviewer FOREIGN KEY (reviewer_id) REFERENCES users(id),
    CONSTRAINT fk_reviews_reviewee FOREIGN KEY (reviewee_id) REFERENCES users(id),
    CONSTRAINT fk_reviews_booking FOREIGN KEY (booking_id) REFERENCES bookings(id),
    CONSTRAINT fk_reviews_listing FOREIGN KEY (listing_id) REFERENCES listings(id),
    CONSTRAINT uq_reviews_booking_reviewer UNIQUE (booking_id, reviewer_id)
);

CREATE INDEX idx_reviews_reviewee ON reviews(reviewee_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_reviews_listing ON reviews(listing_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_reviews_rating ON reviews(rating) WHERE is_deleted = FALSE;