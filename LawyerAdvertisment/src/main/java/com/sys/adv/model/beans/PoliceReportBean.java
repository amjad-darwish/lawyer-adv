/**
 * 
 */
package com.sys.adv.model.beans;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

/**
 * @author amjad_darwish
 *
 */
@Entity
@Table(name="POLICE_REPORT")
@TableGenerator(name = "POLICE_REPORT_SEQ_GEN", table = "PK_SEQUENCES", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "POLICE_REPORT_SEQ", allocationSize = 10)
public class PoliceReportBean {
	public static final String PARM_ID = "id";
	public static final String PARM_CITY = "city";
	public static final String PARM_CITY_ID = "city.id"; 
	public static final String PARM_DELETED = "deleted";
	public static final String POLICE_RECORDS = "policeRecords"; 
	public static final String POLICE_RECORDS_DELETED = "policeRecords.deleted";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.TABLE, generator = "POLICE_REPORT_SEQ_GEN")
	private long id;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@ManyToOne
	@JoinColumn(name="CITY_FK")
	private CityBean city;
	
	@Column(name="NO_OF_PAGES")
	private int noOfPages;
	
	@Column(name="UPLOADED_TIMESTAMP")
	private Calendar uploadedClaendar;
	
	@Column(name="FILE_SIZE")
	private long fileSize;
	
	@Type(type="true_false")
	@Column(name="SEEN")
	private boolean seen;
	
	@Column(name="DELETED")
	@Type(type="true_false")
	private boolean deleted;

	@OneToMany(mappedBy = "policeReportBean", fetch = FetchType.EAGER)
	@Where(clause = "deleted = 'F'")
	private List<PoliceRecordBean> policeRecords;
	
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
	 * @return the city
	 */
	public CityBean getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(CityBean city) {
		this.city = city;
	}

	/**
	 * @return the noOfPages
	 */
	public int getNoOfPages() {
		return noOfPages;
	}

	/**
	 * @param noOfPages the noOfPages to set
	 */
	public void setNoOfPages(int noOfPages) {
		this.noOfPages = noOfPages;
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
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the seen
	 */
	public boolean isSeen() {
		return seen;
	}

	/**
	 * @param seen the seen to set
	 */
	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the policeRecords
	 */
	public List<PoliceRecordBean> getPoliceRecords() {
		return policeRecords;
	}

	/**
	 * @param policeRecords the policeRecords to set
	 */
	public void setPoliceRecords(List<PoliceRecordBean> policeRecords) {
		this.policeRecords = policeRecords;
	}
}
