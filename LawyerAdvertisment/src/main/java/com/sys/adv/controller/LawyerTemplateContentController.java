/**
 * 
 */
package com.sys.adv.controller;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.sys.adv.model.beans.LawyerBean;
import com.sys.adv.model.beans.LawyerTemplateContent;
import com.sys.adv.model.dao.LawyerTemplateContentDao;
import com.sys.adv.util.ParameterUtil;

/**
 * @author amjadd
 *
 */
@RequestMapping(value = "/lawyer/template")
@Controller
public class LawyerTemplateContentController extends GeneralAdvController {
	private static final Logger logger = LogManager.getLogger(LawyerTemplateContentController.class.getName());

	@Autowired
	private LawyerTemplateContentDao lawyerTemplateContentDao;

	@RequestMapping(value = "/view")
	public ModelAndView viewTextEditor(ModelAndView modelAndView, @RequestParam long id) {
		try {
			modelAndView.addObject("template", getGeneralDao().get(LawyerTemplateContent.class, id));
			modelAndView.setViewName("lawyer/template");
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveTemplate(ModelAndView modelAndView, @RequestParam long id, @RequestParam String content) {
		try {
			lawyerTemplateContentDao.updateContent(id, content);

			modelAndView.setViewName("redirect:/lawyer/view");
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}
	
	@RequestMapping(value = "/cancel")
	public ModelAndView cancelTemplate(ModelAndView modelAndView) {
		modelAndView.setViewName("redirect:/lawyer/view");
		return modelAndView;
	}

	@RequestMapping(value = "/try", method = RequestMethod.POST)
	@ResponseBody
	public void generateTemplate(ModelAndView modelAndView, HttpServletResponse response, @RequestParam long id,
			@RequestParam String content) {
		try {
			LawyerTemplateContent template = getGeneralDao().get(LawyerTemplateContent.class, id);
			LawyerBean lawyerBean = template.getLawyer();

			response.setContentType("application/pdf");
			
			if (content == null || content.isBlank()) {
				content = "<p></p>";
			}
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ConverterProperties converterProperties = new ConverterProperties();
			try (PdfDocument lawyerTemplate = new PdfDocument(new PdfWriter(byteArrayOutputStream))) {
				lawyerTemplate.setDefaultPageSize(PageSize.LETTER);
				HtmlConverter.convertToPdf(content, lawyerTemplate, converterProperties);
			}
			
			
			try (PdfDocument printedDocument = new PdfDocument(
					new PdfReader(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())),
					new PdfWriter(response.getOutputStream()))) {
				printedDocument.setDefaultPageSize(PageSize.LETTER);
				
				if (lawyerBean.isUseBackground() && lawyerBean.getBackground() != null) {
					String lawyerBackgroundPath = ParameterUtil
							.getParameterValueByName(ParameterUtil.LAWYER_BACKGROUND_UPLOAD_PATH);
					StringBuilder lawyerBackgroundFullPath = new StringBuilder(lawyerBackgroundPath);
					lawyerBackgroundFullPath.append(System.getProperty("file.separator"))
							.append(lawyerBean.getBackground().getName());

					for (int page = 1; page <= printedDocument.getNumberOfPages(); page++) {
						 PdfCanvas canvas = new PdfCanvas(printedDocument.getPage(page).newContentStreamBefore(),
								 printedDocument.getPage(page).getResources(), printedDocument);
						 
						try (PdfDocument background = new PdfDocument(new PdfReader(lawyerBackgroundFullPath.toString()))) {
							PdfXObject mergedPage = background.getFirstPage().copyAsFormXObject(printedDocument);
				            canvas.addXObject(mergedPage, 0, 0);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("an error", e);

			throw new RuntimeException(e);
		}
	}
}
