package com.nsglobal.queue.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.notification.dto.EmailRequestDto;
import com.nsglobal.queue.notification.dto.NotificationResponseDto;
import com.nsglobal.queue.notification.dto.SmsRequestDto;
import com.nsglobal.queue.notification.service.NotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.API_V1+"/notifications")
public class NotificationController {
	
	private final NotificationService notificationService;

    @PostMapping("/sms")
    public ResponseEntity<NotificationResponseDto> sendSms(
            @Valid @RequestBody SmsRequestDto request) {

        NotificationResponseDto response =
                notificationService.sendSms(
                        request.getPhoneNumber(),
                        request.getMessage());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/email")
    public ResponseEntity<NotificationResponseDto> sendEmail(
            @Valid @RequestBody EmailRequestDto request) {

        NotificationResponseDto response =
                notificationService.sendEmail(
                        request.getEmail(),
                        request.getSubject(),
                        request.getMessage());

        return ResponseEntity.ok(response);
    }


}
