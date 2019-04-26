/**
 * 代码声明
 */
package com.gsoft.framework.codemap.service;

import java.util.Collection;
import java.util.List;

import com.gsoft.framework.codemap.entity.Codeitem;
import com.gsoft.framework.codemap.entity.Codemap;
import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.core.orm.Condition;
import com.gsoft.framework.core.orm.Order;
import com.gsoft.framework.core.orm.Pager;
import com.gsoft.framework.core.orm.PagerRecords;
import com.gsoft.framework.core.service.BaseManager;

/**
 * 
 * @author liupantao
 * 
 */
public interface CodemapManager extends BaseManager {

	/** 
	 * 查询列表
	 * @return
	 * @throws BusException 
	 */
	public List<Codemap> getCodemaps() throws BusException;

	/** 
	 * 条件查询列表
	 * @param conditions
	 * @param orders
	 * @return
	 * @throws BusException 
	 */
	public List<Codemap> getCodemaps(Collection<Condition> conditions, Collection<Order> orders) throws BusException;

	/** 
	 * 根据主键查询
	 * @param id
	 * @return
	 * @throws BusException 
	 */
	public Codemap getCodemap(String id) throws BusException;

	/** 
	 * 分页查询用户
	 * @param pager 分页条件
	 * @param conditions 查询条件
	 * @param orders 排序条件
	 * @return 分页对象
	 * @throws BusException 
	 */
	public PagerRecords getPagerCodemaps(Pager pager,
			Collection<Condition> conditions,
			Collection<Order> orders) throws BusException;

	/** 
	 * 保存并返回对象
	 * @param o
	 * @return
	 * @throws BusException 
	 */
	public Codemap saveCodemap(Codemap o) throws BusException;

	/** 
	 * 删除对象
	 * @param id
	 * @throws BusException 
	 */
	public void removeCodemap(String id) throws BusException;

	/** 
	 * 根据主键集合删除对象
	 * @param ids
	 * @throws BusException 
	 */
	public void removeCodemaps(String[] ids) throws BusException;

	/** 
	 * 主键是否已经使用
	 * @param id
	 * @return
	 * @throws BusException 
	 */
	public boolean exsitCodemap(String id) throws BusException;

	/** 
	 * 属性值是否已经使用
	 * @param propertyName
	 * @param value
	 * @return
	 * @throws BusException 
	 */
	public boolean exsitCodemap(String propertyName, Object value) throws BusException;

	/** 
	 * 根据codemap获取item分页对象
	 * @param pager
	 * @param codemap
	 * @param orders
	 * @return 
	 */
	public PagerRecords getPagerCodeitemsByCodeMap(Pager pager, Codemap codemap, Collection<Order> orders);

	/**
	 * 根据代码集主键集合导出代码集
	 * 
	 * @param ids
	 * @return
	 */
	public String exportCodemapToSql(String[] ids);

	/** 
	 * 根据名称加载codeitem集合
	 * @param name
	 * @return 
	 */
	public List<Codeitem> loadCodeitems(String name);

}
