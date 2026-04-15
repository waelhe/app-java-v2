package com.marketplace.messaging.controller;

import com.marketplace.common.dto.ApiResponse;
import com.marketplace.messaging.entity.Conversation;
import com.marketplace.messaging.entity.Message;
import com.marketplace.messaging.service.MessagingService;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")
public class MessagingController {

    private final MessagingService messagingService;

    public MessagingController(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @PostMapping("/conversations")
    public ResponseEntity<ApiResponse<Conversation>> getOrCreateConversation(
            @RequestParam UUID participant1Id, @RequestParam UUID participant2Id) {
        return ResponseEntity.ok(ApiResponse.success(
            messagingService.getOrCreateConversation(participant1Id, participant2Id)));
    }

    @GetMapping("/conversations/user/{userId}")
    public ResponseEntity<ApiResponse<List<Conversation>>> getUserConversations(@PathVariable UUID userId) {
        return ResponseEntity.ok(ApiResponse.success(messagingService.getUserConversations(userId)));
    }

    @PostMapping("/conversations/{conversationId}")
    public ResponseEntity<ApiResponse<Message>> sendMessage(
            @PathVariable UUID conversationId,
            @RequestParam UUID senderId,
            @RequestParam String content) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Message sent",
                messagingService.sendMessage(conversationId, senderId, content)));
    }

    @GetMapping("/conversations/{conversationId}")
    public ResponseEntity<ApiResponse<Page<Message>>> getMessages(
            @PathVariable UUID conversationId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(
            messagingService.getConversationMessages(conversationId, pageable)));
    }

    @PatchMapping("/conversations/{conversationId}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(
            @PathVariable UUID conversationId, @RequestParam UUID userId) {
        messagingService.markMessagesAsRead(conversationId, userId);
        return ResponseEntity.ok(ApiResponse.success("Messages marked as read"));
    }
}