-- ============================================================================
-- V011 - Admin Module: Admin Actions
-- ============================================================================

CREATE TABLE admin_actions (
    id              UUID            NOT NULL DEFAULT gen_random_uuid(),
    admin_id        UUID            NOT NULL,
    action_type     VARCHAR(50)     NOT NULL,
    target_type     VARCHAR(50),
    target_id       UUID,
    details         TEXT,
    is_deleted      BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100),
    version         BIGINT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_admin_actions PRIMARY KEY (id),
    CONSTRAINT fk_admin_actions_admin FOREIGN KEY (admin_id) REFERENCES users(id)
);

CREATE INDEX idx_admin_actions_admin ON admin_actions(admin_id);
CREATE INDEX idx_admin_actions_target ON admin_actions(target_type, target_id);
