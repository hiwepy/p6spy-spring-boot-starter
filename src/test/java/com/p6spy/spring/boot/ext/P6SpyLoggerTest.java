package com.p6spy.spring.boot.ext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.p6spy.engine.logging.Category;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link P6SpyLogger}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@DisplayName("P6SpyLogger Tests")
class P6SpyLoggerTest {

    @Test
    @DisplayName("Can instantiate P6SpyLogger")
    void testInstantiation() {
        P6SpyLogger logger = new P6SpyLogger();
        assertThat(logger).isNotNull();
    }

    @Test
    @DisplayName("logText does not throw for normal text")
    void testLogTextNormal() {
        P6SpyLogger logger = new P6SpyLogger();
        logger.logText("test message");
        // should not throw
    }

    @Test
    @DisplayName("logText handles text with pipe separator and SQL")
    void testLogTextWithSql() {
        P6SpyLogger logger = new P6SpyLogger();
        logger.logText("header|SELECT * FROM users WHERE id = 1");
        // should not throw
    }

    @Test
    @DisplayName("logText handles text with semicolon only content")
    void testLogTextWithSemicolon() {
        P6SpyLogger logger = new P6SpyLogger();
        logger.logText("header|;");
        // should not throw
    }

    @Test
    @DisplayName("logText handles single segment (no pipe)")
    void testLogTextSingleSegment() {
        P6SpyLogger logger = new P6SpyLogger();
        logger.logText("simple message without pipe");
        // should not throw
    }

    @Test
    @DisplayName("logSQL skips commit category")
    void testLogSqlSkipsCommit() {
        P6SpyLogger logger = new P6SpyLogger();
        logger.logSQL(1, "now", 100, Category.COMMIT, "commit", "commit", "url");
        // should not throw and should skip
    }

    @Test
    @DisplayName("logSQL processes non-commit category")
    void testLogSqlProcessesNonCommit() {
        P6SpyLogger logger = new P6SpyLogger();
        logger.logSQL(1, "now", 100, Category.STATEMENT, "SELECT * FROM t", "SELECT * FROM t", "url");
        // should not throw
    }

    @Test
    @DisplayName("logSQL skips select count queries")
    void testLogSqlSkipsSelectCount() {
        P6SpyLogger logger = new P6SpyLogger();
        logger.logSQL(1, "now", 100, Category.STATEMENT, "select count(*) from t", "select count(*) from t", "url");
        // should not throw and should skip
    }

}
