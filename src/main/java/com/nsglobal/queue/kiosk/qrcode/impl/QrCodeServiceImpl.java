package com.nsglobal.queue.kiosk.qrcode.impl;

import org.springframework.stereotype.Service;

import com.nsglobal.queue.kiosk.qrcode.QrCodeService;
import com.nsglobal.queue.ticket.dto.TicketResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QrCodeServiceImpl implements QrCodeService {

	@Override
	public String generateQrCode(TicketResponseDto ticket) {

		 return "QR-" + ticket.getTicketNumber();
	}

	@Override
	public String generateTicketQrContent(Long ticketId) {

		return null;
	}

}
