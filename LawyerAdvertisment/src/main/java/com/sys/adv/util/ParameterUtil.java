/**
 * 
 */
package com.sys.adv.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sys.adv.exceptions.AdvException;
import com.sys.adv.model.beans.ParameterBean;

/**
 * @author amjad_darwish
 *
 */

@Repository
public abstract class ParameterUtil {
	public static final String POLICE_REPORT_UPLOAD_PATH = "POLICE_REPORT_UPLOAD_PATH";
	public static final String PRINTED_RECORD_PATH = "PRINTED_RECORD_PATH";
	public static final String LAWYER_BACKGROUND_UPLOAD_PATH = "LAWYER_BACKGROUND_UPLOAD_PATH";
	public static final String GENERATED_BARCODE_PATH = "GENERATED_BARCODE_PATH";
	
	private static final Logger logger = LogManager.getLogger(ParameterUtil.class.getName());

	private static Map<String, ParameterBean> parameters;
	
	private static boolean initialized = false;
	
	static {
		parameters = new HashMap<String, ParameterBean>();
	}
	
	private ParameterUtil() {
	}
	
	/**
	 * 
	 * @param parameters
	 */
	public static synchronized void init (List<ParameterBean> parameters) {
		if(!initialized) {
			for(ParameterBean parameter : parameters) {
				ParameterUtil.parameters.put(parameter.getName(), parameter);
			}
			
			initialized = true;
		} else {
			logger.info("The parameter Util is already initialized!");
		}
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws AdvException 
	 */
	public static ParameterBean getParameterBeanByName(String name) throws AdvException {
		return parameters.get(name);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws AdvException 
	 */
	public static String getParameterValueByName(String name) throws AdvException {
		return getParameterBeanByName(name).getValue();
	}
}
