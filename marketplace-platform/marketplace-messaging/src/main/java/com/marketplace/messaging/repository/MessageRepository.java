package com.marketplace.messaging.repository;

import com.marketplace.messaging.entity.Message;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    Page<Message> findByConversationIdAndDeletedFalseOrderByCreatedAtDesc(UUID conversationId, Pageable pageable);
    List<Message> findByConversationIdAndReadFalseAndDeletedFalse(UUID conversationId);
    long countByConversationIdAndReadFalseAndDeletedFalse(UUID conversationId);
}