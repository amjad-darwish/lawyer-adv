/**
 * 
 */
package com.sys.adv.model.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sys.adv.exceptions.AdvException;
import com.sys.adv.model.beans.Office;

/**
 * @author amjadd
 *
 */
@Repository("officeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class OfficeDao extends GeneralDAO {
	private static Logger logger = LogManager.getLogger(OfficeDao.class.getName());

	public Office get(long id) throws AdvException {
		try {
			Session session = openSession();
			
			Office office = (Office) session.createCriteria(Office.class)
					.add(Restrictions.and(Restrictions.eq("deleted", false), Restrictions.eq("id", id)))
					.uniqueResult();
			
			return office;
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}
	
	public List<Office> findAllByLawyerId(long lawyerId) throws AdvException {
		try {
			Session session = openSession();
			
			List<Office> offices = session.createCriteria(Office.class)
					.add(Restrictions.and(Restrictions.eq("deleted", false), Restrictions.eq("lawyer.id", lawyerId)))
					.addOrder(Order.asc("name"))
					.list();
			
			return offices;
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}
	

	public void delete(long id) throws AdvException {
		try {
			Session session = getCurrentSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaUpdate<Office> update = builder.createCriteriaUpdate(Office.class);
			Root<Office> office = update.from(Office.class);
			update.set("deleted", true);
			update.where(builder.equal(office.get("id"), id));

			session.createQuery(update).executeUpdate();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}
}
