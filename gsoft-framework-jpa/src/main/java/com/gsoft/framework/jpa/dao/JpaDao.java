package com.gsoft.framework.jpa.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.gsoft.framework.core.orm.Condition;
import com.gsoft.framework.core.orm.Order;
import com.gsoft.framework.core.orm.Pager;
import com.gsoft.framework.core.orm.PagerRecords;

/**
 * JpaDao 接口
 * @author liupantao
 * @date 2017年10月17日
 *
 * @param <T>
 * @param <ID>  
 */ 
@NoRepositoryBean
public interface JpaDao<T, ID extends Serializable> extends JpaRepository<T, ID> {

	List<T> findAll();

	/**
	 * 获取全部并排序
	 */
	List<T> findAll(Sort sort);

	/**
	 * 保存
	 */
	<S extends T> S saveAndFlush(S entity);

	/**
	 * 根据主键集合获取全部
	 */
	List<T> findAll(Iterable<ID> ids);

	/**
	 * 保存集合
	 */
	<S extends T> List<S> save(Iterable<S> entities);

	/**
	 * Flushes all pending changes to the database.
	 */
	void flush();

	/**
	 * 根据主键删除
	 */
	void delete(ID id);
	
	/**
	 * 根据单一属性删除
	 * 
	 * @param property
	 *            属性名称
	 * @param value
	 *            属性值
	 */
	void delete(String property, Object value);

	/** 
	 * 根据ID集合删除
	 * @param ids 
	 */
	void delete(List<ID> ids);
	
	/**
	 * 根据entity集合删除
	 */
	void deleteInBatch(Iterable<T> entities);

	/**
	 * 删除全部
	 */
	void deleteAllInBatch();

	@Deprecated
	T getOne(ID id);
	
	/**
	 * 根据ID获取对象
	 */
	T findOne(ID id);

	/**
	 * 根据ID获取对象并加载延迟属性
	 * 
	 * @param id
	 * @param cAttributes
	 * @return
	 */
	T findOneInitialize(final ID id, final String[] cAttributes);

	/**
	 * 根据属性值获取唯一对象
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	T findOneByProperty(String propertyName, Object value);
	
	/** 
	 * 根据属性值集合获取唯一对象
	 * @param properties
	 * @param values
	 * @return 
	 */
	T findOneByProperties(String[] properties,Object[] values);

	/**
	 * 根据属性值获取唯一对象
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	T findOneByProperty(String propertyName, Object value, final String[] cAttributes);

	/**
	 * 是否存在ID
	 */
	boolean exists(ID id);

	/**
	 * 检查属性值是否已经存在
	 * 
	 * @param property
	 *            实体属性
	 * @param value
	 *            待检查的属性值
	 * @return - true if it exists, false if it doesn't
	 */
	boolean exists(String property, Object value);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.repository.query.QueryByExampleExecutor#findAll
	 * (org.springframework.data.domain.Example)
	 */
	@Override
	<S extends T> List<S> findAll(Example<S> example);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.repository.query.QueryByExampleExecutor#findAll
	 * (org.springframework.data.domain.Example,
	 * org.springframework.data.domain.Sort)
	 */
	@Override
	<S extends T> List<S> findAll(Example<S> example, Sort sort);

	/**
	 * 
	 * @param propertyA
	 * @param propertyB
	 * @param term
	 * @return
	 */
	List<T> findStartByTermOnAOrB(final String propertyA, final String propertyB, final String term);

	/**
	 * 通用查询
	 * 
	 * @param conditions
	 *            查询过滤条件
	 * @param orders
	 *            结果排序条件
	 * @return 根据查询条件返回的按排序条件排序的结果集
	 */
	List<T> commonQuery(final Collection<Condition> conditions, final Collection<Order> orders);

	/**
	 * 通用分页查询分页 用于单表条件或者数据量较小的多表关联查询
	 * 
	 * @param pager
	 * @param conditions
	 * @param orders
	 * @return
	 */
	PagerRecords findByPager(final Pager pager,// 分页条件
			final Collection<Condition> conditions,// 查询条件
			final Collection<Order> orders);

	/**
	 * 更加属性查询列表
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	List<T> findList(String property, Object value);

	/**
	 * @param properties
	 * @param values
	 * @return
	 */
	List<T> findList(String[] properties, Object[] values);
	
	/** 
	 * 根据属性查找第一个对象
	 * @param property
	 * @param value
	 * @return 
	 */
	T findFirstByProperty(String property, Object value);
	
	/** 
	 * 根据属性集合查找第一个对象
	 * @param properties
	 * @param values
	 * @return 
	 */
	T findFirstByProperties(String[] properties, Object[] values);

	/**
	 * @param propertyName
	 *            属性
	 * @param oldValue
	 *            更新依据值
	 * @param newValue
	 *            更新值
	 */
	void update(String propertyName, Object oldValue, Object newValue);

	/**
	 * 获取实体名称
	 * @return
	 */
	String getEntityName();

	/**
	 * 获取EntityManager
	 * @return
	 */
	EntityManager getEntityManager();

	/**
	 * 
	 * @param initializeObject
	 * @param cAttributes
	 */
	void initializeObjectCollections(Object initializeObject, String[] cAttributes);

	
}
