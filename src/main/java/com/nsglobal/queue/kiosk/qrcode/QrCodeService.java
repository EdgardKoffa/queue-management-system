package com.nsglobal.queue.kiosk.qrcode;

import com.nsglobal.queue.ticket.dto.TicketResponseDto;

public interface QrCodeService {
	
	 /**
     * Génère le contenu du QRCode.
     */
	  String generateQrCode(TicketResponseDto ticket);
	  
    /**  ou
     * Génère le contenu du QRCode.
     */
    String generateTicketQrContent(Long ticketId);


}
