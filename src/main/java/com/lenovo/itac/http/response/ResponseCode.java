package com.lenovo.itac.http.response;

public class ResponseCode {

	/** 001 - 传入的参数为空 */
	public static final String RESPONSE_CODE_PARAM_IS_NULL = "001";
	
	/** 002 - 数据库中已存在该记录 */
	public static final String RESPONSE_CODE_RECORD_EXIST = "002";
	
	/** 003 - 已经分配了权限的角色 */
	public static final String RESPONSE_CODE_ROLE_HAS_BEEN_ASSIGNED = "003";
	
	/** 003 - 已经分配了权限的角色，提示：以下角色已经分配了权限，无法删除。 */
	public static final String RESPONSE_CODE_ROLE_HAS_BEEN_ASSIGNED_MSG = "The following roles have been assigned，you should unassign them before delete:";
}
