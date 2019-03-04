package com.gsoft.framework.jpa.conf;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.gsoft.framework.core.log.ConfigRegisterLog;

/**
 * DruidDBConfig类被@Configuration标注，用作配置信息； DataSource对象被@Bean声明，为Spring容器所管理，
 * @Primary表示这里定义的DataSource将覆盖其他来源的DataSource。
 * @author liupantao
 *
 */
@Configuration
@EnableTransactionManagement
public class DruidDBConfig {

	@Bean
	@Primary
	// 在同样的DataSource中，首先使用被标注的DataSource
	public DataSource dataSource() {
		DruidDataSource dataSource = DruidDataSourceBuilder.create().build(DruidProperties.DEFAULT_PREFIX);
		ConfigRegisterLog.registeBean(dataSource, "默认数据源",this);
		return dataSource;
	}
}