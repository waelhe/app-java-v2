package com.marketplace.notification.service;

import com.marketplace.notification.entity.Notification;
import com.marketplace.notification.entity.NotificationType;
import com.marketplace.notification.repository.NotificationRepository;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Async
    @Transactional
    public void sendNotification(UUID userId, NotificationType type, String title, String body,
                                  UUID referenceId, String referenceType) {
        Notification notification = new Notification(userId, type, title, body);
        notification.setReferenceId(referenceId);
        notification.setReferenceType(referenceType);
        notificationRepository.save(notification);
    }

    @Async
    @Transactional
    public void sendNotification(UUID userId, NotificationType type, String title, String body) {
        notificationRepository.save(new Notification(userId, type, title, body));
    }

    public Page<Notification> getUserNotifications(UUID userId, Pageable pageable) {
        return notificationRepository.findByUserIdAndDeletedFalseOrderByCreatedAtDesc(userId, pageable);
    }

    public Page<Notification> getUnreadNotifications(UUID userId, Pageable pageable) {
        return notificationRepository.findByUserIdAndReadFalseAndDeletedFalseOrderByCreatedAtDesc(userId, pageable);
    }

    public long getUnreadCount(UUID userId) {
        return notificationRepository.countByUserIdAndReadFalseAndDeletedFalse(userId);
    }

    @Transactional
    public void markAsRead(UUID notificationId) {
        notificationRepository.findById(notificationId).ifPresent(n -> n.setRead(true));
    }

    @Transactional
    public void markAllAsRead(UUID userId) {
        notificationRepository.findByUserIdAndReadFalseAndDeletedFalseOrderByCreatedAtDesc(userId, Pageable.unpaged())
            .forEach(n -> n.setRead(true));
    }
}