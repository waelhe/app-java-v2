-- ============================================================================
-- V003 - Booking Module: Bookings table
-- ============================================================================

CREATE TABLE bookings (
    id              UUID            NOT NULL DEFAULT gen_random_uuid(),
    consumer_id     UUID            NOT NULL,
    provider_id     UUID            NOT NULL,
    listing_id      UUID            NOT NULL,
    status          VARCHAR(20)     NOT NULL DEFAULT 'PENDING',
    start_date      TIMESTAMPTZ     NOT NULL,
    end_date        TIMESTAMPTZ     NOT NULL,
    total_price     DECIMAL(12,2)   NOT NULL DEFAULT 0,
    currency        VARCHAR(3)      NOT NULL DEFAULT 'USD',
    notes           TEXT,
    is_deleted      BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100),
    version         BIGINT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_bookings PRIMARY KEY (id),
    CONSTRAINT fk_bookings_consumer FOREIGN KEY (consumer_id) REFERENCES users(id),
    CONSTRAINT fk_bookings_provider FOREIGN KEY (provider_id) REFERENCES users(id),
    CONSTRAINT fk_bookings_listing FOREIGN KEY (listing_id) REFERENCES listings(id)
);

CREATE INDEX idx_bookings_consumer ON bookings(consumer_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_bookings_provider ON bookings(provider_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_bookings_listing ON bookings(listing_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_bookings_status ON bookings(status) WHERE is_deleted = FALSE;
CREATE INDEX idx_bookings_dates ON bookings(start_date, end_date) WHERE is_deleted = FALSE;