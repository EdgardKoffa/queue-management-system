package com.nsglobal.queue.common.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LocalHttpDto {
	
	private String ipAddress;

	private String sessionId;

	private String requestUri;

	private String httpMethod;

	private String user;
}
