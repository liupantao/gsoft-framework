package com.gsoft.framework.jpa.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.gsoft.framework.core.exception.YouiException;
import com.gsoft.framework.core.orm.Condition;
import com.gsoft.framework.core.orm.Order;
import com.gsoft.framework.core.orm.Pager;
import com.gsoft.framework.core.orm.PagerRecords;
import com.gsoft.framework.core.orm.Sort;
import com.gsoft.framework.jpa.dao.JpaDao;
import com.gsoft.framework.util.ConditionUtils;

/**
 * jpa dao 实现
 * 
 * @author liupantao
 * 
 * @param <T>
 * @param <ID>
 */
public class BaseJpaDao<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements JpaDao<T, ID> {

	private Log logger = LogFactory.getLog(BaseJpaDao.class);
	private EntityManager entityManager;

	public BaseJpaDao(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.entityManager = em;
	}
	
	@Override
	@Transactional
	public void delete(List<ID> ids) {
		deleteInBatch(findAll(ids));
	}
	
	@Override
	@Transactional
	public void delete(String property, Object value) {
		deleteInBatch(findList(property, value));
	}

	@Override
	public T findOneInitialize(ID id, String[] cAttributes) {
		T object = findOne(id);
		initializeObjectCollections(object, cAttributes);
		return object;
	}

	@Override
	public T findOneByProperty(final String propertyName, final Object value) {
		return super.findOne(new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Condition condtion = ConditionUtils.getCondition(propertyName, Condition.EQUALS, value);
				return condtion.generateExpression(root, cb);
			}
		});
	}
	
	@Override
	public T findOneByProperties(final String[] properties,final Object[] values) {
		if (properties == null || values == null || properties.length != values.length) {
			return null;
		}
		 
		return super.findOne(new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				for (int i = 0; i < properties.length; i++) {
					Condition condtion = ConditionUtils.getCondition(properties[i], Condition.EQUALS, values[i]);
					predicates.add(condtion.generateExpression(root, cb));
				}
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
	}

	@Override
	public T findOneByProperty(String propertyName, Object value, String[] cAttributes) {
		T entity = this.findOneByProperty(propertyName, value);
		initializeObjectCollections(entity, cAttributes);
		return entity;
	}

	@Override
	public boolean exists(String propertyName, Object value) {
		List<T> entitys = this.findList(propertyName, value);
		return entitys.size() > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findStartByTermOnAOrB(String propertyA, String propertyB, String term) {
		String queryString = "from " + getEntityName() + " as model where x." + propertyA + " like :term1 or x." + propertyB
				+ " like :term2 order by x." + propertyA;
		Query queryObject = entityManager.createQuery(queryString);
		queryObject.setParameter("term1", term + "%");
		queryObject.setParameter("term2", "%" + term + "%");
		return queryObject.getResultList();
	}

	@Override
	public List<T> commonQuery(final Collection<Condition> conditions, Collection<Order> orders) {
		return super.findAll(new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if(conditions!=null){
					for (Condition condition : conditions) {
						predicates.add(condition.generateExpression(root, cb));
					}
				}
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, buildSort(orders));
	}

	@Override
	public PagerRecords findByPager(Pager pager, final Collection<Condition> conditions, Collection<Order> orders) {
		Page<T> page = super.findAll(new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				for (Condition condition : conditions) {
					predicates.add(condition.generateExpression(root, cb));
				}
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, buildPageRequest(pager, orders));
		PagerRecords PagerRecords = new PagerRecords(page.getContent(), page.getTotalElements());
		PagerRecords.setPager(pager);
		return PagerRecords;
	}

	@Override
	public List<T> findList(final String propertyName, final Object value) {
		return super.findAll(new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Condition condtion = ConditionUtils.getCondition(propertyName, Condition.EQUALS, value);
				return condtion.generateExpression(root, cb);
			}
		});
	}

	@Override
	public List<T> findList(final String[] properties, final Object[] values) {
		if (properties == null || values == null || properties.length != values.length) {
			return null;
		}
		return super.findAll(new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				for (int i = 0; i < properties.length; i++) {
					Condition condtion = ConditionUtils.getCondition(properties[i], Condition.EQUALS, values[i]);
					predicates.add(condtion.generateExpression(root, cb));
				}
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
	}
	
	@Override
	public T findFirstByProperty(String property, Object value) {
		List<T> result = findList(property, value);
		if(result.size()>0) {
			return result.get(0);
		}
		return null;
	}
	
	@Override
	public T findFirstByProperties(String[] properties, Object[] values) {
		List<T> result = findList(properties, values);
		if(result.size()>0) {
			return result.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public void update(String propertyName, Object oldValue, Object newValue) {
		StringBuffer queryBuf = new StringBuffer();
		queryBuf.append("update ").append(getEntityName()).append(" as x ").append(" set x.").append(propertyName).append("=:newValue");
		if (oldValue != null) {
			queryBuf.append(" where x.").append(propertyName).append(" = ").append(" :oldValue");
		}

		Query query = entityManager.createQuery(queryBuf.toString()).setParameter("newValue", newValue);
		if (oldValue != null) {
			query.setParameter("oldValue", oldValue);
		}
		query.executeUpdate();
	}

	/**
	 * 
	 * @param initializeObject
	 *            需要延迟加载的对象
	 * @param initializeCollections
	 *            延迟加载对象的集合属性名称数组
	 */
	@Override
	public final void initializeObjectCollections(Object initializeObject, String[] cAttributes) {
		if (initializeObject != null && cAttributes != null) {
			for (String initializeCollection : cAttributes) {
				try {
					initializeObjectCollection(initializeObject, initializeCollection);
				} catch (Exception e) {
					e.printStackTrace();
					logger.warn("属性【" + initializeCollection + "】延迟加载失败：" + e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 强制加载对象的延迟加载集合
	 * 
	 * @param initializeObject
	 * @param initializeCollection
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws YouiException
	 */
	private void initializeObjectCollection(Object initializeObject, String initializeCollection) throws Exception {
		Object obj = null;
		if (initializeObject == null || initializeCollection == null || initializeCollection.equals("")) {
			return;
		}
		obj = PropertyUtils.getProperty(initializeObject, initializeCollection);
		Hibernate.initialize(obj);
	}

	private Sort buildSort(Collection<Order> orders) {
		Sort sort = null;
		if (orders != null && orders.size() > 0) {
			sort = new Sort(orders.toArray(new Order[orders.size()]));
		}
		return sort;
	}

	/**
	 * 建立分页排序请求
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	private Pager buildPageRequest(Pager pager, Collection<Order> orders) {
		Sort sort = buildSort(orders);
		return new Pager(pager.getPageNumber(), pager.getPageSize(), sort);
	}

	@Override
	public String getEntityName() {
		return getDomainClass().getSimpleName();
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
