-- ============================================================================
-- V008 - Messaging Module: Conversations and Messages tables
-- ============================================================================

CREATE TABLE conversations (
    id              UUID            NOT NULL DEFAULT gen_random_uuid(),
    participant_1_id UUID           NOT NULL,
    participant_2_id UUID           NOT NULL,
    listing_id      UUID,
    is_deleted      BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100),
    version         BIGINT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_conversations PRIMARY KEY (id),
    CONSTRAINT fk_conversations_participant1 FOREIGN KEY (participant_1_id) REFERENCES users(id),
    CONSTRAINT fk_conversations_participant2 FOREIGN KEY (participant_2_id) REFERENCES users(id)
);

CREATE TABLE messages (
    id              UUID            NOT NULL DEFAULT gen_random_uuid(),
    conversation_id UUID            NOT NULL,
    sender_id       UUID            NOT NULL,
    content         TEXT            NOT NULL,
    is_read         BOOLEAN         NOT NULL DEFAULT FALSE,
    is_deleted      BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100),
    version         BIGINT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_messages PRIMARY KEY (id),
    CONSTRAINT fk_messages_conversation FOREIGN KEY (conversation_id) REFERENCES conversations(id),
    CONSTRAINT fk_messages_sender FOREIGN KEY (sender_id) REFERENCES users(id)
);

CREATE INDEX idx_conversations_participant1 ON conversations(participant_1_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_conversations_participant2 ON conversations(participant_2_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_messages_conversation ON messages(conversation_id) WHERE is_deleted = FALSE;
CREATE INDEX idx_messages_sender ON messages(sender_id) WHERE is_deleted = FALSE;