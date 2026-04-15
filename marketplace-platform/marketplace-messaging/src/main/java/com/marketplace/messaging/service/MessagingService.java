package com.marketplace.messaging.service;

import com.marketplace.common.exception.BusinessException;
import com.marketplace.common.exception.ResourceNotFoundException;
import com.marketplace.messaging.entity.Conversation;
import com.marketplace.messaging.entity.Message;
import com.marketplace.messaging.repository.ConversationRepository;
import com.marketplace.messaging.repository.MessageRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MessagingService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public MessagingService(ConversationRepository conversationRepository,
                            MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional
    public Conversation getOrCreateConversation(UUID participant1Id, UUID participant2Id) {
        return conversationRepository.findBetweenUsers(participant1Id, participant2Id)
            .orElseGet(() -> conversationRepository.save(new Conversation(participant1Id, participant2Id)));
    }

    public List<Conversation> getUserConversations(UUID userId) {
        return conversationRepository.findByParticipantIdAndDeletedFalse(userId);
    }

    public Conversation getConversation(UUID conversationId) {
        return conversationRepository.findById(conversationId)
            .filter(c -> !c.isDeleted())
            .orElseThrow(() -> new ResourceNotFoundException("Conversation", conversationId));
    }

    @Transactional
    public Message sendMessage(UUID conversationId, UUID senderId, String content) {
        Conversation conversation = getConversation(conversationId);
        if (!conversation.hasParticipant(senderId)) {
            throw new BusinessException("User is not a participant in this conversation");
        }
        return messageRepository.save(new Message(conversationId, senderId, content));
    }

    public Page<Message> getConversationMessages(UUID conversationId, Pageable pageable) {
        return messageRepository.findByConversationIdAndDeletedFalseOrderByCreatedAtDesc(conversationId, pageable);
    }

    @Transactional
    public void markMessagesAsRead(UUID conversationId, UUID userId) {
        messageRepository.findByConversationIdAndReadFalseAndDeletedFalse(conversationId)
            .stream()
            .filter(m -> !m.getSenderId().equals(userId))
            .forEach(m -> m.setRead(true));
    }

    public long getUnreadMessageCount(UUID conversationId) {
        return messageRepository.countByConversationIdAndReadFalseAndDeletedFalse(conversationId);
    }
}