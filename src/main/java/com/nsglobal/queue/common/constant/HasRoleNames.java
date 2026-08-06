package com.nsglobal.queue.common.constant;

public final class HasRoleNames {
	
	public static final String HAS_SUPER_ADMIN= "hasAuthority('ROLE_SUPER_ADMIN')";

	public static final String HAS_ADMIN= "hasAuthority('ROLE_ADMIN')";

	public static final String HAS_AGENCY_MANAGER="hasAuthority('ROLE_AGENCY_MANAGER')";

	public static final String HAS_SUPERVISOR="hasAuthority('ROLE_SUPERVISOR')";

	public static final String HAS_OPERATOR="hasAuthority('ROLE_OPERATOR')";

	public static final String HAS_DISPLAY="hasAuthority('ROLE_DISPLAY')";

	public static final String HAS_AUDITOR="hasAuthority('ROLE_AUDITOR')";
	
	
	public HasRoleNames() {
		super();
	}

}
