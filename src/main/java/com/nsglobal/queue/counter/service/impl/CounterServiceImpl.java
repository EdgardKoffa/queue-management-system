package com.nsglobal.queue.counter.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;
import com.nsglobal.queue.audit.service.AuditService;
import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.branch.repository.BranchRepository;
import com.nsglobal.queue.common.constant.ApiMessages;
import com.nsglobal.queue.common.enums.CounterActions;
import com.nsglobal.queue.common.enums.CounterStatus;
import com.nsglobal.queue.common.enums.TicketStatus;
import com.nsglobal.queue.common.response.ApiPageResponse;
import com.nsglobal.queue.common.response.ApiResponse;
import com.nsglobal.queue.common.response.ResponseBuilder;
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
				.orElseThrow();
		
		/*if(counter.getActive()==false) {
			
			throw new RuntimeException("Le guichet %s N° %d est désactivé."
					.formatted(counter.getName(),counter.getNumber()));
		}*/
		
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
	
	@Transactional
	@Override
	public ApiResponse<CounterResponseDto> create(CounterRequestDto dto) {

		Branch br = branchRepo.findById(dto.getBranchId())
				.orElseThrow(null);//() -> new RuntimeException("La succursale est introuvable"));
		if(br==null) {
			String msg=ApiMessages.NOTFOUND_BRANCH;
			auditservice.log(AuditActionEnum.CREATE_COUNTER,
					ModulesNameEnum.COUNTER, "⚠️"+msg, false);
			return ResponseBuilder.error(msg);
		}
		
		Counter counter = counterRepo.findByName(dto.getName());
		if(counter!=null) {
			String msg=ApiMessages.EXIST_COUNTER;
			auditservice.log(AuditActionEnum.CREATE_COUNTER,
					ModulesNameEnum.COUNTER, "⚠️"+msg, false);
			return ResponseBuilder.error(msg);
		}
		counter=counterRepo.findByNumber(dto.getNumber());
		if(counter!=null) {
			String msg=ApiMessages.EXIST_COUNTER;
			auditservice.log(AuditActionEnum.CREATE_COUNTER,
					ModulesNameEnum.COUNTER, "⚠️"+msg, false);
			return ResponseBuilder.error(msg);
		}
		counter=mapper.toEntity(dto);
		counter.setBranch(br);
		CounterResponseDto counterSaved=mapper.toCounterResponse(counterRepo.save(counter));
		
		String msg=ApiMessages.CREATED;
		auditservice.log(AuditActionEnum.CREATE_COUNTER,
				ModulesNameEnum.COUNTER, "✅️"+msg, true);

		//
		return ResponseBuilder.success(msg, counterSaved);
	}
	
	@Transactional
	@Override
	public ApiResponse<CounterResponseDto> update(CounterRequestDto dto, Long id) {

		Counter existanceC = getById(id);
		if(existanceC==null) {
			return ResponseBuilder.error(ApiMessages.NOTFOUND_COUNTER);
		}
		Counter counter = mapper.toEntity(dto);

		existanceC.setActive(counter.getActive());
		existanceC.setCode(counter.getCode());
		existanceC.setName(counter.getName());
		existanceC.setNumber(counter.getNumber());
		existanceC.setStatus(counter.getStatus());
		
CounterResponseDto counterSaved= mapper.toCounterResponse(counterRepo.save(existanceC));
		
		String msg=ApiMessages.UPDATED;
		auditservice.log(AuditActionEnum.UPDATE_COUNTER,
				ModulesNameEnum.COUNTER, "✅️"+msg, true);

		//
		return ResponseBuilder.success(msg, counterSaved);
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public ApiResponse<CounterResponseDto> findById(Long id) {
		CounterResponseDto res=mapper.toCounterResponse(getById(id));
		return ResponseBuilder.success(ApiMessages.DETAILED,res);
	}
	
	@Transactional(readOnly = true)
	@Override
	public ApiResponse<List<CounterResponseDto>> findAll() {
		List<CounterResponseDto> lis=mapper.toListCounterResponseDto(counterRepo.findAll());
		return ResponseBuilder.success(ApiMessages.DETAILED, lis);
	}
	
	@Transactional(readOnly = true)
	@Override
	public ApiPageResponse<CounterResponseDto> findAll(Pageable pageable) {
		Page<CounterResponseDto> pages=counterRepo.findAll(pageable)
				.map(mapper::toCounterResponse);
		
		return ResponseBuilder.page(ApiMessages.DETAILED, pages);
	}
	
	@Transactional
	@Override
	public ApiResponse<CounterResponseDto> delete(Long id) {
		Counter c=getById(id);
		if(c==null) {
			String msg=ApiMessages.NOTFOUND_COUNTER;
			auditservice.log(
					AuditActionEnum.DELETE_COUNTER, 
					ModulesNameEnum.COUNTER,
					"❌ "+msg, 
					false);
			return ResponseBuilder.error(msg);
		}
		c.setDeletedAt(LocalDateTime.now());
		Counter counter=counterRepo.save(c);//deleteById(id);
		String msg=ApiMessages.DELETED;
		auditservice.log(AuditActionEnum.DELETE_COUNTER,
				ModulesNameEnum.COUNTER, "✅️"+msg, true);
		return ResponseBuilder.success(msg, mapper.toCounterResponse(counter));
	}
	
	/*
	 * Mise en service d'un guichet
	 * */
	@Transactional
	@Override
	public ApiResponse<CounterResponseDto> open(Long counterId) {
		
		Counter counter=getById(counterId);
		if(counter==null) {
			String msg=ApiMessages.NOTFOUND_COUNTER;
			auditservice.log(
					AuditActionEnum.OPEN_COUNTER, 
					ModulesNameEnum.COUNTER,
					"❌ %s".formatted(msg), 
					false);
			return ResponseBuilder.error(msg);
		}
		if(counter.getStatus()==CounterStatus.OPEN) {
			String msg=ApiMessages.ALREADY_OPENED_COUNTER
					.formatted(counter.getName(),counter.getNumber());
			auditservice.log(
					AuditActionEnum.OPEN_COUNTER, 
					ModulesNameEnum.COUNTER,
					"❌ "+ApiMessages.FAILURE_OPEN_COUNTER.formatted(msg), 
					false);
			
			return ResponseBuilder.error(msg);
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
				"✅ "+ApiMessages.SUCCESS, 
				true);
		return ResponseBuilder.success(ApiMessages.SUCCESS,mapper.toCounterResponse(openedCounter));
		
	}

	/*
	 * Fermeture d'un guichet
	 * */
	@Transactional
	@Override
	public ApiResponse<CounterResponseDto> close(Long counterId) {
		
		Counter counter=getById(counterId);
		if(counter==null) {
			String msg=ApiMessages.NOTFOUND_COUNTER;
			auditservice.log(
					AuditActionEnum.CLOSE_COUNTER, 
					ModulesNameEnum.COUNTER,
					"❌ %s".formatted(msg), 
					false);
			return ResponseBuilder.error(msg);
		}
		
		if(counter.getStatus()==CounterStatus.CLOSED) {
			String msg=ApiMessages.ALREADY_CLOSED_COUNTER
					.formatted(counter.getName(),counter.getNumber());
			auditservice.log(
					AuditActionEnum.CLOSE_COUNTER, 
					ModulesNameEnum.COUNTER,
					"❌ "+ApiMessages.FAILURE_CLOSE_COUNTER.formatted(msg), 
					false);
			
			return ResponseBuilder.error(msg);
		}
		
		boolean isCounterBusy=ticketRepo.existsByCounterIdAndStatus(counterId, TicketStatus.IN_PROGRESS);
		
		if(isCounterBusy) {
			String msg=ApiMessages.BUSY_COUNTER
					.formatted(counter.getName(),counter.getNumber());
			auditservice.log(
					AuditActionEnum.CLOSE_COUNTER, 
					ModulesNameEnum.COUNTER,
					"❌ "+ApiMessages.FAILURE_CLOSE_COUNTER.formatted(msg), 
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
				"✅ "+ApiMessages.SUCCESS, 
				true);
		return ResponseBuilder.success(ApiMessages.SUCCESS, mapper.toCounterResponse(closedCounter));
	}

	/*
	 * Assigner un operateur a un guichet
	 * */
	@Transactional
	@Override
	public ApiResponse<CounterResponseDto> assign(Long counterId, Long operatorId) {
		
		Counter counter=getById(counterId);
		if(counter==null) {
			String msg=ApiMessages.NOTFOUND_COUNTER;
			auditservice.log(
					AuditActionEnum.ASSIGN_OPERATOR, 
					ModulesNameEnum.COUNTER,
					"❌ %s".formatted(msg), 
					false);
			return ResponseBuilder.error(msg);
		}
		boolean isOperatorAssigned=counterRepo.existsByOperatorId(operatorId);
		
		if(isOperatorAssigned==true) {
			String msg=ApiMessages.OPERATOR_ALREADY_ASSIGNED;
			auditservice.log(
					AuditActionEnum.ASSIGN_OPERATOR, 
					ModulesNameEnum.COUNTER,
					"❌ "+ApiMessages.COUNTER_FAIL_OPERATOR.formatted(msg), 
					false);
			return ResponseBuilder.error(msg);
		}
		
		User operator=userRepo.findById(operatorId).orElseThrow(null);
		if(operator==null) {
				String msg=ApiMessages.OPERATOR_NOT_FOUND;
				auditservice.log(
						AuditActionEnum.ASSIGN_OPERATOR, 
						ModulesNameEnum.COUNTER,
						"❌ ".formatted(msg), 
						false);
				return ResponseBuilder.error(msg);
			
		}
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
		CounterResponseDto savedRes=mapper.toCounterResponse(counterRepo.save(counter));
		auditservice.log(
				AuditActionEnum.ASSIGN_OPERATOR, 
				ModulesNameEnum.COUNTER,
				"✅ "+ApiMessages.SUCCESS, 
				true);
		return ResponseBuilder.success(ApiMessages.SUCCESS, savedRes);
	}

	/*
	 * Liberer un guichet de son operatur
	 * */
	@Transactional
	@Override
	public ApiResponse<CounterResponseDto> release(Long counterId) {
		
		Counter counter=getById(counterId);
		if(counter==null) {
			String msg=ApiMessages.NOTFOUND_COUNTER;
			auditservice.log(
					AuditActionEnum.ASSIGN_OPERATOR, 
					ModulesNameEnum.COUNTER,
					"❌ %s".formatted(msg), 
					false);
			return ResponseBuilder.error(msg);
		}
		
		User operator=counter.getOperator();
		
		if(operator==null) {
			String msg=ApiMessages.OPERATOR_NOT_ASSIGNED;
			auditservice.log(
					AuditActionEnum.ASSIGN_OPERATOR, 
					ModulesNameEnum.COUNTER,
					"❌ "+ApiMessages.COUNTER_FAIL_RELEASE.formatted(msg), 
					false);
			throw new RuntimeException(msg);
		}
		if(counter.getStatus()!=CounterStatus.CLOSED||counter.getStatus()!=CounterStatus.OUT_OF_SERVICE) {
			String msg=ApiMessages.COUNTER_IN_USE;
			auditservice.log(
					AuditActionEnum.ASSIGN_OPERATOR, 
					ModulesNameEnum.COUNTER,
					"❌ "+ApiMessages.COUNTER_FAIL_RELEASE.formatted(msg), 
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
		
		CounterResponseDto saved=mapper.toCounterResponse(counterRepo.save(counter));
		
		auditservice.log(
				AuditActionEnum.RELEASE_COUNTER, 
				ModulesNameEnum.COUNTER,
				"✅ "+ApiMessages.OPERATOR_RELEASED.formatted(operator.getUserName(),counter.getName()), 
				true);
		return ResponseBuilder.success(ApiMessages.SUCCESS, saved);
	}
	@Transactional
	@Override
	public ApiResponse<List<CounterResponseDto>> openAllCounters() {
		
		List<Counter> counters=counterRepo.findAllByActive(true);
		List<String> checker=new ArrayList<String>();
		for (Counter counter : counters) {
		//	open(counter.getId());
			if(counter.getStatus()==CounterStatus.CLOSED) {
				counter.setStatus(CounterStatus.OPEN);
				checker.add(counter.getName());
			}
		}
		if(checker.isEmpty()) {
			String msg=ApiMessages.ALL_ALREADY_OPENED_COUNTER;
			auditservice.log(AuditActionEnum.OPEN_COUNTER, 
ModulesNameEnum.COUNTER, "❌ "+ApiMessages.ALL_FAILURE_OPEN_COUNTER.formatted(msg), false);
			return ResponseBuilder.error(msg);
		}
		List<Counter> allcounters =counterRepo.saveAll(counters);
		auditservice.log(AuditActionEnum.OPEN_COUNTER, 
				ModulesNameEnum.COUNTER, "✅  "+ApiMessages.SUCCESS, true);
		
		return ResponseBuilder.success(ApiMessages.SUCCESS, mapper.toListCounterResponseDto(allcounters));
	}
	@Transactional
	@Override
	public ApiResponse<List<CounterResponseDto>> closeAllCounters() {
	List<Counter> counters=counterRepo.findAll();
		List<String> checker=new ArrayList<String>();
		for (Counter counter : counters) {
			//close(counter.getId());
			if(counter.getStatus()==CounterStatus.OPEN) {
				counter.setStatus(CounterStatus.CLOSED);
				checker.add(counter.getName());
			}
		}
		if(checker.isEmpty()) {
			String msg=ApiMessages.ALL_ALREADY_CLOSED_COUNTER;
			auditservice.log(AuditActionEnum.CLOSE_COUNTER, 
ModulesNameEnum.COUNTER, "❌ "+ApiMessages.ALL_FAILURE_CLOSE_COUNTER.formatted(msg), false);
			return ResponseBuilder.error(msg);
		}
						
		List<Counter> allcounters =counterRepo.saveAll(counters);
		auditservice.log(AuditActionEnum.CLOSE_COUNTER, 
				ModulesNameEnum.COUNTER, "✅  "+ApiMessages.SUCCESS, true);
		
		return ResponseBuilder.success(ApiMessages.SUCCESS, mapper.toListCounterResponseDto(allcounters));
	}
	@Transactional
	@Override
	public ApiResponse<CounterResponseDto> changeCounterStatus(Long counterId, CounterStatus status) {
		Counter counter=getById(counterId);
		if(counter==null) {
			String msg=ApiMessages.NOTFOUND_COUNTER;
			auditservice.log(
					AuditActionEnum.CHANGE_STATUS, 
					ModulesNameEnum.COUNTER,
					"❌ %s".formatted(msg), 
					false);
			return ResponseBuilder.error(msg);
		}
		counter.setStatus(status);
		Counter patched=counterRepo.save(counter);
		auditservice.log(AuditActionEnum.CHANGE_STATUS, 
				ModulesNameEnum.COUNTER, "✅  "+ApiMessages.SUCCESS, true);
		
		return ResponseBuilder.success(ApiMessages.SUCCESS, mapper.toCounterResponse(patched));
	}
	@Transactional
	@Override
	public ApiResponse<CounterResponseDto> activate_desactivateCounter(Long counterId, boolean isctivate) {
		Counter counter=getById(counterId);
		if(counter==null) {
			String msg=ApiMessages.NOTFOUND_COUNTER;
			auditservice.log(
					AuditActionEnum.CHANGE_IS_ACTIVATE, 
					ModulesNameEnum.COUNTER,
					"❌ %s".formatted(msg), 
					false);
			
			return ResponseBuilder.error(msg);
		}
		
		counter.setActive(isctivate);
		
		Counter patched=counterRepo.save(counter);
		
		auditservice.log(AuditActionEnum.CHANGE_IS_ACTIVATE, 
				ModulesNameEnum.COUNTER, "✅  "+ApiMessages.SUCCESS, true);
		
		return ResponseBuilder.success(ApiMessages.SUCCESS, mapper.toCounterResponse(patched));
	}



}
