/**
 * 
 */
package com.sys.adv.controller;

import java.io.File;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.sys.adv.annotation.Pageable;
import com.sys.adv.criteria.beans.PoliceRecordSearchDTO;
import com.sys.adv.model.beans.PoliceRecordBean;
import com.sys.adv.util.ParameterUtil;

/**
 * @author amjad_darwish
 *
 */
@Controller
@RequestMapping(value = "/searchPoliceRecord")
public class SearchPoliceRecordsController extends GeneralAdvController {
	private static final Logger logger = LogManager.getLogger(SearchPoliceRecordsController.class.getName());

	private static final String FILE_SEPERATOR = System.getProperty("file.separator");

	@Pageable(pageSize = 15)
	@RequestMapping(value = "/listResult")
	public ModelAndView listSearchResultForm(ModelAndView modelAndView, PoliceRecordSearchDTO policeRecordSearchBean,
			HttpSession session, @RequestParam(defaultValue = "false") boolean oldCriteria) {
		try {
			if (oldCriteria) {
				policeRecordSearchBean = (PoliceRecordSearchDTO) session.getAttribute("policeRecordSearchBean");
			}

			session.setAttribute("policeRecordSearchBean", policeRecordSearchBean);
			modelAndView.addObject("searchBean", policeRecordSearchBean);
			modelAndView.addObject("policeRecords", getGeneralDao().listPoliceRecords(policeRecordSearchBean));
			
			addTotalNoOfresults(modelAndView, getGeneralDao().countPoliceRecords(policeRecordSearchBean));

			modelAndView.setViewName("ViewSearchRecordForm");
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	@RequestMapping("/view")
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
				source.copyPagesTo(1, source.getNumberOfPages(), destination);
			}
		} catch (Exception e) {
			logger.error("an error", e);

			throw new RuntimeException(
					String.format("An error occured while reading a police record with this id: ", policeRecordId));
		}
	}
}
