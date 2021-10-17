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
public class UnPrintedPoliceRecordSearchCriteriaDTO {
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Calendar fromDate;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Calendar toDate;
	
	private Integer distance;
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
	 * @return the distance
	 */
	public Integer getDistance() {
		return distance;
	}
	
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Integer distance) {
		this.distance = distance;
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
