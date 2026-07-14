package com.nsglobal.queue.notification.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/*
 * Ce Dto est sera utiliser par email provider
 * */
@Getter
@Setter
@Builder
public class EmailRequestDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String subject;

    @NotBlank
    private String message;

}