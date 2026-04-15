package com.marketplace.admin.entity;

import com.marketplace.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "admin_actions")
public class AdminAction extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "admin_id", nullable = false)
    private UUID adminId;

    @Column(name = "action_type", nullable = false, length = 50)
    private String actionType;

    @Column(name = "target_type", length = 50)
    private String targetType;

    @Column(name = "target_id")
    private UUID targetId;

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    protected AdminAction() {}

    public AdminAction(UUID adminId, String actionType, String targetType, UUID targetId, String details) {
        this.adminId = adminId;
        this.actionType = actionType;
        this.targetType = targetType;
        this.targetId = targetId;
        this.details = details;
    }

    public UUID getId() { return id; }
    public UUID getAdminId() { return adminId; }
    public String getActionType() { return actionType; }
    public String getTargetType() { return targetType; }
    public UUID getTargetId() { return targetId; }
    public String getDetails() { return details; }
}
