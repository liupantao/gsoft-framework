/**
 * 
 */
package com.gsoft.framework.core.dataobj.tree;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于描述Domain实体映射树属性
 * 
 * @author Administrator
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TreeAttribute {
	/**
	 * @Fields TREE_ATTR_ID : ID
	 */
	public static final String TREE_ATTR_ID="id";
	
	/**
	 * @Fields TREE_ATTR_CODE : 编码
	 */
	public static final String TREE_ATTR_CODE="code";
	
	/**
	 * @Fields TREE_ATTR_PARENT : 父对象 
	 */
	public static final String TREE_ATTR_PARENT="parent";
	
	/**
	 * @Fields TREE_ATTR_PID : 父ID
	 */
	public static final String TREE_ATTR_PID="parentId";
	
	/**
	 * @Fields TREE_ATTR_TEXT : 显示文本
	 */
	public static final String TREE_ATTR_TEXT="text";
	
	/**
	 * @Fields TREE_ATTR_GOURP : 分组
	 */
	public static final String TREE_ATTR_GOURP="group";

	/**
	 * @Fields TREE_ATTR_SRC : 树加载路径
	 */
	public static final String TREE_ATTR_SRC="src";
	
	/**
	 * @Fields TREE_ATTR_HREF : 链接 
	 */
	public static final String TREE_ATTR_HREF="href";
	
	/**
	 * @Fields TREE_ATTR_NUM : 序号
	 */
	public static final String TREE_ATTR_NUM="num";
	
	public static final String TREE_ATTR_TARGET = "target";
	
	
	String value();
}
