package com.nsglobal.queue.display.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Pour un affichage sur écran dans la salle d'attente.
 * */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaittingScreenDto {
	
	private List<DisplayTicketDto> calledTickets;
}
