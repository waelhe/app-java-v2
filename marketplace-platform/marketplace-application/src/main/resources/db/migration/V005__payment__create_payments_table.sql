-- ============================================================================
-- V005 - Payment Module: Payments and Wallets tables
-- ============================================================================

CREATE TABLE wallets (
    id              UUID            NOT NULL DEFAULT gen_random_uuid(),
    user_id         UUID            NOT NULL,
    balance         DECIMAL(12,2)   NOT NULL DEFAULT 0,
    currency        VARCHAR(3)      NOT NULL DEFAULT 'USD',
    is_deleted      BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100),
    version         BIGINT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_wallets PRIMARY KEY (id),
    CONSTRAINT fk_wallets_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT uq_wallets_user UNIQUE (user_id)
);

CREATE TABLE payments (
    id              UUID            NOT NULL DEFAULT gen_random_uuid(),
    booking_id      UUID            NOT NULL,
    payer_id        UUID            NOT NULL,
    payee_id        UUID            NOT NULL,
    amount          DECIMAL(12,2)   NOT NULL,
    currency        VARCHAR(3)      NOT NULL DEFAULT 'USD',
    status          VARCHAR(20)     NOT NULL DEFAULT 'PENDING',
    gateway_ref     VARCHAR(255),
    gateway_name    VARCHAR(50),
    is_deleted      BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100),
    version         BIGINT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_payments PRIMARY KEY (id),
    CONSTRAINT fk_payments_booking FOREIGN KEY (booking_id) REFERENCES bookings(id),
    CONSTRAINT fk_payments_payer FOREIGN KEY (payer_id) REFERENCES users(id),
    CONSTRAINT fk_payments_payee FOREIGN KEY (payee_id) REFERENCES users(id)
);

CREATE INDEX idx_payments_booking ON payments(booking_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_payments_payer ON payments(payer_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_payments_payee ON payments(payee_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_payments_status ON payments(status) WHERE is_deleted = FALSE;