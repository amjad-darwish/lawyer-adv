/**
 * 
 */
package com.sys.adv.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.sys.adv.exceptions.AdvException;
import com.sys.adv.model.beans.CityBean;
import com.sys.adv.model.dao.GeneralDAO;

/**
 * @author amjadd
 *
 */
public class IdToCityConverter implements Converter<String, CityBean> {
	@Autowired
	private GeneralDAO generalDAO;
	
	@Override
	public CityBean convert(String cityId) {
		try {
			return generalDAO.get(CityBean.class, Long.valueOf(cityId));
		} catch (AdvException e) {
			throw new RuntimeException(String.format("couldn't load city from the database with this id: %d", cityId));
		}
	}

}
