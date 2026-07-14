package com.nsglobal.queue.appointment.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.appointment.dto.AppointmentAvailabilityDto;
import com.nsglobal.queue.appointment.dto.AppointmentRequestDto;
import com.nsglobal.queue.appointment.dto.AppointmentResponseDto;
import com.nsglobal.queue.appointment.entity.Appointment;
import com.nsglobal.queue.appointment.enums.AppointmentStatus;
import com.nsglobal.queue.appointment.mapper.AppointmentMapper;
import com.nsglobal.queue.appointment.repository.AppointmentRepository;
import com.nsglobal.queue.appointment.service.AppointmentService;
import com.nsglobal.queue.bankservice.entity.BankService;
import com.nsglobal.queue.bankservice.repository.BankServiceRepository;
import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.branch.repository.BranchRepository;
import com.nsglobal.queue.dashboard.service.DashboardService;
import com.nsglobal.queue.notification.service.NotificationService;
import com.nsglobal.queue.ticket.dto.TicketRequestDto;
import com.nsglobal.queue.ticket.dto.TicketResponseDto;
import com.nsglobal.queue.ticket.entity.Ticket;
import com.nsglobal.queue.ticket.service.TicketService;
import com.nsglobal.queue.websocket.service.QueueNotificationService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AppointmentServiceImpl implements AppointmentService {
	
		private final AppointmentRepository appointmentRepository;

	    private final BranchRepository branchRepository;

	    private final BankServiceRepository servicePointRepository;

	    private final AppointmentMapper appointmentMapper;

	    private final TicketService ticketService;

	    private final NotificationService notificationService;

	    private final QueueNotificationService dashboardService;

	@Transactional
	@Override
	public AppointmentResponseDto create(AppointmentRequestDto request) {
		Branch branch = branchRepository.findById(request.getBranchId())
				.orElseThrow(() ->
	                    new EntityNotFoundException("Agence introuvable"));

	    BankService service =
	            servicePointRepository.findById(request.getServiceId())
	                    .orElseThrow(() ->
	                            new EntityNotFoundException("Service introuvable"));
	    
	    /*
	     * Verification et disponibilite des RDV
	     * */
	    Long total = appointmentRepository.countAppointments(
	            branch,
	            service,
	            request.getAppointmentDate(),
	            request.getAppointmentTime()
	            );
	    if(total >= 5){

	        throw new RuntimeException(
	                "Créneau complet");

	    }
	    
	    Appointment appointment = new Appointment();

	    appointment.setCustomerName(request.getCustomerName());

	    appointment.setPhoneNumber(request.getPhoneNumber());

	    appointment.setEmail(request.getEmail());

	    appointment.setBranch(branch);

	    appointment.setService(service);

	    appointment.setAppointmentDate(request.getAppointmentDate());

	    appointment.setAppointmentTime(request.getAppointmentTime());

	    appointment.setStatus(AppointmentStatus.CONFIRMED);
	    
	    //Plus tard: QRCodeGenerator.generate(...)
	    appointment.setQrCode(
	            UUID.randomUUID().toString()
	    );
	    //sauvegarde
	    appointmentRepository.save(appointment);
	    //notification
	   // notificationService.sendAppointmentConfirmation(
	   //         appointment
	   // );
	    //dashboard
	    dashboardService.publishDashboard();
	    
		return appointmentMapper.toResponse(
		        appointment
				);
	}

@Transactional(readOnly = true)
	@Override
	public AppointmentResponseDto findById(Long id) {
	 Appointment appointment = appointmentRepository.findById(id)
	            .orElseThrow(() ->
	                    new EntityNotFoundException(
	                            "Rendez-vous introuvable."));

	    return appointmentMapper.toResponse(appointment);
	}

@Transactional(readOnly = true)
	@Override
	public List<AppointmentResponseDto> findAll() {
	  List<Appointment> appointments =
	            appointmentRepository.findAll();

	    return appointmentMapper.toResponses(
	            appointments);
	}


	@Override
	public AppointmentResponseDto update(Long id, AppointmentRequestDto request) {

			    Appointment appointment =
			            appointmentRepository.findById(id)
			                    .orElseThrow(() ->
			                            new EntityNotFoundException(
			                                    "Rendez-vous introuvable."));
			    if (appointment.getStatus() == AppointmentStatus.CHECKED_IN
			            || appointment.getStatus() == AppointmentStatus.COMPLETED
			            || appointment.getStatus() == AppointmentStatus.NO_SHOW) {

			        throw new RuntimeException(
			                "Impossible de modifier ce rendez-vous.");

			    }
			    Branch branch =
			            branchRepository.findById(request.getBranchId())
			                    .orElseThrow(() ->
			                            new EntityNotFoundException(
			                                    "Agence introuvable."));
			    BankService service =
			            servicePointRepository.findById(request.getServiceId())
			                    .orElseThrow(() ->
			                            new EntityNotFoundException("Service introuvable"));
			   
			    Long total =
			            appointmentRepository.countAppointments(

			                    branch,

			                    service,

			                    request.getAppointmentDate(),

			                    request.getAppointmentTime()

			            );

			    if (total >= 5) {
			        throw new RuntimeException(
			                "Créneau complet.");
			    }
			    
			    appointment.setCustomerName(
			            request.getCustomerName());

			    appointment.setPhoneNumber(
			            request.getPhoneNumber());

			    appointment.setEmail(
			            request.getEmail());

			    appointment.setBranch(branch);

			    appointment.setService(service);

			    appointment.setAppointmentDate(
			            request.getAppointmentDate());

			    appointment.setAppointmentTime(
			            request.getAppointmentTime());
			    appointmentRepository.save(appointment);
			   // notificationService
		       // .sendAppointmentUpdated(
		        //appointment);
			    dashboardService.publishDashboard();
			    
			    return appointmentMapper.toResponse(
			            appointment);
	}

	@Override
	public void cancel(Long id) {
		 Appointment appointment = appointmentRepository.findById(id)
		            .orElseThrow(() ->
		                    new EntityNotFoundException(
		                            "Rendez-vous introuvable."));

		    if (appointment.getStatus() == AppointmentStatus.CHECKED_IN
		            || appointment.getStatus() == AppointmentStatus.COMPLETED
		            || appointment.getStatus() == AppointmentStatus.NO_SHOW) {

		        throw new RuntimeException(
		                "Ce rendez-vous ne peut plus être annulé.");

		    }

		    appointment.setStatus(AppointmentStatus.CANCELLED);

		    appointment.setUpdatedAt(LocalDateTime.now());

		    appointmentRepository.save(appointment);

		  //  notificationService.sendAppointmentCancelled(appointment);

		    dashboardService.publishDashboard();

		
	}

	@Override
	public AppointmentResponseDto checkIn(String qrCode) {
		Appointment appointment =
	            appointmentRepository.findByQrCode(qrCode)
	                    .orElseThrow(() ->
	                            new EntityNotFoundException(
	                                    "QR Code invalide."));
		if (appointment.getStatus() != AppointmentStatus.CONFIRMED) {

		    throw new RuntimeException(
		            "Ce rendez-vous ne peut pas être enregistré.");
		}
		if (!appointment.getAppointmentDate()
		        .equals(LocalDate.now())) {

		    throw new RuntimeException(
		            "Le rendez-vous n'est pas prévu aujourd'hui.");

		}
		TicketRequestDto request = new TicketRequestDto();

		request.setBranchId(
		        appointment.getBranch().getId());

		request.setServiceId(
		        appointment.getService().getId());

		request.setPhone(
		        appointment.getPhoneNumber());

		request.setEmail(
		        appointment.getEmail()
		        //getCustomerName()
		        );

		//request.(false);
		TicketResponseDto ticket =
		        ticketService.create(request);
		Ticket entity =
		        ticketService.findById(ticket.getId());
		appointment.setTicket(entity);

		appointment.setStatus(
		        AppointmentStatus.CHECKED_IN);

		appointment.setUpdatedAt(
		        LocalDateTime.now());
		appointmentRepository.save(appointment);
		//notificationService.sendAppointmentCheckedIn(
		 //       appointment);
		dashboardService.publishDashboard();
		return appointmentMapper.toResponse(
		        appointment);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<AppointmentAvailabilityDto> getAvailableSlots(
			Long branchId, 
			Long serviceId, 
			LocalDate date) {
		Branch branch =
		        branchRepository.findById(branchId)
		                .orElseThrow(() ->
		                        new EntityNotFoundException(
		                                "Agence introuvable"));

		BankService service =
		        servicePointRepository.findById(serviceId)
		                .orElseThrow(() ->
		                        new EntityNotFoundException(
		                                "Service introuvable"));
		List<AppointmentAvailabilityDto> result = new ArrayList<>();
		
		LocalTime start = LocalTime.of(8,0);
		//ou
		//

		LocalTime end = LocalTime.of(18,0);
		while (start.isBefore(end)) {
			Long total =
			        appointmentRepository.countAppointments(
			                branch,
			                service,
			                date,
			                start
			        );
			AppointmentAvailabilityDto dto =
			        new AppointmentAvailabilityDto();
			dto.setAppointmentTime(start);
			dto.setAvailableSlots(
			        Math.max(0,5-total.intValue())
			);
			dto.setAvailable(total < 5);
			result.add(dto);
			start = start.plus(Duration.ofMinutes(30));
			//ou
			//start = start.plusMinutes(30);
		}
		return result;
	}
	
	
}
