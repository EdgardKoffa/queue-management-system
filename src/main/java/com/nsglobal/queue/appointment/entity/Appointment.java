package com.nsglobal.queue.appointment.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.nsglobal.queue.appointment.enums.AppointmentStatus;
import com.nsglobal.queue.bankservice.entity.BankService;
import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.common.entity.BaseEntity;
import com.nsglobal.queue.ticket.entity.Ticket;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "appointment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment extends BaseEntity{
    /**
     * Informations du client
     */
    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String phoneNumber;

    private String email;

    /**
     * Date du rendez-vous
     */
    @Column(nullable = false)
    private LocalDate appointmentDate;

    /**
     * Heure prévue
     */
    @Column(nullable = false)
    private LocalTime appointmentTime;

    /**
     * Agence
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name="branch_id")
    private Branch branch;

    /**
     * Service demandé
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "service_id")
    private BankService service;

    /**
     * Ticket généré lors du check-in
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    /**
     * Etat du rendez-vous
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    /**
     * QR Code (URL ou contenu)
     */
    @Column(length = 1000)
    private String qrCode;
//===puls tard===============
    
    private String notes;

    private Boolean reminderSent;

    private LocalDateTime reminderDate;

    private Boolean smsSent;

    private Boolean emailSent;

    private String cancellationReason;
}