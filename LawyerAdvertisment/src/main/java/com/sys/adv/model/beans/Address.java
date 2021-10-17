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

/**
 * @author amjadd
 *
 */
@Entity
@Table(name="ADDRESS")
@TableGenerator(name = "ADDRESS_SEQ_GEN", table = "PK_SEQUENCES", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "ADDRESS_SEQ", allocationSize = 10)
public class Address {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator = "ADDRESS_SEQ_GEN")
	@Column(name="ID")
	private long id;
	
	@Column(name = "STREET")
	private String street;
	
	@ManyToOne
	@JoinColumn(name = "CITY_FK")
	private CityBean city;
	
	@ManyToOne
	@JoinColumn(name = "STATE_FK")
	private State state;
	
	@ManyToOne
	@JoinColumn(name = "ZIP_CODE")
	private ZipCodeInfo zipCode;
	
	@Column(name = "SECONDARY_NO")
	private String secondaryNumber;

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
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @return the zipCode
	 */
	public ZipCodeInfo getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(ZipCodeInfo zipCode) {
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
}
