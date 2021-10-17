package com.sys.adv.service;

import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.sys.adv.criteria.beans.UserDetails;
import com.sys.adv.model.beans.User;
import com.sys.adv.model.dao.UserDetailsDao;

/**
 * @author amjadd
 *
 */
public class UserDetailsServiceImp implements UserDetailsService {
  private UserDetailsDao userDetailsDao;
  
  public UserDetailsServiceImp(UserDetailsDao userDetailsDao) {
	  this.userDetailsDao = userDetailsDao;
  }

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userDetailsDao.findUserByUsername(username);
    UserBuilder builder = null;
    
    if (user != null) {
      builder = org.springframework.security.core.userdetails.User.withUsername(username);
      builder.disabled(!user.isEnabled());
      builder.password(user.getPassword());
      String[] authorities = user.getAuthorities()
          .stream().map(a -> a.getAuthority()).toArray(String[]::new);

      builder.authorities(authorities);
    } else {
      throw new UsernameNotFoundException("User not found.");
    }
    
    return new UserDetails(builder.build(), user.getLawyer());
  }
}
