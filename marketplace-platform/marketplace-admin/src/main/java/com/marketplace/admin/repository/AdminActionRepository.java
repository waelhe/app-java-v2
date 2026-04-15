package com.marketplace.admin.repository;

import com.marketplace.admin.entity.AdminAction;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminActionRepository extends JpaRepository<AdminAction, UUID> {
    Page<AdminAction> findByAdminId(UUID adminId, Pageable pageable);
    Page<AdminAction> findByTargetTypeAndTargetId(String targetType, UUID targetId, Pageable pageable);
}
