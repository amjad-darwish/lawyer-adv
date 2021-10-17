/**
 * 
 */
package com.sys.adv.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.core.convert.converter.Converter;

/**
 * @author amjadd
 *
 */
public class StringToCalendarConverter implements Converter<String, Calendar> {
	private static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	@Override
	public Calendar convert(String date) {
		try {
			if (date == null || date.trim().isEmpty()) {
				return null;
			}
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(formatter.parse(date));
			
			return calendar;
		} catch (ParseException e) {
			throw new RuntimeException(String.format("Couldn't parse the passed date: %s", date));
		}
	}

}
