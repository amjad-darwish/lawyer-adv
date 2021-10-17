package com.sys.adv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.sys.adv.model.dao.GeneralDAO;
@Controller
public class GeneralAdvController {
	@Autowired
	private GeneralDAO generalDAO;
	
	/**
	 * 
	 * @return
	 */
	protected GeneralDAO getGeneralDao () {
		return generalDAO;
	}
	
	/**
	 * 
	 * @param modelAndView
	 * @param totalNoOfResults
	 */
	protected void addTotalNoOfresults (ModelAndView modelAndView, long totalNoOfResults) {
		modelAndView.addObject("totalNoOfResults", totalNoOfResults);
	}
}
