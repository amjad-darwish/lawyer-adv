package com.sys.adv.util;

import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This class is used as a generic class to implement the utility methods 
 * 
 * @author Amjad Darwish
 */
public class GeneralUtils {
	private static final Logger logger = LogManager.getLogger(GeneralUtils.class.getName());
	
	private static final String EMAIL_PATTERN =  "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);
	
	/**
	 * Mask the passed card no.
	 * 
	 * @createdOn Jun 11, 2014
	 *
	 * @param cardNo	Card number
	 * @return			Masked card no
	 */
	public static String maskCardNo (String cardNo) {
		StringBuilder maskedCardNo = new StringBuilder();
		maskedCardNo.append(cardNo.substring(0, 4));
		maskedCardNo.append(cardNo.substring(4, cardNo.length()-4).replaceAll(".", "*"));
		maskedCardNo.append(cardNo.substring(cardNo.length()-4, cardNo.length()));
		
		return maskedCardNo.toString();
	}
	
	/**
	 * Convert the passed string to integer, if any error occurred while parsing, the default value will be returned 
	 * 
	 * @createdOn Jun 16, 2014
	 *
	 * @param value
	 * @param defaultValue
	 * @return Parsed value
	 */
	public static int strToInt(String value, int defaultValue) {
		try {
			if(value != null && value.trim().length() > 0) {
				return Integer.valueOf(value);
			}
			
			return defaultValue;
		} catch(Exception e) {
			logger.error("An error", e);
			
			return defaultValue;
		}
	}
	
	/**
	 * Convert the passed string to long, if any error occurred while parsing, the default value will be returned 
	 * 
	 * @createdOn Jun 16, 2014
	 *
	 * @param value
	 * @param defaultValue
	 * @return Parsed value
	 */
	public static long strToLong(String value, long defaultValue) {
		try {
			if(value != null && value.trim().length() > 0) {
				return Long.valueOf(value);
			}
			
			return defaultValue;
		} catch(Exception e) {
			logger.error("An error", e);
			
			return defaultValue;
		}
	}
	
	/**
	 * Check if the passed email(s) is/are valid or not. 
	 * 
	 * @param emails Email value(s)
	 * 
	 * @return true if all passed strings are valid email(s), otherwise return false
	 */
	public static boolean validEmails(String ... emails) {
		for(String email: emails) {
			if(!pattern.matcher(email.trim()).matches()) {
				return false;
			}
		}
		
		return true;
	}
}
