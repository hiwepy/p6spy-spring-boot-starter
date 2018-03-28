package com.p6spy.spring.boot;

import javax.sql.DataSource;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.p6spy.engine.spy.P6DataSource;
import com.p6spy.engine.spy.option.P6OptionChangedListener;
import com.p6spy.spring.boot.ext.P6spyDataSource;

/**
 */
@Configuration
@ConditionalOnClass(com.p6spy.engine.spy.P6DataSource.class)
@ConditionalOnProperty(name = "spring.datasource.p6spy.enabled" , havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({ P6SpyProperties.class, DataSourceProperties.class})
public class P6SpyAutoConfiguration {
	
	
	@Bean
	@ConditionalOnMissingBean
	protected P6OptionChangedListener listener() {
		return null;
	}
	
	@Primary
	public P6DataSource druidDataSource(@P6spyDataSource ObjectProvider<DataSource> p6spyDataSource) {
		
		//P6ModuleManager.getInstance().registerOptionChangedListener();
				
		return new P6DataSource(p6spyDataSource.getObject());
	}

}
