package com.nsglobal.queue.ticket.entity;

import com.nsglobal.queue.common.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ticket_history")
@Getter
@Setter
@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class TicketHistory extends BaseEntity {

}
