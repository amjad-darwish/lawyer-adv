/**
 * 
 */
package com.sys.adv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sys.adv.model.beans.LawyerBean;
import com.sys.adv.model.beans.PrintConfig;
import com.sys.adv.model.dao.PrintConfigDao;

/**
 * @author amjadd
 *
 */
@Controller
@RequestMapping(value = "/lawyer/print/config")
public class ManagePrintConfigController extends GeneralAdvController {
	@Autowired
	private PrintConfigDao configDao;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView viewPrintConfig(ModelAndView modelAndView, @RequestParam("lawyerId") LawyerBean lawyerBean) {
		try {
			PrintConfig config = configDao.getById(lawyerBean.getPrintConfig().getId());
			
			modelAndView.addObject("lawyerName", lawyerBean.getName());
			modelAndView.addObject("config", config);
			modelAndView.addObject("cities", getGeneralDao().listCities());
			modelAndView.addObject("zipInfos", getGeneralDao().listZipCodeInfo());
			modelAndView.addObject("concatenatedCustomersLastNames", String.join(", ", config.getCustomersLastNames()));
			
			modelAndView.setViewName("lawyer/print/config/view");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return modelAndView;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView editPrintConfig(ModelAndView modelAndView, PrintConfig printConfig) {
		try {
			getGeneralDao().update(printConfig);

			modelAndView.setViewName("redirect:/lawyer/view");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return modelAndView;
	}
}
