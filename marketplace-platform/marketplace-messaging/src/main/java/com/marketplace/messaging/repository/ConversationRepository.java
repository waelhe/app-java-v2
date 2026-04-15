package com.marketplace.messaging.repository;

import com.marketplace.messaging.entity.Conversation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConversationRepository extends JpaRepository<Conversation, UUID> {
    @Query("SELECT c FROM Conversation c WHERE c.deleted = false AND (c.participant1Id = :userId OR c.participant2Id = :userId) ORDER BY c.updatedAt DESC")
    List<Conversation> findByParticipantIdAndDeletedFalse(UUID userId);

    @Query("SELECT c FROM Conversation c WHERE c.deleted = false AND ((c.participant1Id = :user1 AND c.participant2Id = :user2) OR (c.participant1Id = :user2 AND c.participant2Id = :user1))")
    Optional<Conversation> findBetweenUsers(UUID user1, UUID user2);
}