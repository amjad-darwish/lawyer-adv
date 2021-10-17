package com.sys.adv.model.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.LockModeType;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sys.adv.criteria.beans.PoliceRecordSearchDTO;
import com.sys.adv.criteria.beans.ReportSearchCriteriaDTO;
import com.sys.adv.criteria.beans.UnPrintedPoliceRecordSearchCriteriaDTO;
import com.sys.adv.exceptions.AdvException;
import com.sys.adv.model.beans.CityBean;
import com.sys.adv.model.beans.FaultCode;
import com.sys.adv.model.beans.LawyerBean;
import com.sys.adv.model.beans.ParameterBean;
import com.sys.adv.model.beans.PoliceRecordBean;
import com.sys.adv.model.beans.PoliceRecordWithCode;
import com.sys.adv.model.beans.PoliceReportBean;
import com.sys.adv.model.beans.PrintConfig;
import com.sys.adv.model.beans.PrintedPoliceRecordBean;
import com.sys.adv.model.beans.State;
import com.sys.adv.model.beans.ZipCodeInfo;
import com.sys.adv.util.Constants;
import com.sys.adv.util.PaginationThreadLocal;

@Transactional(propagation = Propagation.REQUIRED)
@Repository("generalDAO")
public class GeneralDAO {
	private static Logger logger = LogManager.getLogger(GeneralDAO.class.getName());
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 
	 * @return
	 */
	Session openSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 
	 * @return
	 */
	StatelessSession openStatelessSession() {
		return sessionFactory.openStatelessSession();
	}

	/**
	 * 
	 * @return
	 */
	Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 
	 * @param <T>
	 * @param object
	 * @param number
	 * @return
	 * @throws AdvException
	 */
	public <T> T get(Class<T> clazz, Number primaryKey) throws AdvException {
		try {
			Session session = openSession();
			return (T) session.get(clazz, primaryKey);
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	/**
	 * 
	 * @param object
	 * @return
	 * @throws AdvException
	 */
	public long save(Object object) throws AdvException {
		try {
			Session session = getCurrentSession();
			return (long) session.save(object);
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	/**
	 * 
	 * @param object
	 * @return
	 * @throws AdvException
	 */
	public void update(Object object) throws AdvException {
		try {
			Session session = openSession();
			session.update(object);
			session.flush();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	/**
	 * 
	 * @param object
	 * @return
	 * @throws AdvException
	 */
	public void saveOrUpdate(Object object) throws AdvException {
		try {
			Session session = openSession();
			session.saveOrUpdate(object);
			session.flush();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	/**
	 * 
	 * @param object
	 * @throws AdvException
	 */
	public void delete(Object object) throws AdvException {
		try {
			Session session = openSession();
			session.delete(object);
			session.flush();
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
	public List<CityBean> listCities() throws AdvException {
		try {
			Session session = openSession();
			return session.createCriteria(CityBean.class).addOrder(Order.asc("name")).list();
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
	public List<CityBean> listCities(String stateName) throws AdvException {
		try {
			Session session = openSession();
			return session.createCriteria(CityBean.class).createAlias("county", "county")
					.createAlias("county.state", "state").add(Restrictions.eq("state.name", stateName))
					.addOrder(Order.asc("name")).list();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	/**
	 * 
	 * @param cityName
	 * @return
	 * @throws AdvException
	 */
	public List<PoliceReportBean> listPoliceReportsByCityName(int cityId, boolean alwaysFirstPage) throws AdvException {
		try {
			Session session = openSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<PoliceReportBean> query = builder.createQuery(PoliceReportBean.class);
			Root<PoliceReportBean> policeReport = query.from(PoliceReportBean.class);

			if (cityId > -1) {
				Join<PoliceReportBean, CityBean> city = policeReport.join("city");

				query.select(policeReport).where(builder.isFalse(policeReport.get("deleted")),
						builder.equal(city.get("id"), cityId));
			} else {
				query.select(policeReport).where(builder.isFalse(policeReport.get("deleted")));
			}

			query.orderBy(builder.desc(policeReport.get("uploadedClaendar")));

			return session.createQuery(query)
					.setMaxResults(PaginationThreadLocal.getPaginationData().getRecordsPerPage())
					.setFirstResult(alwaysFirstPage ? 0
							: (PaginationThreadLocal.getPaginationData().getRecordsPerPage()
									* (PaginationThreadLocal.getPaginationData().getTargetedPage() - 1)))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	/**
	 * 
	 * @param cityName
	 * @return
	 * @throws AdvException
	 */
	public long countUploadedPoliceReportsByCityId(int cityId) throws AdvException {
		try {
			Session session = openSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Long> query = builder.createQuery(Long.class);
			Root<PoliceReportBean> policeReport = query.from(PoliceReportBean.class);

			if (cityId > -1) {
				Join<PoliceReportBean, CityBean> city = policeReport.join("city");

				query.select(builder.count(policeReport)).where(builder.isFalse(policeReport.get("deleted")),
						builder.equal(city.get("id"), cityId));
			} else {
				query.select(builder.count(policeReport)).where(builder.isFalse(policeReport.get("deleted")));
			}

			return session.createQuery(query).uniqueResult();
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
	public List<ParameterBean> listParameters() throws AdvException {
		try {
			Session session = openSession();
			return session.createCriteria(ParameterBean.class).list();
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
	public ParameterBean getParameterByName(String name) throws AdvException {
		try {
			Session session = openSession();
			return (ParameterBean) session.createCriteria(ParameterBean.class)
					.add(Restrictions.eq(ParameterBean.PARM_NAME, name)).uniqueResult();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @throws AdvException
	 */
	public List<PoliceRecordBean> listPoliceRecords(PoliceRecordSearchDTO bean) throws AdvException {
		try {
			Session session = openSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<PoliceRecordBean> query = builder.createQuery(PoliceRecordBean.class);
			Root<PoliceRecordBean> policeRecord = query.from(PoliceRecordBean.class);

			List<Predicate> conditions = new ArrayList<>();

			conditions.add(builder.isFalse(policeRecord.get("deleted")));

			if (bean.getFirstName() != null && bean.getFirstName().trim().length() > 0) {
				conditions.add(builder.like(policeRecord.get("beneficiaryFirstName"),
						builder.literal("%" + bean.getFirstName() + "%")));
			}

			if (bean.getLastName() != null && bean.getLastName().trim().length() > 0) {
				conditions.add(builder.like((policeRecord.get("beneficiaryLastName")),
						builder.literal("%" + bean.getLastName() + "%")));
			}

			if (bean.getDateOA() != null) {
				Calendar beginCal = (Calendar) bean.getDateOA().clone();
				Calendar endCal = (Calendar) bean.getDateOA().clone();

				beginCal.clear(Calendar.HOUR);
				beginCal.clear(Calendar.MINUTE);
				beginCal.clear(Calendar.SECOND);

				endCal.add(Calendar.HOUR, 23);
				endCal.add(Calendar.MINUTE, 59);
				endCal.add(Calendar.SECOND, 59);

				conditions.add(builder.between(policeRecord.get("accidentDate"), beginCal, endCal));
			}

			query.where(conditions.toArray(new Predicate[0])).orderBy(
					builder.desc(policeRecord.get(PoliceRecordBean.ENTRY_DATE)),
					builder.desc(policeRecord.get(PoliceRecordBean.ACCIDENT_DATE)));

			return session.createQuery(query)
					.setMaxResults(PaginationThreadLocal.getPaginationData().getRecordsPerPage())
					.setFirstResult(PaginationThreadLocal.getPaginationData().getRecordsPerPage()
							* (PaginationThreadLocal.getPaginationData().getTargetedPage() - 1))
					.list();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	public long countPoliceRecords(PoliceRecordSearchDTO bean) throws AdvException {
		try {
			Session session = openSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Long> query = builder.createQuery(Long.class);
			Root<PoliceRecordBean> policeRecord = query.from(PoliceRecordBean.class);

			List<Predicate> conditions = new ArrayList<>();

			conditions.add(builder.isFalse(policeRecord.get("deleted")));

			if (bean.getFirstName() != null && bean.getFirstName().trim().length() > 0) {
				conditions.add(builder.like(policeRecord.get("beneficiaryFirstName"),
						builder.literal("%" + bean.getFirstName() + "%")));
			}

			if (bean.getLastName() != null && bean.getLastName().trim().length() > 0) {
				conditions.add(builder.like(policeRecord.get("beneficiaryLastName"),
						builder.literal("%" + bean.getLastName() + "%")));
			}

			if (bean.getDateOA() != null) {
				Calendar beginCal = (Calendar) bean.getDateOA().clone();
				Calendar endCal = (Calendar) bean.getDateOA().clone();

				beginCal.clear(Calendar.HOUR);
				beginCal.clear(Calendar.MINUTE);
				beginCal.clear(Calendar.SECOND);

				endCal.add(Calendar.HOUR, 23);
				endCal.add(Calendar.MINUTE, 59);
				endCal.add(Calendar.SECOND, 59);

				conditions.add(builder.between(policeRecord.get("accidentDate"), beginCal, endCal));
			}

			query.select(builder.count(policeRecord.get("id"))).where(conditions.toArray(new Predicate[0]));

			return session.createQuery(query).uniqueResult();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	/**
	 * 
	 * @param policeReportId
	 * @return
	 * @throws AdvException
	 */
	public List<PoliceRecordBean> listPoliceRecordsByPoliceReportId(long policeReportId) throws AdvException {
		try {
			Session session = openSession();

			return session.createCriteria(PoliceRecordBean.class)
					.add(Restrictions.eq(PoliceRecordBean.POLICE_REPORT_ID, policeReportId))
					.add(Restrictions.eq(PoliceRecordBean.DELETED, false))
					.addOrder(Order.desc(PoliceRecordBean.POLICE_RECORD_ID))
					.setMaxResults(PaginationThreadLocal.getPaginationData().getRecordsPerPage())
					.setFirstResult(PaginationThreadLocal.getPaginationData().getRecordsPerPage()
							* (PaginationThreadLocal.getPaginationData().getTargetedPage() - 1))
					.list();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	public long countPoliceRecordsByPoliceReportId(long policeReportId) throws AdvException {
		try {
			Session session = openSession();

			return (long) session.createCriteria(PoliceRecordBean.class).setProjection(Projections.rowCount())
					.add(Restrictions.eq(PoliceRecordBean.POLICE_REPORT_ID, policeReportId))
					.add(Restrictions.eq(PoliceRecordBean.DELETED, false)).uniqueResult();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	/**
	 * 
	 * @param policeReportIds
	 * @return
	 * @throws AdvException
	 */
	public List<PoliceRecordBean> listPoliceRecordsByIds(List<Long> policeRecordIds) throws AdvException {
		try {
			Session session = openSession();

			Disjunction disjunction = Restrictions.disjunction();

			for (long policeRecordId : policeRecordIds) {
				disjunction.add(Restrictions.eq(PoliceRecordBean.POLICE_RECORD_ID, policeRecordId));
			}

			return session.createCriteria(PoliceRecordBean.class).add(disjunction)
					.addOrder(Order.desc(PoliceRecordBean.ENTRY_DATE).desc(PoliceRecordBean.ACCIDENT_DATE)).list();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	/**
	 * 
	 * @param reportSearchCriteriaDTO
	 * @return
	 * @throws AdvException
	 */
	public Map<Long, Long> getCountPrintedReportsByLawyer(ReportSearchCriteriaDTO searchCriteria) throws AdvException {
		try {
			Session session = openSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Tuple> query = builder.createTupleQuery();
			Root<LawyerBean> lawyer = query.from(LawyerBean.class);
			Join<LawyerBean, PrintedPoliceRecordBean> printedRecord = lawyer.join("printedPoliceRecords");

			List<Predicate> conditions = new ArrayList<>();
			conditions.add(builder.isFalse(lawyer.get("deleted")));

			if (searchCriteria.getLawyerId() != null) {
				conditions.add(builder.equal(lawyer.get("id"), searchCriteria.getLawyerId()));
			}

			if (searchCriteria.getFromDate() != null) {
				Calendar beginCal = (Calendar) searchCriteria.getFromDate().clone();

				beginCal.clear(Calendar.HOUR);
				beginCal.clear(Calendar.MINUTE);
				beginCal.clear(Calendar.SECOND);

				conditions.add(builder.greaterThanOrEqualTo(printedRecord.get("printedDate"), beginCal));
			}

			if (searchCriteria.getToDate() != null) {
				Calendar endCal = (Calendar) searchCriteria.getToDate().clone();

				endCal.clear(Calendar.HOUR);
				endCal.clear(Calendar.MINUTE);
				endCal.clear(Calendar.SECOND);
				endCal.add(Calendar.DAY_OF_MONTH, 1);

				conditions.add(builder.lessThan(printedRecord.get("printedDate"), endCal));
			}

			query.select(
					builder.tuple(lawyer.get("id").alias("id"), builder.count(printedRecord.get("id")).alias("count")))
					.where(conditions.toArray(new Predicate[0]));
			query.groupBy(lawyer.get("id"));

			List<Tuple> results = session.createQuery(query).list();

			return results.stream().collect(
					Collectors.<Tuple, Long, Long>toMap(t -> t.get("id", Long.class), t -> t.get("count", Long.class)));
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	/**
	 * 
	 * @param lawyer
	 * @param policeReportId
	 * @return
	 * @throws AdvException
	 */
	public List<PoliceRecordBean> listUnprintedPoliceRecord(UnPrintedPoliceRecordSearchCriteriaDTO searchCriteriaDTO,
			PrintConfig config) throws AdvException {
		try {
			Session session = openSession();

			Calendar fromDate = (Calendar) searchCriteriaDTO.getFromDate().clone();
			Calendar toDate = (Calendar) searchCriteriaDTO.getToDate().clone();

			fromDate.clear(Calendar.HOUR);
			fromDate.clear(Calendar.MINUTE);
			fromDate.clear(Calendar.SECOND);

			toDate.set(Calendar.HOUR, 23);
			toDate.set(Calendar.MINUTE, 59);
			toDate.set(Calendar.SECOND, 59);

			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(PrintedPoliceRecordBean.class, PrintedPoliceRecordBean.PRINTED_POLICE_RECORD_ALIAS)
					.createAlias(PrintedPoliceRecordBean.PRINTED_LAWYER_BEAN_ALIAS,
							PrintedPoliceRecordBean.LAWYER_BEAN_ALIAS)
					.createAlias(PrintedPoliceRecordBean.PRINTED_POLICE_RECORD_BEAN_ALIAS,
							PrintedPoliceRecordBean.POLICE_RECORD_BEAN_ALIAS)
					.setProjection(Projections.id())
					.add(Restrictions.eq(PrintedPoliceRecordBean.LAWYER_ID, searchCriteriaDTO.getLawyerId()))
					.add(Restrictions.eqProperty(PrintedPoliceRecordBean.POLICE_RECORD_ID, "policeRecord.id"));

			Criteria criteria = session.createCriteria(PoliceRecordBean.class, PoliceRecordBean.POLICE_RECORD_ALIAS)
					.createAlias(PoliceRecordBean.CITY_BEAN_ALIAS, PoliceRecordBean.CITY_BEAN_ALIAS)
					.add(Subqueries.notExists(detachedCriteria))
					.add(Restrictions.between(PoliceRecordBean.ENTRY_DATE, fromDate, toDate))
					.add(Restrictions.eq(PoliceRecordBean.DELETED, false));

			Disjunction disjunction = Restrictions.disjunction();
			Conjunction conjunction = Restrictions.conjunction();

			if (searchCriteriaDTO.getLawyerId() == Constants.UNDELETABLE_LAWYER_ID) {
				conjunction.add(Restrictions.eq(PoliceRecordBean.AT_FAUL, true));
			} else {
				conjunction.add(Restrictions.eq(PoliceRecordBean.AT_FAUL, false));

				if (config.getIncludeCitiesNames() != null && !config.getIncludeCitiesNames().isEmpty()) {
					disjunction.add(Restrictions.in(PoliceRecordBean.CITY_NAME, config.getIncludeCitiesNames()));
				}

				if (config.getCustomersLastNames() != null && !config.getCustomersLastNames().isEmpty()) {
					disjunction.add(
							Restrictions.in(PoliceRecordBean.BENEFICIARY_LAST_NAME, config.getCustomersLastNames()));
				}

				if (config.getZipCodes() != null && !config.getZipCodes().isEmpty()) {
					// multiply by (0.000621371192) to convert the result from meter to mile
					DetachedCriteria detachedCriteria1 = DetachedCriteria.forClass(ZipCodeInfo.class, "zipCodeInfo")
							.setProjection(Projections.sqlProjection(
									"ST_Distance_Sphere (point(this_.longitude, this_.latitude), point(zipCodeInfo_.longitude, zipCodeInfo_.latitude)) * 0.000621371192  as distance",
									new String[] { "distance" },
									new org.hibernate.type.Type[] { StandardBasicTypes.INTEGER }))
							.add(Restrictions.in("zipCodeInfo.id", config.getZipCodes()));

					disjunction.add(Subqueries.geSome(searchCriteriaDTO.getDistance(), detachedCriteria1));
				}

				if (config.getExcludeCitiesNames() != null && !config.getExcludeCitiesNames().isEmpty()) {
					conjunction.add(Restrictions
							.not(Restrictions.in(PoliceRecordBean.CITY_NAME, config.getExcludeCitiesNames())));
				}
			}

			conjunction.add(disjunction);

			return criteria.add(conjunction)
					.addOrder(Order.desc(PoliceRecordBean.ENTRY_DATE).desc(PoliceRecordBean.ACCIDENT_DATE)
							.desc(PoliceRecordBean.POLICE_RECORD_ID))
					.setMaxResults(PaginationThreadLocal.getPaginationData().getRecordsPerPage())
					.setFirstResult(PaginationThreadLocal.getPaginationData().getRecordsPerPage()
							* (PaginationThreadLocal.getPaginationData().getTargetedPage() - 1))
					.list();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	public long countUnprintedPoliceRecord(UnPrintedPoliceRecordSearchCriteriaDTO searchCriteriaDTO, PrintConfig config)
			throws AdvException {
		try {
			Session session = openSession();

			Calendar fromDate = (Calendar) searchCriteriaDTO.getFromDate().clone();
			Calendar toDate = (Calendar) searchCriteriaDTO.getToDate().clone();

			fromDate.clear(Calendar.HOUR);
			fromDate.clear(Calendar.MINUTE);
			fromDate.clear(Calendar.SECOND);

			toDate.set(Calendar.HOUR, 23);
			toDate.set(Calendar.MINUTE, 59);
			toDate.set(Calendar.SECOND, 59);

			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(PrintedPoliceRecordBean.class, PrintedPoliceRecordBean.PRINTED_POLICE_RECORD_ALIAS)
					.createAlias(PrintedPoliceRecordBean.PRINTED_LAWYER_BEAN_ALIAS,
							PrintedPoliceRecordBean.LAWYER_BEAN_ALIAS)
					.createAlias(PrintedPoliceRecordBean.PRINTED_POLICE_RECORD_BEAN_ALIAS,
							PrintedPoliceRecordBean.POLICE_RECORD_BEAN_ALIAS)
					.setProjection(Projections.id())
					.add(Restrictions.eq(PrintedPoliceRecordBean.LAWYER_ID, searchCriteriaDTO.getLawyerId()))
					.add(Restrictions.eqProperty(PrintedPoliceRecordBean.POLICE_RECORD_ID, "policeRecord.id"));

			Criteria criteria = session.createCriteria(PoliceRecordBean.class, PoliceRecordBean.POLICE_RECORD_ALIAS)
					.setProjection(Projections.rowCount())
					.createAlias(PoliceRecordBean.CITY_BEAN_ALIAS, PoliceRecordBean.CITY_BEAN_ALIAS)
					.add(Subqueries.notExists(detachedCriteria))
					.add(Restrictions.between(PoliceRecordBean.ENTRY_DATE, fromDate, toDate))
					.add(Restrictions.eq(PoliceRecordBean.DELETED, false));

			Disjunction disjunction = Restrictions.disjunction();
			Conjunction conjunction = Restrictions.conjunction();

			if (searchCriteriaDTO.getLawyerId() == Constants.UNDELETABLE_LAWYER_ID) {
				conjunction.add(Restrictions.eq(PoliceRecordBean.AT_FAUL, true));
			} else {
				conjunction.add(Restrictions.eq(PoliceRecordBean.AT_FAUL, false));

				if (config.getIncludeCitiesNames() != null && !config.getIncludeCitiesNames().isEmpty()) {
					disjunction.add(Restrictions.in(PoliceRecordBean.CITY_NAME, config.getIncludeCitiesNames()));
				}

				if (config.getCustomersLastNames() != null && !config.getCustomersLastNames().isEmpty()) {
					disjunction.add(
							Restrictions.in(PoliceRecordBean.BENEFICIARY_LAST_NAME, config.getCustomersLastNames()));
				}

				if (config.getZipCodes() != null && !config.getZipCodes().isEmpty()) {
					// multiply by (0.000621371192) to convert the result from meter to mile
					DetachedCriteria detachedCriteria1 = DetachedCriteria.forClass(ZipCodeInfo.class, "zipCodeInfo")
							.setProjection(Projections.sqlProjection(
									"ST_Distance_Sphere (point(this_.longitude, this_.latitude), point(zipCodeInfo_.longitude, zipCodeInfo_.latitude)) * 0.000621371192  as distance",
									new String[] { "distance" },
									new org.hibernate.type.Type[] { StandardBasicTypes.INTEGER }))
							.add(Restrictions.in("zipCodeInfo.id", config.getZipCodes()));

					disjunction.add(Subqueries.geSome(searchCriteriaDTO.getDistance(), detachedCriteria1));
				}

				if (config.getExcludeCitiesNames() != null && !config.getExcludeCitiesNames().isEmpty()) {
					conjunction.add(Restrictions
							.not(Restrictions.in(PoliceRecordBean.CITY_NAME, config.getExcludeCitiesNames())));
				}
			}

			conjunction.add(disjunction);

			return (long) criteria.add(conjunction).uniqueResult();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	public List<State> listStates() throws AdvException {
		try {
			Session session = openSession();

			return session.createCriteria(State.class).list();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	public List<ZipCodeInfo> listZipCodeInfo() throws AdvException {
		try {
			Session session = openSession();
			return session.createCriteria(ZipCodeInfo.class).addOrder(Order.asc("zipCode")).list();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	public void deletePoliceReportById(long id) throws AdvException {
		try {
			Session session = getCurrentSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaUpdate<PoliceReportBean> update = builder.createCriteriaUpdate(PoliceReportBean.class);
			Root<PoliceReportBean> policeReport = update.from(PoliceReportBean.class);
			update.set("deleted", true);
			update.where(builder.equal(policeReport.get("id"), id));

			session.createQuery(update).executeUpdate();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	public void deletePoliceRecordById(long id) throws AdvException {
		try {
			Session session = getCurrentSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaUpdate<PoliceRecordBean> update = builder.createCriteriaUpdate(PoliceRecordBean.class);
			Root<PoliceRecordBean> policeRecord = update.from(PoliceRecordBean.class);
			update.set("deleted", true);
			update.where(builder.equal(policeRecord.get("id"), id));

			session.createQuery(update).executeUpdate();
		} catch (Exception e) {
			logger.error("An error occured", e);
			throw new AdvException(e);
		}
	}

	public List<String> getCitiesNames(List<Long> citiesIds) throws AdvException {
		try {
			Session session = openSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
			Root<CityBean> cityRoot = criteriaQuery.from(CityBean.class);

			criteriaQuery.select(cityRoot.get("name")).orderBy(builder.asc(cityRoot.get("name")))
					.where(cityRoot.get("id").in(citiesIds));

			return session.createQuery(criteriaQuery).list();
		} catch (Exception e) {
			throw new AdvException(e);
		}
	}

	public List<String> getZipCodesByIds(List<Long> zipCodesIds) throws AdvException {
		try {
			Session session = openSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
			Root<ZipCodeInfo> zipRoot = criteriaQuery.from(ZipCodeInfo.class);

			criteriaQuery.select(zipRoot.get("zipCode")).orderBy(builder.asc(zipRoot.get("zipCode")))
					.where(zipRoot.get("id").in(zipCodesIds));

			return session.createQuery(criteriaQuery).list();
		} catch (Exception e) {
			throw new AdvException(e);
		}
	}

	public ZipCodeInfo getZipInfoByCode(String zipCode) throws AdvException {
		try {
			Session session = openSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ZipCodeInfo> criteriaQuery = builder.createQuery(ZipCodeInfo.class);
			Root<ZipCodeInfo> zipRoot = criteriaQuery.from(ZipCodeInfo.class);

			criteriaQuery.select(zipRoot).where(builder.equal(zipRoot.get("zipCode"), zipCode));

			ZipCodeInfo zipCodeInfo = session.createQuery(criteriaQuery).uniqueResult();

			if (zipCodeInfo == null) {
				logger.warn("The zip code \"%s\" doesn't have a record in zip code info table.", zipCode);
			}

			return zipCodeInfo;
		} catch (Exception e) {
			throw new AdvException(e);
		}
	}

	public Map<Long, String> getAndAssignCodes(List<PoliceRecordBean> policeRecords) throws AdvException {
		Session session = openSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<FaultCode> criteriaQuery = builder.createQuery(FaultCode.class);
		Root<FaultCode> faultCodeRoot = criteriaQuery.from(FaultCode.class);

		Subquery<PoliceRecordWithCode> policeRecordWithCodeSubquery = criteriaQuery
				.subquery(PoliceRecordWithCode.class);
		Root<PoliceRecordWithCode> policeRecordWithCodeRoot = policeRecordWithCodeSubquery
				.from(PoliceRecordWithCode.class);

		policeRecordWithCodeSubquery.select(policeRecordWithCodeRoot)
				.where(builder.equal(faultCodeRoot.get("code"), policeRecordWithCodeRoot.get("code")));

		criteriaQuery.select(faultCodeRoot).where(builder.not(builder.exists(policeRecordWithCodeSubquery)));

		List<FaultCode> faultCodes = session.createQuery(criteriaQuery).setLockMode(LockModeType.PESSIMISTIC_WRITE)
				.setMaxResults(policeRecords.size()).list();

		if (faultCodes.size() < policeRecords.size()) {
			throw new AdvException("No enough fault codes!");
		}

		Iterator<FaultCode> faultCodeIterator = faultCodes.iterator();
		Iterator<PoliceRecordBean> policeRecordIterator = policeRecords.iterator();

		Map<Long, String> faultCodesByRecordId = new HashMap<>();

		while (faultCodeIterator.hasNext() && policeRecordIterator.hasNext()) {
			PoliceRecordWithCode policeRecordWithCode = new PoliceRecordWithCode();
			policeRecordWithCode.setCode(faultCodeIterator.next().getCode());
			policeRecordWithCode.setPoliceRecordBean(policeRecordIterator.next());

			faultCodesByRecordId.put(policeRecordWithCode.getPoliceRecordBean().getId(),
					policeRecordWithCode.getCode());

			session.save(policeRecordWithCode);
		}

		return faultCodesByRecordId;
	}
}
