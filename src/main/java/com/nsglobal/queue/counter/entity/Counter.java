
package com.nsglobal.queue.counter.entity;

import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.common.entity.BaseEntity;
import com.nsglobal.queue.common.enums.CounterStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * cette entite represente un guichet. c'est au guichets qu'on counte les numero
 */
@Entity
@Table(name = "counter")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Counter extends BaseEntity {
	
	    @Column(nullable = false, unique = true)
	    private String code;

	    @Positive
	    @Column(nullable = false)
	    private Integer number;

	    @Column(nullable = false)
	    private String name;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private CounterStatus status;

	    @Builder.Default
	    @Column(nullable = false)
	    private Boolean active = true;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "branch_id", nullable = false)
	    private Branch branch;
	
	    /**
	     * champs supplementaire conseillee dans les traitement plus professionnel
	     * */
	    //private String ipAddress;
	    
	    //@OneToOne
	   // private Employee employee;
	    
	   // @OneToOne
	   // private Ticket currentTicket;


}
