package com.sys.adv.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.sys.adv.annotation.Pageable;
import com.sys.adv.exceptions.AdvException;
import com.sys.adv.model.beans.PoliceRecordBean;
import com.sys.adv.model.beans.PoliceReportBean;
import com.sys.adv.model.beans.ZipCodeInfo;
import com.sys.adv.util.ParameterUtil;

@Controller
@RequestMapping(value = "/policeRecord")
public class ManagePoliceRecordController extends GeneralAdvController {
	private static final Logger logger = LogManager.getLogger(ManagePoliceRecordController.class.getName());

	private static final String FILE_SEPERATOR = System.getProperty("file.separator");

	private static final SimpleDateFormat ddmmyyyyFormatter = new SimpleDateFormat("ddMMyyyy");
	private static final SimpleDateFormat hhmmssFormatter = new SimpleDateFormat("hhmmss");

	@RequestMapping(value = "/viewRecords")
	public ModelAndView viewAddIssue(ModelAndView modelAndView,
			@RequestParam(value = "policeReportId", defaultValue = "0") long policeReportId,
			@RequestParam(value = "policeRecordId", defaultValue = "0") long policeRecordId) {
		try {
			PoliceReportBean policeReportBean = getGeneralDao().get(PoliceReportBean.class, policeReportId);

			modelAndView.addObject("policeReport", policeReportBean);

			modelAndView.setViewName("ViewAddingPoliceRecordForm");
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/addEditRecordView")
	public ModelAndView addEditIssue(ModelAndView modelAndView,
			@RequestParam(value = "policeReportId", defaultValue = "0") long policeReportId,
			@RequestParam(value = "policeRecordId", defaultValue = "0") long policeRecordId) {
		try {
			List<String> titles = new ArrayList<String>();
			titles.add("Mr");
			titles.add("Mrs");
			titles.add("Ms");
			titles.add("Miss");

			modelAndView.addObject("titles", titles);

			PoliceReportBean policeReportBean = getGeneralDao().get(PoliceReportBean.class, policeReportId);
			PoliceRecordBean policeRecordBean = getGeneralDao().get(PoliceRecordBean.class, policeRecordId);

			modelAndView.addObject("policeReport", policeReportBean);
			modelAndView.addObject("policeRecordBean", policeRecordBean);

			modelAndView.setViewName("AddEditPoliceRecord");
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/viewReport")
	public ModelAndView viewPoliceReport(ModelAndView modelAndView, HttpServletRequest request,
			@RequestParam long policeReportId) {
		try {
			PoliceReportBean policeReportBean = getGeneralDao().get(PoliceReportBean.class, policeReportId);

			String policeReportPath = ParameterUtil.getParameterValueByName(ParameterUtil.POLICE_REPORT_UPLOAD_PATH);

			StringBuilder policeReportFullPath = new StringBuilder(policeReportPath);

			if (!policeReportPath.endsWith(FILE_SEPERATOR)) {
				policeReportFullPath.append(FILE_SEPERATOR);
			}

			policeReportFullPath.append(policeReportBean.getFileName());

			request.setAttribute("fileName", policeReportFullPath.toString());

			modelAndView.setViewName("forward:/viewFile");
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/addEditRecord", method = RequestMethod.POST)
	public ModelAndView addEditRecord(ModelAndView modelAndView, PoliceRecordBean policeRecordBean) {
		try {
			PoliceReportBean policeReportBean = getGeneralDao().get(PoliceReportBean.class,
					policeRecordBean.getPoliceReportId());

			int lastRequiredPage = policeRecordBean.getFirstPage() + policeRecordBean.getNoOfPages() - 1;

			if (policeRecordBean.getFirstPage() < 1 || lastRequiredPage > policeReportBean.getNoOfPages()) {
				String error = String.format(
						"Please choose valid pages, the entered ones are [%d, %d], but the valid pages should be [1, %d]",
						policeRecordBean.getFirstPage(), lastRequiredPage, policeReportBean.getNoOfPages());

				modelAndView.addObject("error", error);
			} else {
				policeRecordBean.setCityBean(policeReportBean.getCity());
				policeRecordBean.setPoliceReportBean(policeReportBean);

				String partRecordFileName = extractPoliceRecordPDFFromPoliceReport(policeRecordBean);

				policeRecordBean.setPartPoliceFileName(partRecordFileName);

				if (policeReportBean.getNoOfPages() < policeRecordBean.getFirstPage() + policeRecordBean.getNoOfPages()
						- 1) {
					policeRecordBean
							.setNoOfPages(policeReportBean.getNoOfPages() - policeRecordBean.getFirstPage() + 1);
				}

				if (policeRecordBean.getFullZipCode().indexOf('-') > -1) {
					policeRecordBean.setZipCode(policeRecordBean.getFullZipCode().split("-")[0]);
				} else {
					policeRecordBean.setZipCode(policeRecordBean.getFullZipCode());
				}
				
				if (policeRecordBean.getLongitude() == 0 || policeRecordBean.getLatitude() == 0) {
					ZipCodeInfo zipCodeInfo = getGeneralDao().getZipInfoByCode(policeRecordBean.getZipCode());

					if (zipCodeInfo != null && policeRecordBean.getLongitude() == 0) {
						policeRecordBean.setLongitude(zipCodeInfo.getLongitude());
					}

					if (zipCodeInfo != null && policeRecordBean.getLatitude() == 0) {
						policeRecordBean.setLatitude(zipCodeInfo.getLatitude());
					}
				}

				if (policeRecordBean.getId() != 0) {
					getGeneralDao().update(policeRecordBean);
				} else {
					policeRecordBean.setEntryDate(Calendar.getInstance());
					getGeneralDao().save(policeRecordBean);
				}
			}

			modelAndView.setViewName("forward:/policeRecord/addEditRecordView");
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/completeReport")
	public ModelAndView complete(ModelAndView modelAndView,
			@RequestParam(value = "policeReportId", defaultValue = "0") long policeReportId) {
		try {
			PoliceReportBean policeReportBean = getGeneralDao().get(PoliceReportBean.class, policeReportId);

			policeReportBean.setSeen(true);

			getGeneralDao().update(policeReportBean);

			modelAndView.setViewName("redirect:/homePage/search");
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/view")
	@ResponseBody
	public void viewReportRecord(ModelAndView modelAndView, HttpServletResponse response,
			@RequestParam(value = "policeRecordId", defaultValue = "0") long policeRecordId) {
		try {
			PoliceRecordBean policeRecordBean = getGeneralDao().get(PoliceRecordBean.class, policeRecordId);

			String policeReportPath = ParameterUtil.getParameterValueByName(ParameterUtil.POLICE_REPORT_UPLOAD_PATH);

			StringBuilder policeReportFullPath = new StringBuilder(policeReportPath);

			if (!policeReportPath.endsWith(FILE_SEPERATOR)) {
				policeReportFullPath.append(FILE_SEPERATOR);
			}

			policeReportFullPath.append(policeRecordBean.getPartPoliceFileName());

			File file = new File(policeReportFullPath.toString());

			response.setContentType("application/pdf");

			try (PdfDocument source = new PdfDocument(new PdfReader(file));
					PdfDocument destination = new PdfDocument(new PdfWriter(response.getOutputStream()))) {
				destination.setDefaultPageSize(PageSize.LETTER);
				source.copyPagesTo(1, source.getNumberOfPages(), destination);
			}
		} catch (Exception e) {
			logger.error("an error", e);

			throw new RuntimeException(e);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/delete")
	public ModelAndView deleteRecord(@ModelAttribute ModelAndView modelAndView,
			@RequestParam(value = "policeRecordId", defaultValue = "0") long policeRecordId) {
		try {
			PoliceRecordBean policeRecordBean = getGeneralDao().get(PoliceRecordBean.class, policeRecordId);

			policeRecordBean.setDeleted(true);

			getGeneralDao().update(policeRecordBean);

			modelAndView
					.setViewName("forward:/policeRecord/listRecords/" + policeRecordBean.getPoliceReportBean().getId());
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public ModelAndView updateRecord(@ModelAttribute ModelAndView modelAndView,
			@RequestParam(value = "policeReportId", defaultValue = "0") long policeReportId,
			@RequestParam(value = "policeRecordId", defaultValue = "0") long policeRecordId) {
		try {
			modelAndView.setViewName("forward:/policeRecord/addEditRecordView");
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	@Pageable(pageSize = 10)
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/listRecords/{policeReportId}")
	public ModelAndView listPoliceRecords(@ModelAttribute ModelAndView modelAndView,
			@PathVariable(value = "policeReportId") long policeReportId) {
		try {
			List<PoliceRecordBean> policeRecordBeans = getGeneralDao()
					.listPoliceRecordsByPoliceReportId(policeReportId);

			addTotalNoOfresults(modelAndView, getGeneralDao().countPoliceRecordsByPoliceReportId(policeReportId));
			modelAndView.addObject("policeRecords", policeRecordBeans);
			modelAndView.addObject("policeReportId", policeReportId);

			modelAndView.setViewName("listPoliceRecords");
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	/**
	 * 
	 * @param policeRecordBean
	 * @param policeReportBean
	 * @param outputStream
	 * @throws AdvException
	 * @throws IOException
	 * @throws COSVisitorException
	 */
	private String extractPoliceRecordPDFFromPoliceReport(PoliceRecordBean policeRecordBean)
			throws AdvException, IOException {
		String policeReportPath = ParameterUtil.getParameterValueByName(ParameterUtil.POLICE_REPORT_UPLOAD_PATH);

		StringBuilder policeReportFullPath = new StringBuilder(policeReportPath);
		StringBuilder policeRecordFullPath = new StringBuilder(policeReportPath);

		Calendar currentCalendar = Calendar.getInstance();

		if (!policeReportPath.endsWith(FILE_SEPERATOR)) {
			policeReportFullPath.append(FILE_SEPERATOR);
			policeRecordFullPath.append(FILE_SEPERATOR);
		}

		policeReportFullPath.append(policeRecordBean.getPoliceReportBean().getFileName());

		File policeRecordDirectory = new File(
				policeRecordFullPath.toString() + ddmmyyyyFormatter.format(currentCalendar.getTime()));

		if (!policeRecordDirectory.exists()) {
			policeRecordDirectory.mkdirs();
		}

		String policePartRecordName = new StringBuilder(ddmmyyyyFormatter.format(currentCalendar.getTime()))
				.append(FILE_SEPERATOR).append(policeRecordBean.getCityBean().getName()).append("_(")
				.append(policeRecordBean.getFirstPage()).append("_").append(policeRecordBean.getNoOfPages())
				.append(")_").append(hhmmssFormatter.format(currentCalendar.getTime())).append(".pdf").toString();

		policeRecordFullPath.append(policePartRecordName);

		try (PdfDocument source = new PdfDocument(new PdfReader(new File(policeReportFullPath.toString())));
				PdfDocument destination = new PdfDocument(new PdfWriter(new File(policeRecordFullPath.toString())))) {
			destination.setDefaultPageSize(PageSize.LETTER);
			List<Integer> requiredPages = IntStream
					.range(Math.max(1, policeRecordBean.getFirstPage()),
							Math.min(source.getNumberOfPages() + 1,
									policeRecordBean.getFirstPage() + policeRecordBean.getNoOfPages()))
					.boxed().collect(Collectors.toList());

			source.copyPagesTo(requiredPages, destination);
		}

		return policePartRecordName;
	}
}