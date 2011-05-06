package net.agef.jobexchange.webservice.adapter.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static Calendar date2Calendar(Date date) {
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		} else
			return null;
	}

	public static Date calendar2Date(Calendar calendar) {
		if (calendar != null){
			return calendar.getTime();
		}
		else return null;
	}
}
