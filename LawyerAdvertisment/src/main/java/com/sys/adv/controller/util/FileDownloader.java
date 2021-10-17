/**
 * 
 */
package com.sys.adv.controller.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sys.adv.controller.DownloadController;

/**
 * @author amjadd
 *
 */
public class FileDownloader {
private static final Logger logger = LogManager.getLogger(DownloadController.class.getName());
	public static void download(String fileFullPath, String fileName, HttpServletResponse response) {
		try {
			File file = new File(fileFullPath);
			
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            
            byte []bytes = new byte[1024];
            
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
            	
            	while(bufferedInputStream.read(bytes) != -1) {
            		response.getOutputStream().write(bytes);
            	}
	            response.flushBuffer();
            }
		} catch (Exception e) {
			logger.error("an error", e);
		}
	}
}
