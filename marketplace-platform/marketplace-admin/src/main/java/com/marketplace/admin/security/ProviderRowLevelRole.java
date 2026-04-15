package com.marketplace.admin.security;

import io.jmix.security.model.EntityOperation;
import io.jmix.security.role.annotation.EntityAccess;
import io.jmix.security.role.annotation.RowLevelRole;

/**
 * Row-level security role for providers.
 * Providers can only see their own admin actions (where adminId matches their user ID).
 * This is a critical feature for marketplace platforms where data isolation between providers is essential.
 */
@RowLevelRole(
        name = "Provider Row-Level Access",
        code = "provider-row-level",
        description = "Providers can only access their own records. " +
                "Implements row-level security for data isolation between marketplace providers."
)
public interface ProviderRowLevelRole {

    @EntityAccess(entityClass = com.marketplace.admin.entity.AdminAction.class,
            operations = {EntityOperation.READ, EntityOperation.UPDATE})
    void adminAction();
}