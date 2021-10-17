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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author amjad_darwish
 *
 */
@Controller
@RequestMapping(method={RequestMethod.POST, RequestMethod.GET})
public class ViewFileController extends GeneralAdvController {
	private static final Logger logger = LogManager.getLogger(ViewFileController.class.getName());
	
	@RequestMapping("/viewFile")
	@ResponseBody
	public void download(ModelAndView modelAndView, HttpServletResponse response, HttpServletRequest request) {
		try {
			String fileName = (String) request.getAttribute("fileName");
			
			File file = new File(fileName);
			
            response.setContentLength(Long.valueOf(file.length()).intValue());
            response.setContentType("application/pdf");
            
            byte []bytes = new byte[1024];
            
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
            	
            	while(bufferedInputStream.read(bytes) != -1) {
            		response.getOutputStream().write(bytes);
            	}
	            response.flushBuffer();
            }
		} catch (Exception e) {
			logger.error("an error", e);
			
			throw new RuntimeException(e);
		}
	}
}
