package com.nsglobal.queue.common.constant;

public final class HasPermissions {
	public HasPermissions() {
		super();
	}
	
	public static final String HAS_COUNTER_OPEN="hasAuthority('COUNTER_OPEN')";
	public static final String HAS_COUNTER_CLOSE="hasAuthority('COUNTER_CLOSE')";
	public static final String HAS_COUNTER_ASSIGN="hasAuthority('COUNTER_ASSIGN')";
	public static final String HAS_COUNTER_RELEASE="hasAuthority('COUNTER_RELEASE')";
	
	public static final String HAS_TICKET_CREATE="hasAuthority('TICKET_CREATE')";
	public static final String HAS_TICKET_CALL="hasAuthority('TICKET_CALL')";
	public static final String HAS_TICKET_TRANSFER="hasAuthority('TICKET_TRANSFER')";
	public static final String HAS_TICKET_FINISH="hasAuthority('TICKET_FINISH')";
	public static final String HAS_TICKET_CANCEL="hasAuthority('TICKET_CANCEL')";
	
	public static final String HAS_VIEW_DASHBOARD="hasAuthority('VIEW_DASHBOARD')";
	public static final String HAS_MANAGE_APPOINTMENTS="hasAuthority('MANAGE_APPOINTMENTS')";
	public static final String HAS_MANAGE_BRANCHS="hasAuthority('MANAGE_BRANCHS')";
	public static final String HAS_MANAGE_SERVICE="hasAuthority('MANAGE_SERVICE')";
	public static final String HAS_MANAGE_AGENCY="hasAuthority('MANAGE_AGENCY')";
	public static final String HAS_VIEW_REPORTS="hasAuthority('VIEW_REPORTS')";
	
	public static final String HAS_VIEW_LIST="hasAuthority('VIEW_LIST')";
	public static final String HAS_VIEW_DETAIL="hasAuthority('VIEW_DETAIL')";
	
	public static final String HAS_MANAGE_USERS="hasAuthority('MANAGE_USERS')";
	public static final String HAS_MANAGE_ROLES="hasAuthority('MANAGE_ROLES')";
	
	public static final String HAS_SEND_NOTIFICATION="hasAuthority('SEND_NOTIFICATION')";
	public static final String HAS_VIEW_DISPLAY="hasAuthority('VIEW_DISPLAY')";
}
