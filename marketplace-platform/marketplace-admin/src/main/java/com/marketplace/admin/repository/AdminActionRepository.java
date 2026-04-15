package com.marketplace.admin.repository;

import com.marketplace.admin.entity.AdminAction;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class AdminActionRepository {

    private final DataManager dataManager;

    public AdminActionRepository(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public AdminAction save(AdminAction adminAction) {
        return dataManager.save(adminAction);
    }

    public List<AdminAction> findByAdminId(UUID adminId) {
        return dataManager.load(AdminAction.class)
                .condition(PropertyCondition.equal("adminId", adminId))
                .list();
    }

    public List<AdminAction> findByTargetTypeAndTargetId(String targetType, UUID targetId) {
        return dataManager.load(AdminAction.class)
                .condition(PropertyCondition.equal("targetType", targetType))
                .condition(PropertyCondition.equal("targetId", targetId))
                .list();
    }

    public List<AdminAction> findAll() {
        return dataManager.load(AdminAction.class).all().list();
    }

    public void delete(AdminAction adminAction) {
        dataManager.remove(adminAction);
    }
}