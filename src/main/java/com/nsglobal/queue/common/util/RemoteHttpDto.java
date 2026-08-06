package com.nsglobal.queue.common.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RemoteHttpDto {
	
	private String ipAddress;

	private String sessionId;

	private String requestUri;

	private String httpMethod;

	private String userAgent;

	private String userLogged;
}
