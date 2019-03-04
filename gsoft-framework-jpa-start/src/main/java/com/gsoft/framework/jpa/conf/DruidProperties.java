package com.gsoft.framework.jpa.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.gsoft.framework.core.exception.BusException;

/**
 * druid配置
 * @author liupantao
 *
 */
@Component
public class DruidProperties {

	public static final String DEFAULT_PREFIX = "spring.datasource.";

	@Autowired
	private Environment env;

	public String getUrl(String prefix) {
		return getPropertyValue(prefix, "url", false);
	}

	public String getUsername(String prefix) {
		return getPropertyValue(prefix, "username", false);
	}

	public String getPassword(String prefix) {
		return getPropertyValue(prefix, "password", false);
	}

	public String getDriverClassName(String prefix) {
		return getPropertyValue(prefix, "driverClassName", false);
	}

	public int getInitialSize(String prefix) {
		return Integer.parseInt(getPropertyValue(prefix, "initialSize"));
	}

	public int getMinIdle(String prefix) {
		return Integer.parseInt(getPropertyValue(prefix, "minIdle"));
	}

	public int getMaxActive(String prefix) {
		return Integer.parseInt(getPropertyValue(prefix, "maxActive"));
	}

	public int getMaxWait(String prefix) {
		return Integer.parseInt(getPropertyValue(prefix, "maxWait"));
	}

	public int getTimeBetweenEvictionRunsMillis(String prefix) {
		return Integer.parseInt(getPropertyValue(prefix, "timeBetweenEvictionRunsMillis"));
	}

	public int getMinEvictableIdleTimeMillis(String prefix) {
		return Integer.parseInt(getPropertyValue(prefix, "minEvictableIdleTimeMillis"));
	}

	public String getValidationQuery(String prefix) {
		return getPropertyValue(prefix, "validationQuery");
	}

	public boolean isTestWhileIdle(String prefix) {
		return Boolean.valueOf(getPropertyValue(prefix, "testWhileIdle"));
	}

	public boolean isTestOnBorrow(String prefix) {
		return Boolean.valueOf(getPropertyValue(prefix, "testOnBorrow"));
	}

	public boolean isTestOnReturn(String prefix) {
		return Boolean.valueOf(getPropertyValue(prefix, "testOnReturn"));
	}

	public boolean isPoolPreparedStatements(String prefix) {
		return Boolean.valueOf(getPropertyValue(prefix, "poolPreparedStatements"));
	}

	public int getMaxPoolPreparedStatementPerConnectionSize(String prefix) {
		return Integer.parseInt(getPropertyValue(prefix, "maxPoolPreparedStatementPerConnectionSize"));
	}

	public String getFilters(String prefix) {
		return getPropertyValue(prefix, "filters");
	}

	public String getConnectionProperties(String prefix) {
		return getPropertyValue(prefix, "connectionProperties");
	}

	private String getPropertyValue(String prefix, String key) {
		return getPropertyValue(prefix, key, true);
	}

	private String getPropertyValue(String prefix, String key, boolean canOverride) {
		String value = env.getProperty(prefix + key);
		if (value == null && canOverride && !DEFAULT_PREFIX.equals(prefix)) {
			return getPropertyValue(DEFAULT_PREFIX, key);
		}
		if (value == null) {
			throw new BusException("DruidDataSource build Error :" + prefix + key + "can not be null ...");
		}
		return value;
	}

}
