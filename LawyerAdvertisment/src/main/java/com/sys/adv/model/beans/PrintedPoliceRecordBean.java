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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author amjad_darwish
 *
 */
@Entity
@Table(name="PRINTED_POLICE_RECORD")
@TableGenerator(name = "PRINTED_RECORD_SEQ_GEN", table = "PK_SEQUENCES", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "PRINTED_RECORD_SEQ", allocationSize = 10)
public class PrintedPoliceRecordBean {
	public static final String PRINTED_POLICE_RECORD_ALIAS = "printedPoliceRecord";
	public static final String PRINTED_POLICE_RECORD_BEAN_ALIAS = "policeRecordBean";
	public static final String POLICE_RECORD_BEAN_ALIAS = "policeRecordBean";
	public static final String PRINTED_LAWYER_BEAN_ALIAS = "lawyerBean";
	public static final String LAWYER_BEAN_ALIAS = "lawyerBean";
	public static final String POLICE_RECORD_ID = "policeRecordBean.id";
	public static final String PRINTED_DATE = "printedDate";
	public static final String LAWYER_ID 	= "lawyerBean.id";
	public static final String LAWYER_NAME 	= "lawyerBean.name";
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator = "PRINTED_RECORD_SEQ_GEN")
	@Column(name="ID")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="POLICE_RECORD_ID_FK")
	private PoliceRecordBean policeRecordBean;
	
	@ManyToOne
	@JoinColumn(name="LAWYER_ID_FK")
	private LawyerBean lawyerBean;
	
	@Column(name="PRINTED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar printedDate;
	
	@Column(name="FILE_NAME")
	private String fileName;
	
	@Column(name="GROUP_PRINTED_ID")
	private long groupPrintedId;
	
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
	 * @return the printedDate
	 */
	public Calendar getPrintedDate() {
		return printedDate;
	}

	/**
	 * @param printedDate the printedDate to set
	 */
	public void setPrintedDate(Calendar printedDate) {
		this.printedDate = printedDate;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the groupPrintedId
	 */
	public long getGroupPrintedId() {
		return groupPrintedId;
	}

	/**
	 * @param groupPrintedId the groupPrintedId to set
	 */
	public void setGroupPrintedId(long groupPrintedId) {
		this.groupPrintedId = groupPrintedId;
	}
}
