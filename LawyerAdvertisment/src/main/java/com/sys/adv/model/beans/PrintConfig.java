/**
 * 
 */
package com.sys.adv.model.beans;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * @author amjadd
 *
 */
@Table(name = "PRINT_CONFIG")
@Entity
@TableGenerator(name = "PRINT_CONFIG_SEQ_GEN", table = "PK_SEQUENCES", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "PRINT_CONFIG_SEQ", allocationSize = 10)
public class PrintConfig {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator = "PRINT_CONFIG_SEQ_GEN")
	@Column(name="ID")
	private long id;
	
	@ElementCollection
    @CollectionTable(name = "LAWYER_EXCLUDE_CITY", joinColumns = @JoinColumn(name = "PRINT_CONFIG_FK"))
	@Column(name="CITY_FK")
	private List<Long> excludeCitiesIds;
	
	@Transient
	private List<String> excludeCitiesNames;
	
	@ElementCollection
    @CollectionTable(name = "LAWYER_INCLUDE_CITY", joinColumns = @JoinColumn(name = "PRINT_CONFIG_FK"))
	@Column(name="CITY_FK")
	private List<Long> includeCitiesIds;
	
	@Transient
	private List<String> includeCitiesNames;
	
	@ElementCollection
    @CollectionTable(name = "LAWYER_ZIP_CODE", joinColumns = @JoinColumn(name = "PRINT_CONFIG_FK"))
	@Column(name="ZIP_CODE_FK")
	private List<Long> zipCodes;
	
	@ElementCollection
    @CollectionTable(name = "LAWYER_CUSTOMER_LAST_NAME", joinColumns = @JoinColumn(name = "PRINT_CONFIG_FK"))
	@Column(name="CUSTOMER_LAST_NAME")
	private List<String> customersLastNames;
	
	@Column(name = "DISTANCE")
	private int distance;

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
	 * @return the excludeCitiesIds
	 */
	public List<Long> getExcludeCitiesIds() {
		return excludeCitiesIds;
	}

	/**
	 * @param excludeCitiesIds the excludeCitiesIds to set
	 */
	public void setExcludeCitiesIds(List<Long> excludeCitiesIds) {
		this.excludeCitiesIds = excludeCitiesIds;
	}

	/**
	 * @return the includeCitiesIds
	 */
	public List<Long> getIncludeCitiesIds() {
		return includeCitiesIds;
	}

	/**
	 * @param includeCitiesIds the includeCitiesIds to set
	 */
	public void setIncludeCitiesIds(List<Long> includeCitiesIds) {
		this.includeCitiesIds = includeCitiesIds;
	}

	/**
	 * @return the zipCodes
	 */
	public List<Long> getZipCodes() {
		return zipCodes;
	}

	/**
	 * @param zipCodes the zipCodes to set
	 */
	public void setZipCodes(List<Long> zipCodes) {
		this.zipCodes = zipCodes;
	}

	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	/**
	 * @return the customerLastNames
	 */
	public List<String> getCustomersLastNames() {
		return customersLastNames;
	}

	/**
	 * @param customerLastNames the customerLastNames to set
	 */
	public void setCustomersLastNames(List<String> customersLastNames) {
		this.customersLastNames = customersLastNames;
	}

	/**
	 * @return the excludeCitiesNames
	 */
	public List<String> getExcludeCitiesNames() {
		return excludeCitiesNames;
	}

	/**
	 * @param excludeCitiesNames the excludeCitiesNames to set
	 */
	public void setExcludeCitiesNames(List<String> excludeCitiesNames) {
		this.excludeCitiesNames = excludeCitiesNames;
	}

	/**
	 * @return the includeCitiesNames
	 */
	public List<String> getIncludeCitiesNames() {
		return includeCitiesNames;
	}

	/**
	 * @param includeCitiesNames the includeCitiesNames to set
	 */
	public void setIncludeCitiesNames(List<String> includeCitiesNames) {
		this.includeCitiesNames = includeCitiesNames;
	}
}
