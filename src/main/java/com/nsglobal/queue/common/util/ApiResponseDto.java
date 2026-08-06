package com.nsglobal.queue.common.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponseDto {
	private boolean success;
	private String message;
	private String error;
}
