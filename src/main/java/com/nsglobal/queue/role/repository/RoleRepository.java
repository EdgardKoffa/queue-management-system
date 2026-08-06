package com.nsglobal.queue.role.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsglobal.queue.common.enums.EnumRole;
import com.nsglobal.queue.role.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(EnumRole name);

	    boolean existsByName(EnumRole name);
}
