/**
 * 
 */
package com.sys.adv.model.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sys.adv.exceptions.AdvException;
import com.sys.adv.model.beans.LawyerBean;
import com.sys.adv.model.beans.LawyerTemplateContent;

/**
 * @author amjadd
 *
 */
@Repository("lawyerDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class LawyerDao extends GeneralDAO {
	private static Logger logger = LogManager.getLogger(LawyerDao.class.getName());

	public LawyerBean get(long id) throws AdvException {
		try {
			Session session = openSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<LawyerBean> criteriaQuery = builder.createQuery(LawyerBean.class);
			Root<LawyerBean> lawyerRoot = criteriaQuery.from(LawyerBean.class);

			criteriaQuery.select(lawyerRoot).where(builder.and(builder.equal(lawyerRoot.get("id"), id),
					builder.equal(lawyerRoot.get("deleted"), false)));

			return session.createQuery(criteriaQuery).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.uniqueResult();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	/**
	 * 
	 * @return
	 * @throws AdvException
	 */
	public List<LawyerBean> listLawyers() throws AdvException {
		try {
			Session session = openSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<LawyerBean> criteriaQuery = builder.createQuery(LawyerBean.class);
			Root<LawyerBean> lawyerRoot = criteriaQuery.from(LawyerBean.class);

			criteriaQuery.select(lawyerRoot).orderBy(builder.asc(lawyerRoot.get("name")))
					.where(builder.isFalse(lawyerRoot.get("deleted")));

			return session.createQuery(criteriaQuery).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.list();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	public List<LawyerBean> listLawyersForPrintLetters() throws AdvException {
		try {
			Session session = openSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<LawyerBean> criteriaQuery = builder.createQuery(LawyerBean.class);
			Root<LawyerBean> lawyerRoot = criteriaQuery.from(LawyerBean.class);
			Join<LawyerBean, LawyerTemplateContent> templateContent = lawyerRoot.join("template");

			criteriaQuery.select(lawyerRoot).orderBy(builder.asc(lawyerRoot.get("name")))
					.where(builder.and(builder.isFalse(lawyerRoot.get("deleted")),
							builder.gt(builder.length(templateContent.get("content")), 0)));

			return session.createQuery(criteriaQuery).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.list();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	public void delete(long id) throws AdvException {

		try {
			Session session = openSession();
			LawyerBean lawyerBean = get(LawyerBean.class, id);
			lawyerBean.setDeleted(true);
			session.update(lawyerBean);
			session.flush();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	public LawyerBean findByName(String name) throws AdvException {
		try {
			Session session = openSession();
			return (LawyerBean) session.createCriteria(LawyerBean.class)
					.add(Restrictions.and(Restrictions.ilike("name", name), Restrictions.eq("deleted", false)))
					.uniqueResult();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}
}
