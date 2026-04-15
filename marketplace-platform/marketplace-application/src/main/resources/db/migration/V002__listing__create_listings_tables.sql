-- ============================================================================
-- V002 - Listing Module: Listings, Categories, Pricing, Media, Availability
-- ============================================================================

CREATE TABLE listing_categories (
    id              UUID            NOT NULL DEFAULT gen_random_uuid(),
    name            VARCHAR(100)    NOT NULL,
    slug            VARCHAR(100)    NOT NULL,
    description     VARCHAR(500),
    parent_id       UUID,
    is_deleted      BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100),
    version         BIGINT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_listing_categories PRIMARY KEY (id),
    CONSTRAINT uk_listing_categories_slug UNIQUE (slug)
);

CREATE TABLE listings (
    id              UUID            NOT NULL DEFAULT gen_random_uuid(),
    provider_id     UUID            NOT NULL,
    category_id     UUID            NOT NULL,
    title           VARCHAR(255)    NOT NULL,
    description     TEXT,
    pricing_model   VARCHAR(20)     NOT NULL DEFAULT 'FIXED',
    base_price      DECIMAL(12,2)   NOT NULL DEFAULT 0,
    currency        VARCHAR(3)      NOT NULL DEFAULT 'USD',
    status          VARCHAR(20)     NOT NULL DEFAULT 'DRAFT',
    address         VARCHAR(500),
    is_deleted      BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100),
    version         BIGINT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_listings PRIMARY KEY (id),
    CONSTRAINT fk_listings_provider FOREIGN KEY (provider_id) REFERENCES users(id),
    CONSTRAINT fk_listings_category FOREIGN KEY (category_id) REFERENCES listing_categories(id)
);

CREATE TABLE listing_media (
    id              UUID            NOT NULL DEFAULT gen_random_uuid(),
    listing_id      UUID            NOT NULL,
    media_type      VARCHAR(20)     NOT NULL DEFAULT 'IMAGE',
    url             VARCHAR(1000)   NOT NULL,
    display_order   INTEGER         NOT NULL DEFAULT 0,
    is_deleted      BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100),
    version         BIGINT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_listing_media PRIMARY KEY (id),
    CONSTRAINT fk_listing_media_listing FOREIGN KEY (listing_id) REFERENCES listings(id) ON DELETE CASCADE
);

CREATE TABLE listing_availability (
    id              UUID            NOT NULL DEFAULT gen_random_uuid(),
    listing_id      UUID            NOT NULL,
    day_of_week     SMALLINT,
    start_time      TIME,
    end_time        TIME,
    specific_date   DATE,
    is_available    BOOLEAN         NOT NULL DEFAULT TRUE,
    is_deleted      BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100),
    version         BIGINT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_listing_availability PRIMARY KEY (id),
    CONSTRAINT fk_listing_availability_listing FOREIGN KEY (listing_id) REFERENCES listings(id) ON DELETE CASCADE
);

CREATE INDEX idx_listings_provider ON listings(provider_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_listings_category ON listings(category_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_listings_status ON listings(status) WHERE is_deleted = FALSE;
CREATE INDEX idx_listings_pricing ON listings(pricing_model, base_price) WHERE is_deleted = FALSE;
CREATE INDEX idx_listing_media_listing ON listing_media(listing_id);
CREATE INDEX idx_listing_availability_listing ON listing_availability(listing_id);

CREATE INDEX idx_listings_search ON listings
    USING GIN (to_tsvector('english', coalesce(title,'') || ' ' || coalesce(description,'')))
    WHERE is_deleted = FALSE;

INSERT INTO listing_categories (name, slug, description) VALUES
    ('Real Estate', 'real-estate', 'Properties for rent or sale'),
    ('Vehicles', 'vehicles', 'Cars, motorcycles, and other vehicles'),
    ('Services', 'services', 'Professional and personal services'),
    ('Electronics', 'electronics', 'Phones, computers, and gadgets'),
    ('Home and Garden', 'home-garden', 'Furniture, decor, and garden supplies'),
    ('Fashion', 'fashion', 'Clothing, shoes, and accessories'),
    ('Jobs', 'jobs', 'Employment opportunities'),
    ('Events', 'events', 'Event planning and venues'),
    ('Education', 'education', 'Courses, tutoring, and training'),
    ('Health', 'health', 'Health and wellness services');