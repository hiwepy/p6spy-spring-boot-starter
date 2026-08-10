package com.p6spy.spring.boot;

import java.util.stream.Stream;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;

import com.p6spy.engine.common.P6LogQuery;
import com.p6spy.engine.spy.DefaultJdbcEventListenerFactory;
import com.p6spy.engine.spy.JdbcEventListenerFactory;
import com.p6spy.engine.spy.P6DataSource;
import com.p6spy.engine.spy.P6DriverManagerDataSource;
import com.p6spy.engine.spy.option.P6OptionChangedListener;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link P6SpyAutoConfiguration}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@DisplayName("P6SpyAutoConfiguration Tests")
class P6SpyAutoConfigurationTest {

    @Test
    @DisplayName("Auto-configuration class can be instantiated")
    void testInstantiation() {
        P6SpyAutoConfiguration configuration = new P6SpyAutoConfiguration();
        assertThat(configuration).isNotNull();
    }

    @Test
    @DisplayName("jdbcEventListenerFactory bean returns DefaultJdbcEventListenerFactory instance")
    void testJdbcEventListenerFactoryBean() {
        P6SpyAutoConfiguration configuration = new P6SpyAutoConfiguration();
        JdbcEventListenerFactory factory = configuration.jdbcEventListenerFactory();
        assertThat(factory).isNotNull();
        assertThat(factory).isInstanceOf(DefaultJdbcEventListenerFactory.class);
    }

    @Test
    @DisplayName("p6DataSource bean wraps provided DataSource")
    void testP6DataSourceBean() {
        P6SpyAutoConfiguration configuration = new P6SpyAutoConfiguration();
        JdbcEventListenerFactory factory = configuration.jdbcEventListenerFactory();

        P6DriverManagerDataSource delegate = new P6DriverManagerDataSource();
        delegate.setUrl("jdbc:h2:mem:test");
        delegate.setUser("sa");
        delegate.setPassword("");

        ObjectProvider<DataSource> dsProvider = simpleProvider(delegate);

        P6OptionChangedListener listener = new P6LogQuery();
        ObjectProvider<P6OptionChangedListener> listenerProvider = simpleProvider(listener);

        P6DataSource p6DataSource = configuration.p6DataSource(dsProvider, listenerProvider, factory);
        assertThat(p6DataSource).isNotNull();
        assertThat(p6DataSource).isInstanceOf(P6DataSource.class);
    }

    @Test
    @DisplayName("p6DataSource processes multiple listeners via stream")
    void testP6DataSourceWithMultipleListeners() {
        P6SpyAutoConfiguration configuration = new P6SpyAutoConfiguration();
        JdbcEventListenerFactory factory = configuration.jdbcEventListenerFactory();

        P6DriverManagerDataSource delegate = new P6DriverManagerDataSource();
        delegate.setUrl("jdbc:h2:mem:test");
        delegate.setUser("sa");
        delegate.setPassword("");

        ObjectProvider<DataSource> dsProvider = simpleProvider(delegate);

        P6OptionChangedListener listener = new P6LogQuery();
        ObjectProvider<P6OptionChangedListener> listenerProvider = simpleProvider(listener);

        P6DataSource p6DataSource = configuration.p6DataSource(dsProvider, listenerProvider, factory);
        assertThat(p6DataSource).isNotNull();
    }

    private <T> ObjectProvider<T> simpleProvider(T instance) {
        return new ObjectProvider<T>() {
            @Override
            public T getObject() {
                return instance;
            }
            @Override
            public T getObject(Object... args) {
                return instance;
            }
            @Override
            public T getIfAvailable() {
                return instance;
            }
            @Override
            public T getIfUnique() {
                return instance;
            }
            @Override
            public Stream<T> stream() {
                return Stream.of(instance);
            }
        };
    }

}
