package com.nsglobal.queue.settings;

import java.util.List;

public interface SettingService {
	public String getValue(String key);
	public String setValue(String key,String value);
	public SettingEntity save(String key,String value);
	public List<SettingEntity> findAll();
}
