/**
 * 
 */
package com.sys.adv.criteria.beans;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author amjad_darwish
 *
 */
public class PoliceRecordSearchDTO implements Serializable {
	private String firstName;
	private String lastName;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Calendar dateOA;
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the dateOA
	 */
	public Calendar getDateOA() {
		return dateOA;
	}
	/**
	 * @param dateOA the dateOA to set
	 */
	public void setDateOA(Calendar dateOA) {
		this.dateOA = dateOA;
	}
}
