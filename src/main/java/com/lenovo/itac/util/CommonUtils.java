package com.lenovo.itac.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
	
	/** 年-月-日 时：分：秒 的日期格式 yyyy-MM-dd hh:mm:ss */
	public static final String DATE_FORMAT_Y_M_D_H_M_S = "yyyy-MM-dd hh:mm:ss";
	
	public static final int MO_LENGTH_MIN = 1;
	public static final int MO_LENGTH_MAX = 20;
	
	/** 换行符 */
	public static final String CHARACTER_NEW_LINE = "\\n";
	
	public static String format(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_Y_M_D_H_M_S);
		if (null !=  date) {
			return sdf.format(date);
		}
		return null;
	}
	
	/**
	 * 校验MO是否满足条件，主要是判断字符串长度是否满足条件
	 * @param mo
	 * @return
	 */
	public static boolean validateMO(String mo) {
		if (null != mo && mo.length() > MO_LENGTH_MIN && mo.length() < MO_LENGTH_MAX) {
			return true;
		}
		return false;
	}
}
