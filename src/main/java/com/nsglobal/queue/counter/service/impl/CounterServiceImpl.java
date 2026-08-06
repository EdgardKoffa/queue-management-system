package com.nsglobal.queue.counter.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;
import com.nsglobal.queue.audit.service.AuditService;
import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.branch.repository.BranchRepository;
import com.nsglobal.queue.common.enums.CounterActions;
import com.nsglobal.queue.common.enums.CounterStatus;
import com.nsglobal.queue.common.enums.TicketStatus;
import com.nsglobal.queue.counter.dto.CounterRequestDto;
import com.nsglobal.queue.counter.dto.CounterResponseDto;
import com.nsglobal.queue.counter.entity.Counter;
import com.nsglobal.queue.counter.entity.CounterHistory;
import com.nsglobal.queue.counter.mapper.CounterMapper;
import com.nsglobal.queue.counter.repository.CounterHistoryRepository;
import com.nsglobal.queue.counter.repository.CounterRepository;
import com.nsglobal.queue.counter.service.CounterService;
import com.nsglobal.queue.ticket.repository.TicketRepository;
import com.nsglobal.queue.user.entity.User;
import com.nsglobal.queue.user.repository.UserRepository;
import com.nsglobal.queue.websocket.service.QueueNotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CounterServiceImpl implements CounterService {

	private final CounterRepository counterRepo;
	private final CounterHistoryRepository hitoryRepo;
	private final CounterMapper mapper;
	private final BranchRepository branchRepo;
	
	private final UserRepository userRepo;
	
	private final TicketRepository ticketRepo;
	
	private final QueueNotificationService notification;
	
	private final AuditService auditservice;
	
	private Counter getById(Long id) {
		
		Counter counter=counterRepo.findById(id)
				.orElseThrow(
						()->{
							
						return	new RuntimeException("Impossible de trouver le guichet");
						}
						);
		
		if(counter.getActive()==false) {
			
			throw new RuntimeException("Le guichet %s N° %d est désactivé."
					.formatted(counter.getName(),counter.getNumber()));
		}
		
		return counter;
	}
	
	private void saveHistory(Counter counter,CounterActions action,String ipAddress,String operator,String comment) {
		
		CounterHistory histo=CounterHistory
				.builder()
				.action(action)
				.assigned_operator(operator)
				.ipAdress(ipAddress)
				.reccordTime(LocalDateTime.now())
				.counter(counter)
				.comment(comment)
				.build();
		
		hitoryRepo.save(histo);
	}
	
	@Override
	public CounterResponseDto create(CounterRequestDto dto) {

		Branch br = branchRepo.findById(dto.getBranchId())
				.orElseThrow(() -> new RuntimeException("La succursale est introuvable"));

		Counter counter = mapper.toEntity(dto);
		counter.setBranch(br);

		return mapper.toCounterResponse(counterRepo.save(counter));
	}

	@Override
	public CounterResponseDto update(CounterRequestDto dto, Long id) {

		Counter existanceC = getById(id);
		Counter counter = mapper.toEntity(dto);

		existanceC.setActive(counter.getActive());
		existanceC.setCode(counter.getCode());
		existanceC.setName(counter.getName());
		existanceC.setNumber(counter.getNumber());
		existanceC.setStatus(counter.getStatus());

		return mapper.toCounterResponse(counterRepo.save(existanceC));
	}

	@Override
	public CounterResponseDto findById(Long id) {
		return mapper.toCounterResponse(getById(id));
	}

	@Override
	public List<CounterResponseDto> findAll() {
		return mapper.toListCounterResponseDto(counterRepo.findAll());
	}

	@Override
	public void delete(Long id) {
		counterRepo.deleteById(id);

	}
	
	/*
	 * Mise en service d'un guichet
	 * */
	@Transactional
	@Override
	public CounterResponseDto open(Long counterId) {
		
		Counter counter=getById(counterId);
		
		if(counter.getStatus()==CounterStatus.OPEN) {
			String msg="Le guichet %s N° %d est déjà en service"
					.formatted(counter.getName(),counter.getNumber());
			auditservice.log(
					AuditActionEnum.OPEN_COUNTER, 
					ModulesNameEnum.COUNTER,
					"❌ Echec d'ouverture du guichet. Cause: %s".formatted(msg), 
					false);
			
			throw new RuntimeException(msg);
		}
		if(counter.getStatus()==CounterStatus.OUT_OF_SERVICE) {
			String msg="Le guichet %s N° %d est en hors service"
					.formatted(counter.getName(),counter.getNumber());
			auditservice.log(
					AuditActionEnum.OPEN_COUNTER, 
					ModulesNameEnum.COUNTER,
					"❌ Echec d'ouverture du guichet. Cause: %s".formatted(msg), 
					false);
			throw new RuntimeException(msg);
		}
		if(counter.getStatus()==CounterStatus.BUSY) {
			throw new RuntimeException("Le guichet %s N° %d est déjà en service"
					.formatted(counter.getName(),counter.getNumber()));
		}
		
		if(counter.getOperator()==null) {
			String msg="Aucun opérateur n'est assigné  au guichet %s N° %d."
					.formatted(counter.getName(),counter.getNumber());
			auditservice.log(
					AuditActionEnum.OPEN_COUNTER, 
					ModulesNameEnum.COUNTER,
					"❌ Echec d'ouverture du guichet. Cause: %s".formatted(msg), 
					false);
			throw new RuntimeException(msg);
		}
		
		counter.setStatus(CounterStatus.OPEN);
		
		Counter openedCounter=counterRepo.save(counter);
		
		//ligne de notification
		//websocket.publishCounter(counter)
		notification.publishCounter(counter);
		notification.publishDashboard();
		
		//historique
		saveHistory(
				openedCounter,
				CounterActions.OPENED,
				null,null,
				"Overture du guichet");
		auditservice.log(
				AuditActionEnum.OPEN_COUNTER, 
				ModulesNameEnum.COUNTER,
				"✅ Succes d'ouverture du guichet %s .".formatted(openedCounter.getName()), 
				true);
		return mapper.toCounterResponse(openedCounter);
		
	}

	/*
	 * Fermeture d'un guichet
	 * */
	@Transactional
	@Override
	public CounterResponseDto close(Long counterId) {
		
		Counter counter=getById(counterId);
		
		if(counter.getStatus()==CounterStatus.CLOSED ) {
			String msg="Le guichet %s N° %d est déjà fermé."
					.formatted(counter.getName(),counter.getNumber());
			auditservice.log(
					AuditActionEnum.CLOSE_COUNTER, 
					ModulesNameEnum.COUNTER,
					"❌ Echec de fermeture du guichet. Cause: %s".formatted(msg), 
					false);
			throw new RuntimeException(msg);
		}
		
		boolean isCounterBusy=ticketRepo.existsByCounterIdAndStatus(counterId, TicketStatus.IN_PROGRESS);
		
		if(isCounterBusy) {
			String msg="Le guichet %s N° %d a traitement encours."
					.formatted(counter.getName(),counter.getNumber());
			auditservice.log(
					AuditActionEnum.CLOSE_COUNTER, 
					ModulesNameEnum.COUNTER,
					"❌ Echec de fermeture du guichet. Cause: %s".formatted(msg), 
					false);
			throw new RuntimeException(msg);
		}
		counter.setStatus(CounterStatus.CLOSED);
		
		Counter closedCounter=counterRepo.save(counter);
		
		//lignet de notification
		//websocket.publishCounter(counter);
		notification.publishCounter(counter);
		notification.publishDashboard();
		
		//historique
		saveHistory(
				closedCounter,
				CounterActions.CLOSED,
				null,null,
				"Fermeture du guichet");
		auditservice.log(
				AuditActionEnum.CLOSE_COUNTER, 
				ModulesNameEnum.COUNTER,
				"✅ Succes de fermeture du guichet %s .".formatted(closedCounter.getName()), 
				true);
		return mapper.toCounterResponse(closedCounter);
	}

	/*
	 * Assigner un operateur a un guichet
	 * */
	@Transactional
	@Override
	public CounterResponseDto assign(Long counterId, Long operatorId) {
		
		Counter counter=getById(counterId);
		
		boolean isOperatorAssigned=counterRepo.existsByOperatorId(operatorId);
		
		if(isOperatorAssigned==true) {
			String msg="L'opérateur déjà assigné à un guichet.";
			auditservice.log(
					AuditActionEnum.ASSIGN_OPERATOR, 
					ModulesNameEnum.COUNTER,
					"❌ Echec d'assignation d'un opérateur au guichet. Cause: %s".formatted(msg), 
					false);
			throw new RuntimeException(msg);
		}
		
		User operator=userRepo.findById(operatorId).orElseThrow(
				()->{
					String msg="Cet L'opérateur est introuvable.";
					auditservice.log(
							AuditActionEnum.ASSIGN_OPERATOR, 
							ModulesNameEnum.COUNTER,
							"❌ Echec d'assignation d'un opérateur au guichet. Cause: %s".formatted(msg), 
							false);
					return new RuntimeException(msg);
				}
				);
		
		counter.setOperator(operator);	
		
		//notification
		notification.publishCounter(counter);
		notification.publishDashboard();
		
		//historique
		saveHistory(
				counter,
				CounterActions.ASSIGNED_OPERATOR,
				null,operator.getEmail(),
				"l'opérateur est assigné au guichet");
		auditservice.log(
				AuditActionEnum.ASSIGN_OPERATOR, 
				ModulesNameEnum.COUNTER,
				"✅ l'opérateur %s est assigné au guichet %s .".formatted(operator.getUserName(),counter.getName()), 
				true);
		return mapper.toCounterResponse(counterRepo.save(counter));
	}

	/*
	 * Liberer un guichet de son operatur
	 * */
	@Transactional
	@Override
	public CounterResponseDto release(Long counterId) {
		
		Counter counter=getById(counterId);
		
		User operator=counter.getOperator();
		
		if(operator==null) {
			String msg="Aucun opérateur n'est assigné à cet guichet.";
			auditservice.log(
					AuditActionEnum.ASSIGN_OPERATOR, 
					ModulesNameEnum.COUNTER,
					"❌ Echec de retraction d'un opérateur au guichet. Cause: %s".formatted(msg), 
					false);
			throw new RuntimeException(msg);
		}
		if(counter.getStatus()!=CounterStatus.CLOSED||counter.getStatus()!=CounterStatus.OUT_OF_SERVICE) {
			String msg="Le guichet est occupé par l'opérateur.";
			auditservice.log(
					AuditActionEnum.ASSIGN_OPERATOR, 
					ModulesNameEnum.COUNTER,
					"❌ Echec de retraction d'un opérateur au guichet. Cause: %s".formatted(msg), 
					false);
			throw new RuntimeException(msg);
		}
		counter.setOperator(null);
		
		//notification
		notification.publishCounter(counter);
		notification.publishDashboard();
		
		//historique
		saveHistory(
				counter,
				CounterActions.RELEASED_OPRATOR,
				null,operator.getEmail(),
				"l'opérateur est retiré du guichet");
		auditservice.log(
				AuditActionEnum.RELEASE_COUNTER, 
				ModulesNameEnum.COUNTER,
				"✅ l'opérateur %s est retiré du guichet %s .".formatted(operator.getUserName(),counter.getName()), 
				true);
		return mapper.toCounterResponse(counterRepo.save(counter));
	}

	@Override
	public void openAllCounters() {
		
		List<Counter> counters=counterRepo.findAllByActive(true);
		
		for (Counter counter : counters) {
			open(counter.getId());
		/*	if(counter.getStatus()==CounterStatus.CLOSED) {
				counter.setStatus(CounterStatus.OPEN);
				
			}*/
		}
		
	}

	@Override
	public void closeAllCounters() {
	List<Counter> counters=counterRepo.findAll();
		
		for (Counter counter : counters) {
			close(counter.getId());
		/*	if(counter.getStatus()==CounterStatus.OPEN) {
				counter.setStatus(CounterStatus.CLOSED);
				
			}*/
		}
		
	}



}
