/**
 * 
 */
package com.sys.adv.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@RequestMapping(method=RequestMethod.POST)
public class DownloadController extends GeneralAdvController {
	private static final Logger logger = LogManager.getLogger(DownloadController.class.getName());
	
	@RequestMapping("/download")
	@ModelAttribute("modelAndView")
	public ModelAndView download(ModelAndView modelAndView, HttpServletResponse response, HttpServletRequest request) {
		try {
			String fileName = (String) request.getAttribute("fileName");
			
			System.out.println("fileName " + fileName);
			
			File file = new File(fileName);
			
//			response.setContentType("application/txt");

            response.setContentLength(new Long(file.length()).intValue());

            response.setHeader("Content-Disposition", "attachment; filename=\"" + request.getAttribute("downloadFileName") + "\"");
            
            byte []bytes = new byte[1024];
            
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
            	
            	while(bufferedInputStream.read(bytes) != -1) {
            		response.getOutputStream().write(bytes);
            	}
	            response.flushBuffer();
            }
		} catch (Exception e) {
			logger.error("an error", e);
		
			modelAndView.setViewName("abnormalError");
			
			return modelAndView;
		}
		
		return null;
	}
}
