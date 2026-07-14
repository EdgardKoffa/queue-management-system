package com.nsglobal.queue.display.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/*
 * Pour un affichage sur écran de guichet.
 * */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounterDisplayDto {
 private Long counterId;
 private String counterName;
 private DisplayTicketDto ticketDto;
}
