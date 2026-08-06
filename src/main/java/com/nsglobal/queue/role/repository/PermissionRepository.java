package com.nsglobal.queue.role.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsglobal.queue.common.enums.EnumPermissions;
import com.nsglobal.queue.role.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
	Optional<Permission> findByName(String name);

    boolean existsByName(EnumPermissions name);

}
