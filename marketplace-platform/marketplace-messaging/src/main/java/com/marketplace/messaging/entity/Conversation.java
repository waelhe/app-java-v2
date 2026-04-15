package com.marketplace.messaging.entity;

import com.marketplace.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "conversations")
public class Conversation extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "participant_1_id", nullable = false)
    private UUID participant1Id;

    @Column(name = "participant_2_id", nullable = false)
    private UUID participant2Id;

    @Column(name = "listing_id")
    private UUID listingId;

    protected Conversation() {}

    public Conversation(UUID participant1Id, UUID participant2Id) {
        this.participant1Id = participant1Id;
        this.participant2Id = participant2Id;
    }

    public UUID getId() { return id; }
    public UUID getParticipant1Id() { return participant1Id; }
    public UUID getParticipant2Id() { return participant2Id; }
    public UUID getListingId() { return listingId; }
    public void setListingId(UUID listingId) { this.listingId = listingId; }

    public boolean hasParticipant(UUID userId) {
        return participant1Id.equals(userId) || participant2Id.equals(userId);
    }
}