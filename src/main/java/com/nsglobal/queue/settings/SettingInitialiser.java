package com.nsglobal.queue.settings;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SettingInitialiser  implements CommandLineRunner {
	
	private final SettingService service;
	
	@Override
	public void run(String... args) throws Exception {
		
		List<SettingEntity> list=service.findAll();
		
		List<SettingKeysEnum> keys=Arrays.asList(SettingKeysEnum.values());
		int i=0;
		
		for (SettingKeysEnum key : keys) {
			boolean sett=list.stream().filter(s->s.getKey().equals(key.name())).findFirst().isPresent();
			
			if(sett==false) {
				service.save(key.name(), key.getDefaultValue());
				System.out.println((i+1)+" -> initialisatio de %s avec la valeur %s".formatted(key.name(),key.getDefaultValue()));
				i++;
			}else {
				i++;
				continue;
			}
					
		}
	}

}
