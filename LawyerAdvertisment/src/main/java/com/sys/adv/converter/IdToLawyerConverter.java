/**
 * 
 */
package com.sys.adv.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.sys.adv.exceptions.AdvException;
import com.sys.adv.model.beans.LawyerBean;
import com.sys.adv.model.dao.LawyerDao;

/**
 * @author amjadd
 *
 */
public class IdToLawyerConverter implements Converter<String, LawyerBean> {
	@Autowired
	private LawyerDao lawyerDao;
	
	@Override
	public LawyerBean convert(String lawyerId) {
		try {
			return lawyerDao.get(Long.valueOf(lawyerId));
		} catch (AdvException e) {
			throw new RuntimeException(String.format("couldn't load Lawyer from the database with this id: %d", lawyerId));
		}
	}

}
