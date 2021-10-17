package com.sys.adv.model.beans;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "AUTHORITIES")
@IdClass(CompositeKey.class)
public class Authorities {
	@Id
	@Column(name = "AUTHORITY")
	private String authority;

	@ManyToOne
	@JoinColumn(name = "USER_NAME")
	@Id
	private User user;

	/**
	 * @return the authority
	 */
	public String getAuthority() {
		return authority;
	}

	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
}

class CompositeKey implements Serializable {
	private String authority;
	private User user;

	@Override
	public int hashCode() {
		return (authority.hashCode() + user.getUserName().hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CompositeKey) {
			CompositeKey compositeKey = (CompositeKey) obj;

			return Objects.equals(compositeKey.user.getUserName(), user.getUserName())
					&& Objects.equals(compositeKey.authority, authority);
		}

		return false;
	}
}
