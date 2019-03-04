package com.gsoft.framework.jpa.conf;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 
 * @author liupantao
 * 
 */
public class DruidDataSourceBuilder {

	/**
	 * For build multiple DruidDataSource, detail see document.
	 */

	public static DruidDataSourceBuilder create() {
		return new DruidDataSourceBuilder();
	}

	public DruidDataSource build(String prefix) {
		return new DruidDataSourceWrapper(prefix);
	}

}
