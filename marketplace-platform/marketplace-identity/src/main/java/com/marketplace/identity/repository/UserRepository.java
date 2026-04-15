package com.marketplace.identity.repository;

import com.marketplace.identity.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmailAndDeletedFalse(String email);

    Optional<User> findByIdAndDeletedFalse(UUID id);

    boolean existsByEmailAndDeletedFalse(String email);

    @Query("SELECT u FROM User u WHERE u.deleted = false AND u.status = 'ACTIVE'")
    Page<User> findAllActive(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.deleted = false")
    Page<User> findAllNotDeleted(Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.deleted = false AND r.name = :roleName")
    Page<User> findByRoleName(@Param("roleName") String roleName, Pageable pageable);
}