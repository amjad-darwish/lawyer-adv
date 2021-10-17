/**
 * 
 */
package com.sys.adv.criteria.beans;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.sys.adv.model.beans.LawyerBean;

/**
 * @author amjadd
 *
 */
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
	private LawyerBean lawyer;
	private org.springframework.security.core.userdetails.UserDetails user;
	
	public UserDetails(org.springframework.security.core.userdetails.UserDetails user) {
		this(user, null);
	}
	
	public UserDetails(org.springframework.security.core.userdetails.UserDetails user, LawyerBean lawyer) {
		this.user = user;
		this.lawyer = lawyer;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getAuthorities();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}
	
	public LawyerBean getLawyer() {
		return lawyer;
	}
	
	public org.springframework.security.core.userdetails.UserDetails getUser() {
		return user;
	}
	
	public boolean isLawyer() {
		return lawyer != null;
	}
}
