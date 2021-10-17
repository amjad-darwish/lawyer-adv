/**
 * 
 */
package com.sys.adv.model.beans;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author amjadd
 *
 */
@Entity
@Table(name="FAULT_CODE")
public class FaultCode {
	@Id
	private String code;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
