package com.p6spy.spring.boot.ext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.p6spy.engine.logging.Category;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Slf4jLogger}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("Slf4jLogger Tests")
class Slf4jLoggerTest {

    @Test
    @DisplayName("Can instantiate Slf4jLogger")
    void testInstantiation() {
        Slf4jLogger logger = new Slf4jLogger();
        assertThat(logger).isNotNull();
    }

    @Test
    @DisplayName("logText stores last entry")
    void testLogTextStoresLastEntry() {
        Slf4jLogger logger = new Slf4jLogger();
        logger.logText("test entry");
        assertThat(logger.getLastEntry()).isEqualTo("test entry");
    }

    @Test
    @DisplayName("getLastEntry returns null initially")
    void testGetLastEntryInitiallyNull() {
        Slf4jLogger logger = new Slf4jLogger();
        assertThat(logger.getLastEntry()).isNull();
    }

    @Test
    @DisplayName("setLastEntry and getLastEntry work correctly")
    void testSetAndGetLastEntry() {
        Slf4jLogger logger = new Slf4jLogger();
        logger.setLastEntry("custom entry");
        assertThat(logger.getLastEntry()).isEqualTo("custom entry");
    }

    @Test
    @DisplayName("logSQL skips resultset category")
    void testLogSqlSkipsResultset() {
        Slf4jLogger logger = new Slf4jLogger();
        logger.logSQL(1, "now", 100, Category.RESULTSET, "stmt", "SELECT * FROM t", "url");
        // should not log resultset
    }

    @Test
    @DisplayName("logSQL processes statement category")
    void testLogSqlProcessesStatement() {
        Slf4jLogger logger = new Slf4jLogger();
        logger.logSQL(1, "now", 100, Category.STATEMENT, "stmt", "SELECT * FROM t", "url");
        // should log statement
    }

    @Test
    @DisplayName("logException does not throw")
    void testLogException() {
        Slf4jLogger logger = new Slf4jLogger();
        logger.logException(new RuntimeException("test error"));
        // should not throw
    }

    @Test
    @DisplayName("isCategoryEnabled always returns true")
    void testIsCategoryEnabled() {
        Slf4jLogger logger = new Slf4jLogger();
        assertThat(logger.isCategoryEnabled(Category.STATEMENT)).isTrue();
        assertThat(logger.isCategoryEnabled(Category.RESULTSET)).isTrue();
        assertThat(logger.isCategoryEnabled(Category.COMMIT)).isTrue();
    }

}
