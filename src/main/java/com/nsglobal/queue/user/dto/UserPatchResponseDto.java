package com.nsglobal.queue.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserPatchResponseDto {
	private boolean success;
	private String message;
	private String error;
}
