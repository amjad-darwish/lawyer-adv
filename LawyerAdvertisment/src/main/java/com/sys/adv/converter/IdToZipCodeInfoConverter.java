/**
 * 
 */
package com.sys.adv.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.sys.adv.exceptions.AdvException;
import com.sys.adv.model.beans.ZipCodeInfo;
import com.sys.adv.model.dao.GeneralDAO;

/**
 * @author amjadd
 *
 */
public class IdToZipCodeInfoConverter implements Converter<String, ZipCodeInfo> {
	@Autowired
	private GeneralDAO generalDAO;
	
	@Override
	public ZipCodeInfo convert(String zipCodeInfoId) {
		try {
			return generalDAO.get(ZipCodeInfo.class, Long.valueOf(zipCodeInfoId));
		} catch (AdvException e) {
			throw new RuntimeException(String.format("couldn't load zip code info from the database with this id: %d", zipCodeInfoId));
		}
	}

}
