/**
 * 
 */
package com.sys.adv.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sys.adv.annotation.Pageable;
import com.sys.adv.criteria.beans.UserDetails;
import com.sys.adv.model.beans.CityBean;
import com.sys.adv.model.beans.PoliceReportBean;
import com.sys.adv.model.dao.GeneralDAO;
import com.sys.adv.util.Constants;

/**
 * @author amjad_darwish
 *
 */
@Controller("homePageController")
@RequestMapping(value="/homePage")
public class HomePageController extends GeneralAdvController {
	private static final Logger logger = LogManager.getLogger(HomePageController.class.getName());
	
	@ModelAttribute("cities")
	private List<CityBean> listCities() {
		try {
			return getGeneralDao().listCities(Constants.NEW_JERSY_STATE_NAME);
		} catch (Exception e) {
			logger.error("an error", e);
		}
		
		return null;
	}
	
	@RequestMapping(value = "/search")
	@Pageable(pageSize = 30)
	public ModelAndView viewMainPage(ModelAndView modelAndView, @RequestParam(value="cityId", defaultValue="-1") int cityId) {
		try {
			GeneralDAO generalDAO = getGeneralDao();
			
			List<PoliceReportBean> uploadedPoliceReports = null;
			
			UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (user.isLawyer()) {
				uploadedPoliceReports = generalDAO.listPoliceReportsByCityName(-1, true);
			} else {
				uploadedPoliceReports = generalDAO.listPoliceReportsByCityName(cityId, false);
			}
			
			addTotalNoOfresults(modelAndView, generalDAO.countUploadedPoliceReportsByCityId(cityId));
			
			modelAndView.addObject("uploadedPoliceReports", uploadedPoliceReports);
			modelAndView.addObject("selectedCityId", cityId);
			
			modelAndView.setViewName("homePage");
		} catch (Exception e) {
			logger.error("an error", e);
			
			modelAndView.setViewName("abnormalError");
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView deletePoliceReport(ModelAndView modelAndView, @RequestParam long id, @RequestParam(value="cityId", defaultValue="-1") int cityId) {
		try {
			getGeneralDao().deletePoliceReportById(id);
			
			modelAndView.setViewName(String.format("forward:/homePage/search?cityId=%d", cityId));
		} catch (Exception e) {
			logger.error("an error", e);
			
			modelAndView.setViewName("abnormalError");
		}
		
		return modelAndView;
	}
}
