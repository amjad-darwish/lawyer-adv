/**
 * 
 */
package com.sys.adv.model.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Type;

/**
 * @author amjadd
 *
 */
@Entity
@Table(name="OFFICE")
@TableGenerator(name = "OFFICE_SEQ_GEN", table = "PK_SEQUENCES", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "OFFICE_SEQ", allocationSize = 10)
public class Office {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator = "OFFICE_SEQ_GEN")
	@Column(name="ID")
	private long id;
	
	@Column(name="NAME")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "LAWYER_FK")
	private LawyerBean lawyer;
	
	@Column(name = "STREET")
	private String street;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "ZIP_CODE")
	private String zipCode;
	
	@Column(name = "SECONDARY_NO")
	private String secondaryNumber;
	
	@Column(name="DELETED")
	@Type(type="true_false")
	private boolean deleted;
	
	@Column(name = "LATITUDE")
	private double latitude;
	
	@Column(name = "LONGITUDE")
	private double longitude;

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
	 * @return the lawyer
	 */
	public LawyerBean getLawyer() {
		return lawyer;
	}

	/**
	 * @param lawyer the lawyer to set
	 */
	public void setLawyer(LawyerBean lawyer) {
		this.lawyer = lawyer;
	}

	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
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
	 * @return the secondaryNumber
	 */
	public String getSecondaryNumber() {
		return secondaryNumber;
	}

	/**
	 * @param secondaryNumber the secondaryNumber to set
	 */
	public void setSecondaryNumber(String secondaryNumber) {
		this.secondaryNumber = secondaryNumber;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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
}
