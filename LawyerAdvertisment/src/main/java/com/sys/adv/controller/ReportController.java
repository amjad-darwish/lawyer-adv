/**
 * 
 */
package com.sys.adv.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sys.adv.criteria.beans.ReportSearchCriteriaDTO;
import com.sys.adv.criteria.beans.UserDetails;
import com.sys.adv.model.beans.LawyerBean;
import com.sys.adv.model.dao.LawyerDao;

/**
 * @author amjad_darwish
 *
 */
@Controller
@RequestMapping(value = "/reports")
public class ReportController extends GeneralAdvController {
	private static final Logger logger = LogManager.getLogger(ReportController.class.getName());

	@Autowired
	private LawyerDao lawyerDao;

	@RequestMapping("/viewSearchForm")
	public ModelAndView viewReportSerachForm(ModelAndView modelAndView) {
		try {
			modelAndView.setViewName("ViewSearchReportForm");
		} catch (Exception e) {
			logger.error("An error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	@RequestMapping("/listReports")
	public ModelAndView viewReportList(ModelAndView modelAndView,
			@ModelAttribute("searchCriteria") ReportSearchCriteriaDTO reportSearchCriteriaDTO) {
		try {
			UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<LawyerBean> lawyers = null;

			if (user.isLawyer()) {
				reportSearchCriteriaDTO.setLawyerId(user.getLawyer().getId());
				lawyers = new ArrayList<>(1);
				lawyers.add(user.getLawyer());
			} else {
				lawyers = lawyerDao.listLawyers();
			}

			Map<Long, Long> clientCountReports = getGeneralDao()
					.getCountPrintedReportsByLawyer(reportSearchCriteriaDTO);

			Map<String, Long> result = lawyers.stream().collect(Collectors.toMap(LawyerBean::getName,
					lawyer -> clientCountReports.getOrDefault(lawyer.getId(), 0L)));

			modelAndView.addObject("clientCountReports", new TreeMap<>(result));

			modelAndView.setViewName("forward:/reports/viewSearchForm");
		} catch (Exception e) {
			logger.error("An error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}
}
