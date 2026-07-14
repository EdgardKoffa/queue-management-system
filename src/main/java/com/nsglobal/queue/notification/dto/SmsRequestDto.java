package com.nsglobal.queue.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/*
 * Ce dto sera utilisé par le SmsProvider.
 * */
@Getter
@Setter
@Builder
public class SmsRequestDto {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String message;

}