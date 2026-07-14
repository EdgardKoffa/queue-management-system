package com.nsglobal.queue.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/***
 * Ce dto est commun a tous les canaux de messagerie: sms,email
 * champ commun: recipient et message
 * subject est particulier a email canal
 * */
@Getter
@Setter
@Builder
public class NotificationRequestDto {

    @NotBlank
    private String recipient;//destinataire

    @NotBlank
    private String subject;//objet

    @NotBlank
    private String message;//contenu

}