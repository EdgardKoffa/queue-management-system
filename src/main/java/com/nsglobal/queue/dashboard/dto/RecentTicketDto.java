package com.nsglobal.queue.dashboard.dto;


import java.time.LocalDateTime;

import com.nsglobal.queue.common.enums.TicketStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentTicketDto {

    private String number;

    private String service;

    private TicketStatus status;

    private String counter;

    private LocalDateTime createdAt;

}
