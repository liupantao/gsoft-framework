/**
 * 代码声明
 */
package com.gsoft.framework.codemap.service;

import java.util.Collection;
import java.util.List;

import com.gsoft.framework.codemap.entity.Codeitem;
import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.core.orm.Condition;
import com.gsoft.framework.core.orm.Order;
import com.gsoft.framework.core.orm.Pager;
import com.gsoft.framework.core.orm.PagerRecords;
import com.gsoft.framework.core.service.BaseManager;

/**
 * CodeitemManager
 * 
 * @author liupantao
 * @date 2017年10月17日
 * 
 */
public interface CodeitemManager extends BaseManager {

	/**
	 * 查询列表
	 * 
	 * @return
	 * @throws BusException
	 */
	public List<Codeitem> getCodeitems() throws BusException;

	/**
	 * 条件查询列表
	 * 
	 * @param conditions
	 * @param orders
	 * @return
	 * @throws BusException
	 */
	public List<Codeitem> getCodeitems(Collection<Condition> conditions, Collection<Order> orders) throws BusException;

	/** 
	 * 根据主键查询
	 * @param id
	 * @return
	 * @throws BusException 
	 */
	public Codeitem getCodeitem(String id) throws BusException;

	/** 
	 * 分页查询用户
	 * @param pager
	 * @param conditions
	 * @param orders
	 * @return 分页对象
	 * @throws BusException 
	 */
	public PagerRecords getPagerCodeitems(Pager pager, // 分页条件
			Collection<Condition> conditions, // 查询条件
			Collection<Order> orders) throws BusException;

	/** 
	 * 保存并返回对象
	 * @param o
	 * @return
	 * @throws BusException 
	 */
	public Codeitem saveCodeitem(Codeitem o) throws BusException;

	/** 
	 * 删除对象
	 * @param id
	 * @throws BusException 
	 */
	public void removeCodeitem(String id) throws BusException;

	/** 
	 * 根据主键集合删除对象
	 * @param ids
	 * @throws BusException 
	 */
	public void removeCodeitems(String[] ids) throws BusException;

	/** 
	 * 主键是否已经使用
	 * @param id
	 * @return
	 * @throws BusException 
	 */
	public boolean exsitCodeitem(String id) throws BusException;
	
	/**
	 * 属性值是否已经使用 
	 * @param propertyName
	 * @param value
	 * @return
	 * @throws BusException
	 */
	public boolean exsitCodeitem(String propertyName, Object value) throws BusException;

}
