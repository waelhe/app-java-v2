package com.marketplace.admin.service;

import com.marketplace.admin.entity.AdminAction;
import com.marketplace.admin.repository.AdminActionRepository;
import com.marketplace.common.dto.PagedResponse;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminService {

    private final AdminActionRepository actionRepository;

    public AdminService(AdminActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Transactional
    public AdminAction logAction(UUID adminId, String actionType, String targetType, UUID targetId, String details) {
        AdminAction action = new AdminAction(adminId, actionType, targetType, targetId, details);
        return actionRepository.save(action);
    }

    public PagedResponse<AdminAction> getActionsByAdmin(UUID adminId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return PagedResponse.from(actionRepository.findByAdminId(adminId, pageable));
    }

    public PagedResponse<AdminAction> getActionsByTarget(String targetType, UUID targetId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return PagedResponse.from(actionRepository.findByTargetTypeAndTargetId(targetType, targetId, pageable));
    }
}
