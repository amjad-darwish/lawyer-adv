package com.sys.adv.model.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CITY")
public class CityBean {
	@Id
	@Column(name="ID")
	private long id;
	
	@Column(name="NAME")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="COUNTY_FK")
	private CountyBean county;

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
	 * @return the county
	 */
	public CountyBean getCounty() {
		return county;
	}

	/**
	 * @param county the county to set
	 */
	public void setCounty(CountyBean county) {
		this.county = county;
	}
}
