package com.nsglobal.queue.counter.entity;

import java.time.LocalDateTime;

import com.nsglobal.queue.common.entity.BaseEntity;
import com.nsglobal.queue.common.enums.CounterActions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "counter_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounterHistory extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "counter_id", nullable = false)
	private Counter counter;
	
	@Column(nullable = true)
	private String ipAdress;
	
	@Column(nullable = true)
	private String assigned_operator;
	
	@Column
	private CounterActions action;
	
	@Column
	private LocalDateTime reccordTime;

	@Column(nullable = true)
	private String comment;

}
