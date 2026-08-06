package com.nsglobal.queue.kiosk.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.bankservice.dto.BankServiceResponseDto;
import com.nsglobal.queue.bankservice.service.BankServiceService;
import com.nsglobal.queue.branch.dto.BranchResponseDto;
import com.nsglobal.queue.branch.service.BranchService;
import com.nsglobal.queue.kiosk.dto.TicketGenerationRequestDto;
import com.nsglobal.queue.kiosk.dto.TicketGenerationResponseDto;
import com.nsglobal.queue.kiosk.qrcode.QrCodeService;
import com.nsglobal.queue.kiosk.service.KioskService;
import com.nsglobal.queue.kiosk.service.WaitingTimeEstimator;
import com.nsglobal.queue.notification.service.NotificationService;
import com.nsglobal.queue.ticket.dto.TicketRequestDto;
import com.nsglobal.queue.ticket.dto.TicketResponseDto;
import com.nsglobal.queue.ticket.service.TicketService;
import com.nsglobal.queue.websocket.service.QueueNotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class KioskServiceImpl implements KioskService {
	
	private final TicketService ticketService;

    private final BranchService branchService;

    private final BankServiceService bankServiceService;

    private final WaitingTimeEstimator waitingTimeEstimator;
    
    private final QrCodeService qrCodeService;
    
    private final QueueNotificationService websocket;
    
   // private final NotificationService notification;
	
	private TicketRequestDto buildTicketRequest(
	        TicketGenerationRequestDto request) {

		TicketRequestDto dto =
	            new TicketRequestDto();

	    dto.setBranchId(request.getBranchId());

	    dto.setServiceId(request.getServiceId());

	    dto.setPriority(request.getPriority());

	    dto.setPhone(request.getPhone());

	    dto.setLanguage(request.getLanguage());
	    

	    return dto;

	}
	
	private TicketGenerationResponseDto buildResponse(
	        TicketResponseDto ticket,
	        BranchResponseDto branch,
	        BankServiceResponseDto service,
	        Integer estimatedTime,
	        String qrCode) {

	    TicketGenerationResponseDto response =
	            new TicketGenerationResponseDto();

	    response.setTicketId(ticket.getId());

	    response.setTicketNumber(ticket.getTicketNumber());

	    response.setBranch(branch.getName());

	    response.setService(service.getName());

	    response.setEstimatedWaitingTime(estimatedTime);

	    response.setQrCode(qrCode);

	    response.setIssueTime(ticket.getIssueTime());

	    return response;

	}
	
	@Override
	public TicketGenerationResponseDto generateTicket(TicketGenerationRequestDto request) {
		
		   // 1. Vérifier que l'agence existe
	    BranchResponseDto branch =
	            branchService.findById(request.getBranchId()).getData();

	    // 2. Vérifier que le service existe
	    BankServiceResponseDto service =
	             bankServiceService.findById(request.getServiceId());

	     // 3. Préparer la demande destinée au TicketService
	     TicketRequestDto ticketRequest =
	             buildTicketRequest(request);
	     
	     // 4. Créer le ticket
	     TicketResponseDto ticket =
	             ticketService.create(ticketRequest);
	     
	     
	     // 5. Calcul du temps d'attente
	     Integer estimatedTime =
	             waitingTimeEstimator.estimate(
	                     request.getBranchId(),
	                     request.getServiceId());

	     // 6. Génération du QR Code
	     String qrCode =
	             qrCodeService.generateQrCode(ticket);
	     
	     websocket.publishKiosk(ticket);

	     // 7. Construire la réponse
	     return buildResponse(
	             ticket,
	             branch,
	             service,
	             estimatedTime,
	             qrCode);

	}

}
