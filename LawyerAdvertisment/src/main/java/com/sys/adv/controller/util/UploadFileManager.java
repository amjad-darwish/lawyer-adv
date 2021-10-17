/**
 * 
 */
package com.sys.adv.controller.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.web.multipart.MultipartFile;

import com.sys.adv.exceptions.AdvException;
import com.sys.adv.model.beans.LawyerBackground;
import com.sys.adv.model.beans.LawyerBean;
import com.sys.adv.util.ParameterUtil;

/**
 * @author amjadd
 *
 */
public class UploadFileManager {
	private static final String FILE_SEPERATOR = System.getProperty("file.separator");
	private static final SimpleDateFormat ddmmyyyyFormatter = new SimpleDateFormat("ddMMyyyy");
	private static final SimpleDateFormat hhmmssFormatter = new SimpleDateFormat("hhmmss");
	
	public static LawyerBackground uploadBackground(MultipartFile background, LawyerBean lawyer)
			throws AdvException, IOException {
		Calendar current = Calendar.getInstance();

		String templateName = uploadFile(lawyer, current, background);

		LawyerBackground lawyerBackground = new LawyerBackground();

		lawyerBackground.setName(templateName);
		lawyerBackground.setDescribableName(background.getOriginalFilename());
		lawyerBackground.setLawyerBean(lawyer);
		lawyerBackground.setUploadedClaendar(current);

		return lawyerBackground;
	}

	private static String uploadFile(LawyerBean lawyer, Calendar current, MultipartFile file)
			throws AdvException, IOException {
		String directoryPath = getFileDirectoryPath(current);
		String fileName = generateFileName(lawyer, current, file);
		String fullPath = new StringBuilder(directoryPath).append(FILE_SEPERATOR).append(fileName).toString();

		try (BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream())) {
			try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fullPath))) {
				byte[] bytes = new byte[1024];

				while (inputStream.read(bytes) != -1) {
					outputStream.write(bytes);
				}

				outputStream.flush();
			}
		}

		return new StringBuilder(directoryPath.substring(directoryPath.lastIndexOf(FILE_SEPERATOR) + 1))
				.append(FILE_SEPERATOR).append(fileName).toString();
	}

	private static String generateFileName(LawyerBean lawyer, Calendar current, MultipartFile file) {
		return new StringBuilder(lawyer.getName()).append("_").append(hhmmssFormatter.format(current.getTime()))
				.append(extractExtenstion(file)).toString();
	}

	private static String extractExtenstion(MultipartFile file) {
		return file.getOriginalFilename().contains(".")
				? file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'))
				: "";
	}

	private static String getFileDirectoryPath(Calendar current) throws AdvException {
		String path = ParameterUtil.getParameterValueByName(ParameterUtil.LAWYER_BACKGROUND_UPLOAD_PATH);

		StringBuilder fullPath = new StringBuilder(path);

		if (!path.endsWith(FILE_SEPERATOR)) {
			fullPath.append(FILE_SEPERATOR);
		}

		fullPath.append(ddmmyyyyFormatter.format(current.getTime()));

		File policeReportDirectory = new File(fullPath.toString());
		
		if (!policeReportDirectory.exists()) {
			policeReportDirectory.mkdirs();
		}

		return fullPath.toString();
	}

	public static String generateFillPath(LawyerBean lawyer) throws AdvException {
		String directoryPath = ParameterUtil.getParameterValueByName(ParameterUtil.LAWYER_BACKGROUND_UPLOAD_PATH);
		String fileName = lawyer.getBackground().getName();
		
		StringBuilder fullPath = new StringBuilder(directoryPath);
		
		if(!directoryPath.endsWith(FILE_SEPERATOR)) {
			fullPath.append(FILE_SEPERATOR);
		}
		
		fullPath.append(fileName);
		
		return fullPath.toString();
	}
}
