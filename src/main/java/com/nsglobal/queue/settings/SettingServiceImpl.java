package com.nsglobal.queue.settings;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService{
	
	private final SettingRepository setRepo;

	@Override
	public String getValue(String key) {
		SettingEntity param=setRepo.findByKey(key.toString());
		return param.getValue();
	}

	@Override
	public String setValue(String key, String value) {
		SettingEntity param=setRepo.findByKey(key.toString());
		param.setValue(value);
		setRepo.save(param);
		return value;
	}

	@Override
	public List<SettingEntity> findAll() {
		
		return setRepo.findAll();
	}

	@Override
	public SettingEntity save(String key, String value) {
		
		return setRepo.save(SettingEntity
				.builder()
				.key(key)
				.value(value)
				.build());
	}
	
	
	

}
