package com.gsoft.framework.jpa.conf;

import java.sql.SQLException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 
 * @author liupantao
 * 
 */
class DruidDataSourceWrapper extends DruidDataSource implements InitializingBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3809924576384445584L;
	@Autowired
	private DruidProperties druidProperties;

	private String prefix;

	public DruidDataSourceWrapper(String prefix) {
		super();
		this.prefix = prefix;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.setUrl(druidProperties.getUrl(prefix));
		this.setUsername(druidProperties.getUsername(prefix));
		this.setPassword(druidProperties.getPassword(prefix));
		this.setDriverClassName(druidProperties.getDriverClassName(prefix));
		// configuration
		this.setInitialSize(druidProperties.getInitialSize(prefix));
		this.setMinIdle(druidProperties.getMinIdle(prefix));
		this.setMaxActive(druidProperties.getMaxActive(prefix));
		this.setMaxWait(druidProperties.getMaxWait(prefix));
		this.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis(prefix));
		this.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis(prefix));
		this.setValidationQuery(druidProperties.getValidationQuery(prefix));
		this.setTestWhileIdle(druidProperties.isTestWhileIdle(prefix));
		this.setTestOnBorrow(druidProperties.isTestOnBorrow(prefix));
		this.setTestOnReturn(druidProperties.isTestOnReturn(prefix));
		this.setPoolPreparedStatements(druidProperties.isPoolPreparedStatements(prefix));
		this.setMaxPoolPreparedStatementPerConnectionSize(druidProperties
				.getMaxPoolPreparedStatementPerConnectionSize(prefix));
		try {
			this.setFilters(druidProperties.getFilters(prefix));
		} catch (SQLException e) {
		}
		this.setConnectionProperties(druidProperties.getConnectionProperties(prefix));
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
