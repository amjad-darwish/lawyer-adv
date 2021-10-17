package com.sys.adv.model.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sys.adv.model.beans.User;

@Transactional(propagation = Propagation.REQUIRED)
public class UserDetailsDao extends GeneralDAO {
	public User findUserByUsername(String username) {
		return getCurrentSession().get(User.class, username);
	}
}
