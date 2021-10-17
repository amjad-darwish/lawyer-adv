/**
 * 
 */
package com.sys.adv.initializers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sys.adv.model.beans.ParameterBean;
import com.sys.adv.model.dao.GeneralDAO;
import com.sys.adv.util.ParameterUtil;

/**
 * @author amjad_darwish
 *
 */
@Component
public class ParametersInitializer implements InitializingBean {
	private static final Logger logger = LogManager.getLogger(ParametersInitializer.class.getName());

	@Autowired
	private GeneralDAO generalDAO;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			List<ParameterBean> parameters = generalDAO.listParameters();
			ParameterUtil.init(parameters);
		} catch (Exception e) {
			logger.error("An error", e);

			throw e;
		}
	}
}
