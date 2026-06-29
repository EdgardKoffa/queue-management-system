package com.nsglobal.queue.bankservice.entity;

import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankService extends BaseEntity {
	@Column(nullable = false, unique = true)
	private  String code;
	
	@Column(nullable = false)
	private  String name;
	
	@Column(nullable = false)
	private String prefix;
	
	@Min(0)
	@Column(nullable = false)
	@Builder.Default
	private Integer priority = 0;

	@Positive
	@Column(nullable = false,name="estimated_duration")
	private Integer estimatedDurationMinutes;

	@Builder.Default
	@Column(nullable = false)
	private Boolean active = true;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id",nullable = false)
	private Branch branch;
}
