package com.nsglobal.queue.common.util;

public interface UtilsService {
	
	String getAuthenticatedUser();
	
	String getConnectedUserName();
	
	RemoteHttpDto getRemoteHostInfo();
	
	LocalHttpDto getServerHostInfo();
}
