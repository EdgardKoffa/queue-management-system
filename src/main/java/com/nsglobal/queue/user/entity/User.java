package com.nsglobal.queue.user.entity;

import java.time.LocalDate;

import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.common.entity.BaseEntity;
import com.nsglobal.queue.role.entity.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "utilisateur")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
	
	@Column(nullable = false,
			name = "username",
			unique = true,length = 30,comment = "Le nom d'utilisateur peut etre un pseudo ou un numero de telephone")
	private String userName;
	
	@Column(nullable = false,unique = true)
	@Email
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(name = "lastname")
	private String lastName;
	
	@Column(name = "firstname")
	private String firstName;
	
	@Column(unique = true)
	private String phone;
	
	@Column(nullable = false)
	private boolean enabled;
	
	@Column
	private boolean locked;
	
	@Column(name = "lastlogin")
	private LocalDate lastLogin;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id")
	private Branch branch;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Role role;
	
	
}
