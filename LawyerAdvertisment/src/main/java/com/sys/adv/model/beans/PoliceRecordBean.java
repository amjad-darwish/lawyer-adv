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
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author amjad_darwish
 *
 */
@Entity
@Table(name = "POLICE_RECORDS")
@TableGenerator(name = "POLICE_RECORD_SEQ_GEN", table = "PK_SEQUENCES", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "POLICE_RECORD_SEQ", allocationSize = 10)
public class PoliceRecordBean {
	public static final String POLICE_RECORD_ALIAS = "policeRecord";
	public static final String CITY_BEAN_ALIAS = "cityBean";
	public static final String CITY_ID = "cityBean.id";
	public static final String CITY_NAME = "cityName";
	public static final String POLICE_RECORD_ID = "id";
	public static final String BENEFICIARY_FIRST_NAME = "beneficiaryFirstName";
	public static final String BENEFICIARY_LAST_NAME = "beneficiaryLastName";
	public static final String ACCIDENT_DATE = "accidentDate";
	public static final String ENTRY_DATE = "entryDate";
	public static final String POLICE_REPORT_BEAN = "policeReportBean";
	public static final String POLICE_REPORT_ID = "policeReportBean.id";
	public static final String POLICE_REPORT_DELETED = "deleted";
	public static final String DELETED = "deleted";
	public static final String AT_FAUL = "atFault";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "POLICE_RECORD_SEQ_GEN")
	@Column(name = "ID")
	private long id;

	@Column(name = "BENEFICIARY_FIRST_NAME")
	private String beneficiaryFirstName;

	@Column(name = "BENEFICIARY_MIDDLE_NAME")
	private String beneficiaryMiddleName;

	@Column(name = "BENEFICIARY_LAST_NAME")
	private String beneficiaryLastName;

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Column(name = "ACCIDENT_DATE")
	private Calendar accidentDate;

	@Column(name = "FIRST_PAGE")
	private int firstPage;

	@Column(name = "NO_OF_PAGES")
	private int noOfPages;

	@Column(name = "BENEFICIARY_TITLE")
	private String beneficiaryTitle;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "APPARTMENT")
	private String apartment;

	@ManyToOne
	@JoinColumn(name = "CITY_FK")
	private CityBean cityBean;

	@Column(name = "CITY_NAME")
	private String cityName;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIP_CODE")
	private String zipCode;

	@Column(name = "FULL_ZIP_CODE")
	private String fullZipCode;
	
	@ManyToOne
	@JoinColumn(name = "POLICE_REPORT_FK")
	@Where(clause = "deleted = 'F'")
	private PoliceReportBean policeReportBean;

	@Column(name = "DELETED")
	@Type(type = "true_false")
	private boolean deleted;

	@Transient
	private long policeReportId;

	@Column(name = "PART_POLICE_FILE_NAME")
	private String partPoliceFileName;

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Column(name = "ENTRY_DATE", updatable = false)
	private Calendar entryDate;

	@Column(name = "LONGITUDE", columnDefinition = "double default '-1'")
	private double longitude;

	@Column(name = "LATITUDE", columnDefinition = "double default '-1'")
	private double latitude;
	
	@Column(name = "AT_FAULT", columnDefinition = "char(1) default 'F'")
	@Type(type = "true_false")
	private boolean atFault;

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
	 * @return the beneficiaryFirstName
	 */
	public String getBeneficiaryFirstName() {
		return beneficiaryFirstName;
	}

	/**
	 * @param beneficiaryFirstName the beneficiaryFirstName to set
	 */
	public void setBeneficiaryFirstName(String beneficiaryFirstName) {
		this.beneficiaryFirstName = beneficiaryFirstName;
	}

	/**
	 * @return the beneficiaryLastName
	 */
	public String getBeneficiaryLastName() {
		return beneficiaryLastName;
	}

	/**
	 * @param beneficiaryLastName the beneficiaryLastName to set
	 */
	public void setBeneficiaryLastName(String beneficiaryLastName) {
		this.beneficiaryLastName = beneficiaryLastName;
	}

	/**
	 * @return the accidentDate
	 */
	public Calendar getAccidentDate() {
		return accidentDate;
	}

	/**
	 * @param accidentDate the accidentDate to set
	 */
	public void setAccidentDate(Calendar accidentDate) {
		this.accidentDate = accidentDate;
	}

	/**
	 * @return the beneficiaryMiddleName
	 */
	public String getBeneficiaryMiddleName() {
		return beneficiaryMiddleName;
	}

	/**
	 * @param beneficiaryMiddleName the beneficiaryMiddleName to set
	 */
	public void setBeneficiaryMiddleName(String beneficiaryMiddleName) {
		this.beneficiaryMiddleName = beneficiaryMiddleName;
	}

	/**
	 * @return the firstPage
	 */
	public int getFirstPage() {
		return firstPage;
	}

	/**
	 * @param firstPage the firstPage to set
	 */
	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
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
	 * @return the beneficiaryTitle
	 */
	public String getBeneficiaryTitle() {
		return beneficiaryTitle;
	}

	/**
	 * @param beneficiaryTitle the beneficiaryTitle to set
	 */
	public void setBeneficiaryTitle(String beneficiaryTitle) {
		this.beneficiaryTitle = beneficiaryTitle;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the apartment
	 */
	public String getApartment() {
		return apartment;
	}

	/**
	 * @param apartment the apartment to set
	 */
	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the cityBean
	 */
	public CityBean getCityBean() {
		return cityBean;
	}

	/**
	 * @param cityBean the cityBean to set
	 */
	public void setCityBean(CityBean cityBean) {
		this.cityBean = cityBean;
	}

	/**
	 * @return the policeReportBean
	 */
	public PoliceReportBean getPoliceReportBean() {
		return policeReportBean;
	}

	/**
	 * @param policeReportBean the policeReportBean to set
	 */
	public void setPoliceReportBean(PoliceReportBean policeReportBean) {
		this.policeReportBean = policeReportBean;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the policeReportId
	 */
	public long getPoliceReportId() {
		return policeReportId;
	}

	/**
	 * @param policeReportId the policeReportId to set
	 */
	public void setPoliceReportId(long policeReportId) {
		this.policeReportId = policeReportId;
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
	 * @return the partPoliceFileName
	 */
	public String getPartPoliceFileName() {
		return partPoliceFileName;
	}

	/**
	 * @param partPoliceFileName the partPoliceFileName to set
	 */
	public void setPartPoliceFileName(String partPoliceFileName) {
		this.partPoliceFileName = partPoliceFileName;
	}

	/**
	 * @return the entryDate
	 */
	public Calendar getEntryDate() {
		return entryDate;
	}

	/**
	 * @param entryDate the entryDate to set
	 */
	public void setEntryDate(Calendar entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the fullZipCode
	 */
	public String getFullZipCode() {
		return fullZipCode;
	}

	/**
	 * @param fullZipCode the fullZipCode to set
	 */
	public void setFullZipCode(String fullZipCode) {
		this.fullZipCode = fullZipCode;
	}
	
	public String getPrintableAddress() {
		return address + ", " + cityName + ", " + state + " " + fullZipCode; 
	}

	/**
	 * @return the atFault
	 */
	public boolean isAtFault() {
		return atFault;
	}

	/**
	 * @param atFault the atFault to set
	 */
	public void setAtFault(boolean atFault) {
		this.atFault = atFault;
	}
}
