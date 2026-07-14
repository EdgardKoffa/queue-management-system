package com.nsglobal.queue.role.entity;

import java.util.HashSet;
import java.util.Set;

import com.nsglobal.queue.common.entity.BaseEntity;
import com.nsglobal.queue.common.enums.EnumRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseEntity {
	
	@Column(nullable = false,
            unique = true,
            length = 50)
	@Enumerated(EnumType.STRING)
    private EnumRole name;
	
	@Column
	private String description;
	
	 @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(
	            name = "role_permission",
	            joinColumns = @JoinColumn(name = "role_id"),
	            inverseJoinColumns = @JoinColumn(name = "permission_id")
	    )
	    @Builder.Default
	    private Set<Permission> permissions =
	            new HashSet<>();

}
