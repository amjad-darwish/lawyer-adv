/**
 * 
 */
package com.sys.adv.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author amjadd
 *
 */
@RequestMapping("/editors")
@Controller
public class TextEditorController extends GeneralAdvController {
	private static final Logger logger = LogManager.getLogger(TextEditorController.class.getName());
	
	@RequestMapping(value = "/classic")
	public ModelAndView viewTextEditor(ModelAndView modelAndView) {
		try {
			modelAndView.setViewName("editors/classic-editor");
		} catch (Exception e) {
			logger.error("an error", e);
			
			modelAndView.setViewName("abnormalError");
		}
		
		return modelAndView;
	}
}
