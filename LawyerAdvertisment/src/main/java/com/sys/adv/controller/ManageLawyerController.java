package com.sys.adv.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sys.adv.controller.util.FileDownloader;
import com.sys.adv.controller.util.UploadFileManager;
import com.sys.adv.model.beans.LawyerBean;
import com.sys.adv.model.beans.LawyerTemplateContent;
import com.sys.adv.model.beans.PrintConfig;
import com.sys.adv.model.dao.LawyerDao;
import com.sys.adv.util.Constants;

@Controller
@RequestMapping(value = "/lawyer")
public class ManageLawyerController extends GeneralAdvController {
	private static final Logger logger = LogManager.getLogger(ManageLawyerController.class.getName());
	
	@Autowired
	private LawyerDao lawyerDao;

	private ModelAndView view(ModelAndView modelAndView) {
		return view(-1, modelAndView);
	}

	@RequestMapping(value = "/view")
	@ModelAttribute("modelAndView")
	public ModelAndView view(@RequestParam(value = "lawyerId", defaultValue = "-1") long id,
			ModelAndView modelAndView) {
		try {
			if (id != -1) {
				modelAndView.addObject("lawyer", lawyerDao.get(id));
			}

			modelAndView.addObject("lawyers", lawyerDao.listLawyers());
			
			modelAndView.setViewName("lawyer/manageLawyer");
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/addEdit", method = RequestMethod.POST)
	@ModelAttribute("modelAndView")
	@Transactional
	public ModelAndView add(@RequestParam(defaultValue = "-1") long id, @RequestParam String name,
			@RequestParam(defaultValue = "false") boolean useBackground,
			@RequestParam(defaultValue = "false") boolean usePoliceReport,
			@RequestParam(defaultValue = "0") int noOfPoliceReportPages,
			@RequestParam("background") MultipartFile background, ModelAndView modelAndView) {

		try {
			if (name == null || name.trim().length() == 0) {
				throw new IllegalArgumentException("Name shouldn't be null/empty!");
			}

			LawyerBean existBean = null;

			if (id == -1) {
				existBean = lawyerDao.findByName(name);
			} else {
				existBean = lawyerDao.get(id);
			}

			boolean addOperationWithoutBackground = (id == -1 && (background == null || background.isEmpty()));
			boolean updateOperationWithoutBackground = (id != -1 && existBean.getBackground() == null
					&& (background == null || background.isEmpty()));

			if (useBackground && (addOperationWithoutBackground || updateOperationWithoutBackground)) {
				throw new IllegalArgumentException("Background shouldn't be null or empty!");
			}

			if (existBean != null && id == -1) {
				modelAndView.addObject("error", "User name is invalid(taken), please try another name!");
			} else {
				LawyerBean lawyer = null;

				if (id == -1) { // insert
					lawyer = new LawyerBean();

					lawyer.setName(name);
					lawyer.setTemplate(new LawyerTemplateContent());
					lawyer.setPrintConfig(new PrintConfig());
				} else { // update
					lawyer = existBean;
					
					if (!usePoliceReport) {
						noOfPoliceReportPages = lawyer.getNoOfPoliceReportPages();
					}
				}

				if (background != null && !background.isEmpty()) {
					lawyer.setBackground(UploadFileManager.uploadBackground(background, lawyer));
				}

				lawyer.setUseBackground(useBackground);
				lawyer.setUsePoliceReport(usePoliceReport);
				lawyer.setNoOfPoliceReportPages(noOfPoliceReportPages);

				lawyerDao.saveOrUpdate(lawyer);
			}

			return view(modelAndView);
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ModelAttribute("modelAndView")
	public ModelAndView delete(@RequestParam int lawyerId, ModelAndView modelAndView) {

		try {
			if (lawyerId <= 0) {
				throw new IllegalArgumentException(String.format("Invalid lawyer id: %d!", lawyerId));
			}

			if (lawyerId != Constants.UNDELETABLE_LAWYER_ID) {
				lawyerDao.delete(lawyerId);
			}

			return view(modelAndView);
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/download")
	@ResponseBody
	public void downloadTemplate(ModelAndView modelAndView, @RequestParam(value = "lawyerId") long id,
			HttpServletResponse response) throws IOException {
		try {
			LawyerBean lawyer = lawyerDao.get(id);

			String fullPath = UploadFileManager.generateFillPath(lawyer);
			String fileName = lawyer.getBackground().getDescribableName();

			FileDownloader.download(fullPath, fileName, response);
		} catch (Exception e) {
			logger.error("an error", e);

			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}
}
