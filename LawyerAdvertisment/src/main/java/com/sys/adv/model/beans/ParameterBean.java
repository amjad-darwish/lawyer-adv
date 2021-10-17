package com.sys.adv.model.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author amjad_darwish
 *
 */

@Entity
@Table(name="PARAMETER")
public class ParameterBean {
	public static final String PARM_NAME = "name";
	
	@Id
	@Column(name="ID")
	private long id;
	
	@Column(name="NAME", unique = true)
	private String name;
	
	@Column(name="VALUE")
	private String value;
	
	@Column(name="DESCRIPTION")
	private String description;

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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
