/**
 * 
 */
package com.sys.adv.model.beans;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author amjad_darwish
 *
 */
@Entity
@Table(name="LAWYER_BACKGROUND")
@TableGenerator(name = "BACKGROUND_SEQ_GEN", table = "PK_SEQUENCES", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "BACKGROUND_SEQ", allocationSize = 10)
public class LawyerBackground {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator = "BACKGROUND_SEQ_GEN")
	private long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DESCRIBABLE_NAME")
	private String describableName;
	
	@OneToOne(mappedBy = "background")
	private LawyerBean lawyerBean;
	
	@Column(name="UPLOADED_TIMESTAMP")
	private Calendar uploadedClaendar;
	
	@Column(name="UPDATED_ON")
	private Calendar updatedOn;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the describableName
	 */
	public String getDescribableName() {
		return describableName;
	}

	/**
	 * @param describableName the describableName to set
	 */
	public void setDescribableName(String describableName) {
		this.describableName = describableName;
	}

	/**
	 * @return the lawyerBean
	 */
	public LawyerBean getLawyerBean() {
		return lawyerBean;
	}

	/**
	 * @param lawyerBean the lawyerBean to set
	 */
	public void setLawyerBean(LawyerBean lawyerBean) {
		this.lawyerBean = lawyerBean;
	}

	/**
	 * @return the uploadedClaendar
	 */
	public Calendar getUploadedClaendar() {
		return uploadedClaendar;
	}

	/**
	 * @param uploadedClaendar the uploadedClaendar to set
	 */
	public void setUploadedClaendar(Calendar uploadedClaendar) {
		this.uploadedClaendar = uploadedClaendar;
	}

	/**
	 * @return the updatedOn
	 */
	public Calendar getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * @param updatedOn the updatedOn to set
	 */
	public void setUpdatedOn(Calendar updatedOn) {
		this.updatedOn = updatedOn;
	}
}