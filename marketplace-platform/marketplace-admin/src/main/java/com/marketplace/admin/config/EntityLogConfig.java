package com.marketplace.admin.config;

import io.jmix.audit.entity.LoggedEntity;
import io.jmix.audit.EntityLog;
import io.jmix.core.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * Configures Jmix Entity Log to track all changes on AdminAction entity.
 * This enables audit trail: who changed what and when.
 *
 * Entity Log entries are stored in AUDIT_LOGGED_ATTRS and AUDIT_LOGGED_ENTITIES tables.
 * They can be viewed from the Audit > Entity Log menu in the admin UI.
 */
@Configuration
public class EntityLogConfig {

    private static final Logger log = LoggerFactory.getLogger(EntityLogConfig.class);

    private final DataManager dataManager;

    public EntityLogConfig(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @EventListener
    @Transactional
    public void onApplicationReady(ApplicationReadyEvent event) {
        try {
            // Check if AdminAction is already registered for entity logging
            long count = dataManager.load(LoggedEntity.class)
                    .condition(io.jmix.core.querycondition.PropertyCondition.equal(
                            "name", "admin_AdminAction"))
                    .list()
                    .size();

            if (count == 0) {
                LoggedEntity loggedEntity = dataManager.create(LoggedEntity.class);
                loggedEntity.setName("admin_AdminAction");
                loggedEntity.setAuto(true);
                loggedEntity.setCreate(true);
                loggedEntity.setModify(true);
                loggedEntity.setDelete(true);
                dataManager.save(loggedEntity);
                log.info("Registered AdminAction entity for Entity Log auditing");
            }
        } catch (Exception e) {
            log.warn("Could not register AdminAction for Entity Log: {}", e.getMessage());
        }
    }
}