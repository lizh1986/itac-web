package com.lenovo.itac.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
	
	/** 年-月-日 时：分：秒 的日期格式 yyyy-MM-dd hh:mm:ss */
	public static final String DATE_FORMAT_Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";
	
	/** 年-月-日 的日期格式 yyyy-MM-dd */
	public static final String DATE_FORMAT_Y_M_D = "yyyy-MM-dd";
	
	/** 当前时间和起始时间的时间差的格式：x days y hours */
	public static final String TIME_FORMAT_DURATION = "%d day %d hour";
	
	public static final int MO_LENGTH_MIN = 1;
	public static final int MO_LENGTH_MAX = 20;
	
	/** 换行符 */
	public static final String CHARACTER_NEW_LINE = "\\n";
	
	public static final String BUILD_DONE_STATION = "J33091000000000";
	public static final String RT_DONE_STATION = "J33092000000000";
	
	public static final String BUILD_DONE_STATUS_WITHOUT_LOT_NUMBER = "Missing Lot No.";
	public static final String BUILD_DONE_STATUS_FORMAT = "Last Work Step: %s";
	
	public static String format(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_Y_M_D_H_M_S);
		if (null !=  date) {
			return sdf.format(date);
		}
		return null;
	}
	
	public static Date parse(String dateStr) throws ParseException {
		if (dateStr == null || dateStr.isEmpty()) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_Y_M_D);
		return sdf.parse(dateStr);
	}
	
	/**
	 * 计算起始时刻到当前的时间，以字符串形式表示
	 * @param start
	 * @return
	 */
	public static String calcDuration(Date start) {
		Date current = new Date();
		
		int intervalDays = getIntervalDays(start, current);
		int intervalHours = getIntervalHours(start, current) % 24;
		
		return String.format(TIME_FORMAT_DURATION, intervalDays, intervalHours);
	}
	
	private static int getIntervalDays(Date fDate, Date oDate) {
	    if (null == fDate || null == oDate) {
	        return -1;
	    }

	    long intervalMilli = oDate.getTime() - fDate.getTime();

	    return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	 }

	private static int getIntervalHours(Date fDate, Date oDate) {
		if (null == fDate || null == oDate) {
			return -1;
		}

		long intervalMilli = oDate.getTime() - fDate.getTime();

		return (int) (intervalMilli / (60 * 60 * 1000));
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
