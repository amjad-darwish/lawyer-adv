/**
 * 
 */
package com.sys.adv.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.sys.adv.exceptions.AdvException;
import com.sys.adv.model.beans.State;
import com.sys.adv.model.dao.GeneralDAO;

/**
 * @author amjadd
 *
 */
public class IdToStateConverter implements Converter<String, State> {
	@Autowired
	private GeneralDAO generalDAO;
	
	@Override
	public State convert(String stateId) {
		try {
			return generalDAO.get(State.class, Long.valueOf(stateId));
		} catch (AdvException e) {
			throw new RuntimeException(String.format("couldn't load state from the database with this id: %d", stateId));
		}
	}

}
