/**
 * 
 */
package com.sys.adv.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sys.adv.model.beans.LawyerBean;
import com.sys.adv.model.beans.Office;
import com.sys.adv.model.beans.ZipCodeInfo;
import com.sys.adv.model.dao.LawyerDao;
import com.sys.adv.model.dao.OfficeDao;

/**
 * @author amjadd
 *
 */
@Controller
@RequestMapping(value = "/lawyer/office")
public class ManageLawyerOfficeController extends GeneralAdvController {
	private static final Logger logger = LogManager.getLogger(ManageLawyerOfficeController.class.getName());

	@Autowired
	private OfficeDao officeDao;
	
	@Autowired
	private LawyerDao lawyerDao;

	@RequestMapping(value = "/view")
	@ModelAttribute("modelAndView")
	public ModelAndView view(ModelAndView modelAndView, @RequestParam("lawyerId") LawyerBean lawyer) {
		try {
			modelAndView.addObject("lawyer", lawyer);
			
			modelAndView.setViewName("lawyer/office/view");
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ModelAttribute("modelAndView")
	public ModelAndView add(ModelAndView modelAndView, @RequestParam("lawyerId") LawyerBean lawyer, Office office) {
		try {
			office.setLawyer(lawyer);
			
			officeDao.save(office);
			
			return view(modelAndView, lawyerDao.get(lawyer.getId()));
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ModelAttribute("modelAndView")
	public ModelAndView delete(@RequestParam("lawyerId") int lawyerId, @RequestParam("officeId") int officeId, ModelAndView modelAndView) {

		try {
			if (officeId <= 0) {
				throw new IllegalArgumentException(String.format("Invalid office id: %d!", officeId));
			}

			officeDao.delete(officeId);

			return view(modelAndView, lawyerDao.get(lawyerId));
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}
	
	@RequestMapping(value = "/getGeo", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ZipCodeInfo getGeo(@RequestParam("zipCode") String zipCode, HttpServletResponse response) {
		try {
			return getGeneralDao().getZipInfoByCode(zipCode.trim());
		} catch (Exception e) {
			logger.error("an error", e);
			
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return null;
		}
	}
}
