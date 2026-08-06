package com.nsglobal.queue.role.entity;

import java.util.HashSet;
import java.util.Set;

import com.nsglobal.queue.common.entity.BaseEntity;
import com.nsglobal.queue.common.enums.EnumPermissions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permission")

public class Permission extends BaseEntity {
	
	@Column(nullable = false, unique = true, length = 100)
	@Enumerated(EnumType.STRING)
    private EnumPermissions name;

    @Column(length = 300)
    private String description;

    @ManyToMany(mappedBy = "permissions")
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
}
