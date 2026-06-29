package com.nsglobal.queue.branch.entity;


import com.nsglobal.queue.agency.entity.Agency;
import com.nsglobal.queue.common.entity.BaseEntity;
import com.nsglobal.queue.common.enums.EnumStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "branch")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch extends BaseEntity {
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
	 
	 @Column(nullable = false)
	 private String city;
	 
	 @Column(nullable = false)
	 private String address;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "agency_id", nullable = false)
	 private Agency agency;
}
