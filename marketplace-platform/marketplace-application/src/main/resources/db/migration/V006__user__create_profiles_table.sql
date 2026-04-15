-- ============================================================================
-- V006 - User Module: Provider and Consumer profiles
-- ============================================================================

CREATE TABLE provider_profiles (
    id              UUID            NOT NULL DEFAULT gen_random_uuid(),
    user_id         UUID            NOT NULL,
    display_name    VARCHAR(100)    NOT NULL,
    bio             TEXT,
    specialties     JSONB           DEFAULT '[]',
    license_number  VARCHAR(100),
    is_verified     BOOLEAN         NOT NULL DEFAULT FALSE,
    is_deleted      BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100),
    version         BIGINT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_provider_profiles PRIMARY KEY (id),
    CONSTRAINT fk_provider_profiles_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT uq_provider_profiles_user UNIQUE (user_id)
);

CREATE TABLE consumer_profiles (
    id              UUID            NOT NULL DEFAULT gen_random_uuid(),
    user_id         UUID            NOT NULL,
    display_name    VARCHAR(100)    NOT NULL,
    preferences     JSONB           DEFAULT '{}',
    is_deleted      BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100),
    version         BIGINT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_consumer_profiles PRIMARY KEY (id),
    CONSTRAINT fk_consumer_profiles_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT uq_consumer_profiles_user UNIQUE (user_id)
);

CREATE INDEX idx_provider_profiles_user ON provider_profiles(user_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_consumer_profiles_user ON consumer_profiles(user_id) WHERE is_deleted = FALSE;