package com.nsglobal.queue.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsglobal.queue.role.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
