package com.nsglobal.queue.settings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<SettingEntity, String> {
	
	SettingEntity findByKey(String key);
	
}
