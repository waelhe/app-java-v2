-- ============================================================================
-- V007 - Notification Module: Notifications table
-- ============================================================================

CREATE TABLE notifications (
    id              UUID            NOT NULL DEFAULT gen_random_uuid(),
    user_id         UUID            NOT NULL,
    type            VARCHAR(30)     NOT NULL,
    title           VARCHAR(255)    NOT NULL,
    body            TEXT,
    is_read         BOOLEAN         NOT NULL DEFAULT FALSE,
    reference_id    UUID,
    reference_type  VARCHAR(50),
    is_deleted      BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100),
    version         BIGINT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_notifications PRIMARY KEY (id),
    CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_notifications_user ON notifications(user_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_notifications_user_unread ON notifications(user_id, is_read) WHERE is_deleted = FALSE AND is_read = FALSE;