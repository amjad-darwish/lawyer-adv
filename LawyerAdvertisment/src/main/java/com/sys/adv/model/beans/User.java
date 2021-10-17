/**
 * 
 */
package com.sys.adv.model.beans;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author amjadd
 *
 */
@Entity
@Table(name = "USERS")
public class User {
	@Id
	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "ENABLED", nullable = false)
	private boolean enabled;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<Authorities> authorities;
	
	@ManyToOne
	@JoinColumn(name = "LAYWER_ID_FK")
	private LawyerBean lawyer;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the authorities
	 */
	public Set<Authorities> getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(Set<Authorities> authorities) {
		this.authorities = authorities;
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
}