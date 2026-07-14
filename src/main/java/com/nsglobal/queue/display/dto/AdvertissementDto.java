package com.nsglobal.queue.display.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertissementDto {
	 
		private Long id;

	    private String title;

	    private String mediaUrl;

	    private Integer duration;

}
