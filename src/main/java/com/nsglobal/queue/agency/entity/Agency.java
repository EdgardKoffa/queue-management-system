package com.nsglobal.queue.agency.entity;

import com.nsglobal.queue.common.entity.BaseEntity;
import com.nsglobal.queue.common.enums.EnumStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "agency")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agency extends BaseEntity {
	
	@Column(nullable = false, unique = true)
	private  String code;
	
	@Column(nullable = false)
	private  String name;
	
	 @Column(length = 20)
	private  String phone;
	 
	 @Column(unique = true)
	private  String email;
	
	 @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	private  EnumStatus status;
}
