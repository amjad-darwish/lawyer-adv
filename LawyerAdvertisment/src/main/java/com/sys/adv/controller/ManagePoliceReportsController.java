package com.sys.adv.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.sys.adv.model.beans.CityBean;
import com.sys.adv.model.beans.PoliceReportBean;
import com.sys.adv.util.Constants;
import com.sys.adv.util.ParameterUtil;

@Controller
@RequestMapping(value = "/policeReport")
public class ManagePoliceReportsController extends GeneralAdvController {
	private static final Logger logger = LogManager.getLogger(ManagePoliceReportsController.class.getName());

	private static final String FILE_SEPERATOR = System.getProperty("file.separator");
	private static final SimpleDateFormat ddmmyyyyFormatter = new SimpleDateFormat("ddMMyyyy");
	private static final SimpleDateFormat hhmmssFormatter = new SimpleDateFormat("hhmmss");

	@RequestMapping(value = "/viewUploadForm")
	@ModelAttribute("modelAndView")
	public ModelAndView viewUploadForm(ModelAndView modelAndView,
			@RequestParam(value = "cityId", defaultValue = "-1") int cityId) {
		try {
			List<CityBean> cityBeans = getGeneralDao().listCities(Constants.NEW_JERSY_STATE_NAME);

			modelAndView.addObject("cities", cityBeans);
			modelAndView.addObject("selectedCityId", cityId);

			modelAndView.setViewName("uploadPoliceReport");
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/uploadReport", method = RequestMethod.POST)
	public ModelAndView uploadPoliceReport(ModelAndView modelAndView,
			@RequestParam(value = "cityId", defaultValue = "-1") long cityId,
			@RequestParam("file") MultipartFile file) {
		try {
			String error = null;

			if (cityId == -1) {
				error = "Please select the city!";
			}

			if (file == null || file.isEmpty()) {
				error = "Please choose the police report to upload!";
			}

			if (error != null) {
				modelAndView.addObject("error", error);
				modelAndView.setViewName("forward:/policeReport/viewUploadForm");

				return modelAndView;
			}

			Calendar currentCalendar = Calendar.getInstance();

			CityBean city = getGeneralDao().get(CityBean.class, cityId);

			String policeReportPath = ParameterUtil.getParameterValueByName(ParameterUtil.POLICE_REPORT_UPLOAD_PATH);

			StringBuilder policeReportFullPath = new StringBuilder(policeReportPath);
			String directoryName = ddmmyyyyFormatter.format(currentCalendar.getTime());

			if (!policeReportPath.endsWith(FILE_SEPERATOR)) {
				policeReportFullPath.append(FILE_SEPERATOR);
			}

			policeReportFullPath.append(directoryName);

			File policeReportDirectory = new File(policeReportFullPath.toString());

			if (!policeReportDirectory.exists()) {
				policeReportDirectory.mkdirs();
			}

			String policeReportName = new StringBuilder(city.getName()).append("_")
					.append(hhmmssFormatter.format(currentCalendar.getTime())).append(".pdf").toString();

			String fullPath = new StringBuilder(policeReportFullPath).append(FILE_SEPERATOR).append(policeReportName)
					.toString();

			try (BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream())) {
				try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fullPath))) {
					byte[] bytes = new byte[4096];

					while (inputStream.read(bytes) != -1) {
						outputStream.write(bytes);
					}

					outputStream.flush();
				}
			}

			int noOfPages = 0;

			try (PdfDocument pdfDocument = new PdfDocument(new PdfReader(file.getInputStream()))) {
				noOfPages = pdfDocument.getNumberOfPages();
			}

			PoliceReportBean policeReportBean = new PoliceReportBean();

			policeReportBean.setFileName(
					new StringBuilder(directoryName).append(FILE_SEPERATOR).append(policeReportName).toString());
			policeReportBean.setCity(city);
			policeReportBean.setFileSize(file.getSize());
			policeReportBean.setSeen(false);
			policeReportBean.setDeleted(false);
			policeReportBean.setUploadedClaendar(currentCalendar);
			policeReportBean.setNoOfPages(noOfPages);

			getGeneralDao().save(policeReportBean);

			modelAndView.setViewName("redirect:/homePage/search?cityId=-1");
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}
}
