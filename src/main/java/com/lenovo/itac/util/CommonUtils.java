package com.lenovo.itac.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
	
	private static final String DATE_FORMAT_Y_M_D_H_M_S = "yyyy-MM-dd hh:mm:ss";
	
	public static String format(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_Y_M_D_H_M_S);
		if (null !=  date) {
			return sdf.format(date);
		}
		return null;
	}
}
