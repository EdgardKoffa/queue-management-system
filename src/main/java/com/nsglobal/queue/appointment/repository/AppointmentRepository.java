package com.nsglobal.queue.appointment.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nsglobal.queue.appointment.entity.Appointment;
import com.nsglobal.queue.appointment.enums.AppointmentStatus;
import com.nsglobal.queue.bankservice.entity.BankService;
import com.nsglobal.queue.branch.entity.Branch;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	@EntityGraph(attributePaths = {"branch","service","ticket"})
    Optional<Appointment> findByIdAndStatusNot(
            Long id,
            AppointmentStatus status);
	
	@EntityGraph(attributePaths = {"branch","service","ticket"})
    List<Appointment> findByAppointmentDate(LocalDate date);

    List<Appointment> findByAppointmentDateAndStatus(
            LocalDate date,
            AppointmentStatus status);
    
    @EntityGraph(attributePaths = {"branch","service","ticket"})
    List<Appointment> findByBranch(Branch branch);
    
    
    @EntityGraph(attributePaths = {"branch","service","ticket"})
    List<Appointment> findByService(BankService service);

    @EntityGraph(attributePaths = {"branch","service","ticket"})
    List<Appointment> findByBranchAndAppointmentDate(
            Branch branch,
            LocalDate date);
    
    @Query("""
    		SELECT COUNT(a)
    		FROM Appointment a
    		WHERE a.branch = :branch
    		AND a.service = :service
    		AND a.appointmentDate = :date
    		AND a.appointmentTime = :time
    		AND a.status <> com.nsglobal.queue.appointment.enums.AppointmentStatus.CANCELLED
    		""")
    		Long countAppointments(
    		        @Param("branch") Branch branch,
    		        @Param("service") BankService service,
    		        @Param("date") LocalDate date,
    		        @Param("time") LocalTime time);


    /*
     * Très utile pour l'accueil.
     * */
    @Query("""
    		SELECT a
    		FROM Appointment a
    		WHERE a.appointmentDate >= :today
    		ORDER BY a.appointmentDate,
    		         a.appointmentTime
    		""")
    		List<Appointment> findUpcomingAppointments(
    		        @Param("today") LocalDate today);
    
    /*
     * Les rendez-vous d'une agence
     * */
    @Query("""
    		SELECT a
    		FROM Appointment a
    		WHERE a.branch.id = :branchId
    		ORDER BY a.appointmentDate,
    		         a.appointmentTime
    		""")
    		List<Appointment> findByBranchId(
    		        @Param("branchId") Long branchId);
    
    /*
     * Les rendez-vous d'un service
     * */
    
    @Query("""
    		SELECT a
    		FROM Appointment a
    		WHERE a.service.id = :serviceId
    		ORDER BY a.appointmentDate,
    		         a.appointmentTime
    		""")
    		List<Appointment> findByServiceId(
    		        @Param("serviceId") Long serviceId);
    
    /**
     * Les rendez-vous du jour
	*
	*	Très utilisé par le Dashboard.
	*
	*Hibernate traduit CURRENT_DATE selon le SGBD utilisé. 
	*C'est portable et conforme à JPQL.
     * **/
    @Query("""
    		SELECT a
    		FROM Appointment a
    		WHERE a.appointmentDate = CURRENT_DATE
    		ORDER BY a.appointmentTime
    		""")
    		List<Appointment> findTodayAppointments();
    
    /**
     * Les rendez-vous en attente de check-in
     * Le Scheduler utilisera cette requête.
     * */
    @Query("""
    		SELECT a
    		FROM Appointment a
    		WHERE a.status =
    		com.nsglobal.queue.appointment.enums.AppointmentStatus.CONFIRMED
    		AND a.appointmentDate = CURRENT_DATE
    		ORDER BY a.appointmentTime
    		""")
    		List<Appointment> findTodayConfirmedAppointments();
    
    /*
     * Les NO_SHOW
	*	`À 18h par exemple :
     * */
    @Query("""
    		SELECT a
    		FROM Appointment a
    		WHERE a.status =
    		com.nsglobal.queue.appointment.enums.AppointmentStatus.CONFIRMED
    		AND a.appointmentDate < :today
    		""")
    		List<Appointment> findExpiredAppointments(
    		        @Param("today") LocalDate today);
    
    /*
     * Recherche par QR Code
     * */
    Optional<Appointment> findByQrCode(String qrCode);
    
    
    /**
     * Recherche par téléphone
     * */
    List<Appointment> findByPhoneNumberOrderByAppointmentDateDesc(
            String phoneNumber);
    /**
     * Vérifier qu'un Ticket n'a pas déjà été créé
	Lors du check-in :
	Cela évite de générer deux tickets pour le même rendez-vous.
     * */
    boolean existsByTicketId(Long ticketId);
    boolean existsByTicketIsNotNullAndId(Long id);
}
