/**
 * 
 */
package com.sys.adv.model.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sys.adv.exceptions.AdvException;
import com.sys.adv.model.beans.LawyerBean;
import com.sys.adv.model.beans.PrintConfig;

/**
 * @author amjadd
 *
 */
@Repository
@Transactional
public class PrintConfigDao extends GeneralDAO {
	public PrintConfig getByLawyerId(long lawyerId) throws AdvException {
		Session session = openSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<PrintConfig> criteriaQuery = builder.createQuery(PrintConfig.class);
		Root<LawyerBean> lawyerRoot = criteriaQuery.from(LawyerBean.class);

		criteriaQuery.select(lawyerRoot.get("printConfig")).where(
				builder.and(builder.equal(lawyerRoot.get("id"), lawyerId), builder.isFalse(lawyerRoot.get("deleted"))));

		PrintConfig config = session.createQuery(criteriaQuery).uniqueResult();

		// detach from the session, so the below set methods will not generate delete
		// and insert statements
		session.detach(config);

		return publishOtherFields(config);
	}

	public PrintConfig getById(long id) throws AdvException {
		Session session = openSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<PrintConfig> criteriaQuery = builder.createQuery(PrintConfig.class);
		Root<PrintConfig> printConfig = criteriaQuery.from(PrintConfig.class);

		criteriaQuery.select(printConfig).where(builder.and(builder.equal(printConfig.get("id"), id)));

		PrintConfig config = session.createQuery(criteriaQuery).uniqueResult();

		// detach from the session, so the below set methods will not generate delete
		// and insert statements
		session.detach(config);
		
		return publishOtherFields(config);
	}
	
	private PrintConfig publishOtherFields(PrintConfig config) throws AdvException {
		config.setExcludeCitiesIds(getExcludeCitiesIdsByConfigId(config.getId()));
		config.setIncludeCitiesIds(getIncludeCitiesIdsByConfigId(config.getId()));
		config.setZipCodes(getZipCodesByConfigId(config.getId()));
		config.setCustomersLastNames(getCustomerLastNames(config.getId()));
		
		if (config.getIncludeCitiesIds() != null && !config.getIncludeCitiesIds().isEmpty()) {
			config.setIncludeCitiesNames(getCitiesNames(config.getIncludeCitiesIds()));
		} else {
			config.setIncludeCitiesNames(Collections.emptyList());
		}
		
		if (config.getExcludeCitiesIds() != null && !config.getExcludeCitiesIds().isEmpty()) {
			config.setExcludeCitiesNames(getCitiesNames(config.getExcludeCitiesIds()));
		} else {
			config.setExcludeCitiesNames(Collections.emptyList());
		}
		
		return config;
	}

	private List<Long> getZipCodesByConfigId(long id) {
		Session session = openSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<PrintConfig> printConfig = criteriaQuery.from(PrintConfig.class);
		Join<PrintConfig, Long> zipCodes = printConfig.join("zipCodes");

		criteriaQuery.select(zipCodes).where(builder.and(builder.equal(printConfig.get("id"), id)));

		return session.createQuery(criteriaQuery).list();
	}

	private List<Long> getIncludeCitiesIdsByConfigId(long id) {
		Session session = openSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<PrintConfig> printConfig = criteriaQuery.from(PrintConfig.class);
		Join<PrintConfig, Long> includeCitiesIds = printConfig.join("includeCitiesIds");

		criteriaQuery.select(includeCitiesIds).where(builder.and(builder.equal(printConfig.get("id"), id)));

		return session.createQuery(criteriaQuery).list();
	}

	private List<Long> getExcludeCitiesIdsByConfigId(long id) {
		Session session = openSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<PrintConfig> printConfig = criteriaQuery.from(PrintConfig.class);
		Join<PrintConfig, Long> excludeCitiesIds = printConfig.join("excludeCitiesIds");

		criteriaQuery.select(excludeCitiesIds).where(builder.and(builder.equal(printConfig.get("id"), id)));

		return session.createQuery(criteriaQuery).list();
	}
	
	
	private List<String> getCustomerLastNames(long id) {
		Session session = openSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
		Root<PrintConfig> printConfig = criteriaQuery.from(PrintConfig.class);
		Join<PrintConfig, String> customersLastNames = printConfig.join("customersLastNames");

		criteriaQuery.select(customersLastNames).where(builder.and(builder.equal(printConfig.get("id"), id)));

		return session.createQuery(criteriaQuery).list();
	}
	
}
