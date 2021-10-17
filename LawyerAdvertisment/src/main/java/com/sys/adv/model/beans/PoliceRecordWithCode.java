/**
 * 
 */
package com.sys.adv.model.beans;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

/**
 * @author amjadd
 *
 */
@Entity
@Table(name="POLICE_RECORD_WITH_CODE")
public class PoliceRecordWithCode {
	@Id
	private String code;
	
	@ManyToOne
	@JoinColumn(name="POLICE_RECORD_ID_FK")
	@Where(clause = "deleted = 'F'")
	private PoliceRecordBean policeRecordBean;
	
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

	/**
	 * @return the policeRecordBean
	 */
	public PoliceRecordBean getPoliceRecordBean() {
		return policeRecordBean;
	}

	/**
	 * @param policeRecordBean the policeRecordBean to set
	 */
	public void setPoliceRecordBean(PoliceRecordBean policeRecordBean) {
		this.policeRecordBean = policeRecordBean;
	}

	@Override
	public String toString() {
		return "PoliceRecordWithCode (code=" + code + ", policeRecordBean=" + policeRecordBean + ")";
	}
}
