/**
 * 
 */
package com.sys.adv.model.dao;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sys.adv.exceptions.AdvException;
import com.sys.adv.model.beans.LawyerTemplateContent;

/**
 * @author amjadd
 *
 */
@Repository
public class LawyerTemplateContentDao extends GeneralDAO {
	private static final Logger logger = LogManager.getLogger(LawyerTemplateContentDao.class.getName());

	public void updateContent(long id, String content) throws AdvException {
		try {
			Session session = getCurrentSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaUpdate<LawyerTemplateContent> update = builder.createCriteriaUpdate(LawyerTemplateContent.class);
			Root<LawyerTemplateContent> templateContent = update.from(LawyerTemplateContent.class);
			update.set("content", content);
			update.where(builder.equal(templateContent.get("id"), id));

			session.createQuery(update).executeUpdate();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}
}
