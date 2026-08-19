package com.p6spy.spring.boot;

import javax.sql.DataSource;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.p6spy.engine.common.P6LogQuery;
import com.p6spy.engine.spy.DefaultJdbcEventListenerFactory;
import com.p6spy.engine.spy.JdbcEventListenerFactory;
import com.p6spy.engine.spy.P6DataSource;
import com.p6spy.engine.spy.P6ModuleManager;
import com.p6spy.engine.spy.option.P6OptionChangedListener;
import com.p6spy.spring.boot.ext.P6spyDataSource;

/**
 * <p>Auto-configuration for P6Spy.</p>
 * <p>Configures P6Spy datasource proxy, JDBC event listener factory, and option change listeners
 * when the P6Spy library is present on the classpath.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(com.p6spy.engine.spy.P6DataSource.class)
@EnableConfigurationProperties({ P6SpyProperties.class })
public class P6SpyAutoConfiguration {

	/**
	 * <p>Creates a default P6LogQuery option change listener.</p>
	 *
	 * @return a new {@link P6LogQuery} instance
	 */
	@Bean
	@ConditionalOnMissingBean
    /**
     * <p>P6 option changed listener.</p>
     * @return the p6 option changed listener
     */
	protected P6OptionChangedListener p6OptionChangedListener() {
		return new P6LogQuery();
	}

	/**
	 * <p>Creates a default JDBC event listener factory.</p>
	 *
	 * @return a new {@link DefaultJdbcEventListenerFactory} instance
	 */
	@Bean
	@ConditionalOnMissingBean
    /**
     * <p>Jdbc event listener factory.</p>
     * @return the jdbc event listener factory
     */
	protected JdbcEventListenerFactory jdbcEventListenerFactory() {
		return new DefaultJdbcEventListenerFactory();
	}

	/**
	 * <p>Creates a P6Spy-wrapped DataSource that intercepts JDBC calls for logging and monitoring.</p>
	 *
	 * @param p6spyDataSource   the datasource qualified with {@link P6spyDataSource}
	 * @param p6OptionChangedListener provider for option change listeners
	 * @param jdbcEventListenerFactory the JDBC event listener factory
	 * @return the P6Spy-wrapped {@link P6DataSource}
	 */
	@Bean
	@Primary
	public P6DataSource p6DataSource(@P6spyDataSource ObjectProvider<DataSource> p6spyDataSource,
			ObjectProvider<P6OptionChangedListener> p6OptionChangedListener,
			JdbcEventListenerFactory jdbcEventListenerFactory) {

		p6OptionChangedListener.stream().forEach(listener -> {
			P6ModuleManager.getInstance().registerOptionChangedListener(listener);
		});

		P6DataSource p6DataSource = new P6DataSource(p6spyDataSource.getObject());
		p6DataSource.setJdbcEventListenerFactory(jdbcEventListenerFactory);

		return p6DataSource;
	}

}
