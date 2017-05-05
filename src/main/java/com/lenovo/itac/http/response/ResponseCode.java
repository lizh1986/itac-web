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

	/** 004 - session已失效，用户信息不存在 */
	public static final String SESSION_TIME_OUT = "004";
	
	/** 005 - 登录失败 */
	public static final String FAILED_TO_LOG_IN = "005";
	
	/** 005 - 登录失败 */
	public static final String FAILED_TO_LOG_IN_MSG = "Failed to log in. The erro code is: %s";
	
	/** 006 - 注销失败 */
	public static final String FAILED_TO_LOG_OUT = "006";
	
	/** 006 - 注销失败 */
	public static final String FAILED_TO_LOG_OUT_MSG = "Failed to log out. The erro code is: %s";
	
	/** 007 - 时间范围不正确，起始时间不能晚于终止时间 */
	public static final String RESPONSE_CODE_TIME_RANGE = "007";

	/** 008 - 选择的时间范围太长，不支持 */
	public static final String RESPONSE_CODE_TIME_RANGE_TOO_LONG = "008";
}
