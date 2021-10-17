package com.sys.adv.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sys.adv.annotation.Pageable;
import com.sys.adv.util.GeneralUtils;
import com.sys.adv.util.PaginationData;
import com.sys.adv.util.PaginationThreadLocal;

@Component
public class PaginationInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = LogManager.getLogger(PaginationInterceptor.class.getName());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse 
							 response, Object handler) throws Exception {
		try {
			if(handler instanceof HandlerMethod &&
					((HandlerMethod)handler).getMethod().isAnnotationPresent(Pageable.class)) {
				PaginationData paginationData = new PaginationData();
			
				Pageable pageable = ((HandlerMethod)handler).getMethod().getAnnotation(Pageable.class);
				
				paginationData.setRecordsPerPage(pageable.pageSize());
		
				int selectedPageNo = GeneralUtils.strToInt(request.getParameter("p"), 1);
					
				if(selectedPageNo < 1) {
					selectedPageNo = 1;
				}
					
				paginationData.setTargetedPage(selectedPageNo);
				
				PaginationThreadLocal.setPaginationData(paginationData);
			}
			
			return super.preHandle(request, response, handler);
		} catch(Exception e) {
			logger.error("An error", e);
			
			return false;
		}
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView) throws Exception {
		try {
			if(handler instanceof HandlerMethod &&
					((HandlerMethod)handler).getMethod().isAnnotationPresent(Pageable.class)) {
				PaginationData paginationData = PaginationThreadLocal.getPaginationData();

				long totalNoOfResult = (Long) modelAndView.getModel().get("totalNoOfResults");
				
				long totalNoOfPages = (long) Math.ceil((double)totalNoOfResult/ paginationData.getRecordsPerPage());
				
				if(totalNoOfPages < 1) {		
					totalNoOfPages = 1;
				}

				modelAndView.addObject("noOfRecordsPerPage", paginationData.getRecordsPerPage());
				modelAndView.addObject("currentPage", paginationData.getTargetedPage());
				modelAndView.addObject("firstPage", Math.max(1, paginationData.getTargetedPage()-3));
				modelAndView.addObject("lastPage", Math.min(totalNoOfPages, paginationData.getTargetedPage()+3));
				modelAndView.addObject("noOfPages", totalNoOfPages);
				
				PaginationThreadLocal.unsetPaginationData();
			}
			
			super.postHandle(request, response, handler, modelAndView);
		} catch(Exception e) {
			logger.error("An error", e);
			
			modelAndView.setViewName("abnoramlError");
		}
	}
}
