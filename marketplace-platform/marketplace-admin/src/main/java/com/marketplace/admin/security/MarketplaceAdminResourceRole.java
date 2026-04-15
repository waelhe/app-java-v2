package com.marketplace.admin.security;

import io.jmix.security.model.EntityAttributeOperation;
import io.jmix.security.model.EntityOperation;
import io.jmix.security.role.annotation.EntityAccess;
import io.jmix.security.role.annotation.EntityAttributeAccess;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificAccess;
import io.jmix.security.role.annotation.UiAccess;
import io.jmix.security.role.annotation.MenuAccess;

@ResourceRole(
        name = "Marketplace Administrator",
        code = "marketplace-admin",
        description = "Full access to all admin functionality including CRUD operations on admin actions, " +
                "user management, entity log, and system configuration"
)
public interface MarketplaceAdminResourceRole {

    @EntityAccess(entityClass = com.marketplace.admin.entity.AdminAction.class,
            operations = {EntityOperation.CREATE, EntityOperation.READ, EntityOperation.UPDATE, EntityOperation.DELETE})
    void adminAction();

    @EntityAttributeAccess(entityClass = com.marketplace.admin.entity.AdminAction.class,
            attributes = {"actionType", "adminId", "targetType", "targetId", "details", "createdAt", "updatedAt", "createdBy", "updatedBy"},
            operations = {EntityAttributeOperation.CREATE, EntityAttributeOperation.READ, EntityAttributeOperation.UPDATE})
    void adminActionAttributes();

    @MenuAccess(menuIds = {"admin_AdminAction.list", "admin_AdminAction.detail"})
    void adminActionMenu();

    @UiAccess(uiIds = {"admin_AdminAction.list", "admin_AdminAction.detail"})
    void adminActionViews();

    @SpecificAccess(resources = {"ui.loginToUi", "ui.filter.editConditions", "ui.filter.customConditions"})
    void uiSpecific();
}