/**
 * 
 */
package com.sys.adv.criteria.beans;

import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author amjad_darwish
 *
 */
public class ReportSearchCriteriaDTO {
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Calendar fromDate;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Calendar toDate;
	
	private Long lawyerId;

	/**
	 * @return the fromDate
	 */
	public Calendar getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Calendar getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Calendar toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the lawyerId
	 */
	public Long getLawyerId() {
		return lawyerId;
	}

	/**
	 * @param lawyerId the lawyerId to set
	 */
	public void setLawyerId(Long lawyerId) {
		this.lawyerId = lawyerId;
	}
}
