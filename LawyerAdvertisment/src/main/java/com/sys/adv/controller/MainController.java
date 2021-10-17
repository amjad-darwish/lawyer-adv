/**
 * 
 */
package com.sys.adv.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author amjad_darwish
 *
 */
@Controller
public class MainController extends GeneralAdvController {
	private static final Logger logger = LogManager.getLogger(MainController.class.getName());
	
	@RequestMapping(value = {"/", "/home"}, method={RequestMethod.GET, RequestMethod.POST})
	@ModelAttribute
	private ModelAndView viewMainLinks(ModelAndView modelAndView) {
		try {
			modelAndView.setViewName("mainLinks");
		} catch (Exception e) {
			logger.error("an error", e);
			
			modelAndView.setViewName("abnormalError");
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/unauthorized", method={RequestMethod.GET, RequestMethod.POST})
	@ModelAttribute
	private ModelAndView unauthorized(ModelAndView modelAndView) {
		try {
			modelAndView.setViewName("unauthorized");
		} catch (Exception e) {
			logger.error("an error", e);
			
			modelAndView.setViewName("abnormalError");
		}
		
		return modelAndView;
	}
}
