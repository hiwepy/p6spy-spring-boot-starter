package com.p6spy.spring.boot.ext;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.FormattedLogger;
import com.p6spy.engine.spy.appender.P6Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  
/**
 * <p>P6Spy logger implementation backed by SLF4J.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class Slf4jLogger extends FormattedLogger implements P6Logger{

    private static final Logger logger = LoggerFactory.getLogger("p6spy");

    /** @return the last logged entry */ public String getLastEntry() { return lastEntry; }
    /** @param lastEntry the last logged entry */ public void setLastEntry(String lastEntry) { this.lastEntry = lastEntry; }

    protected String lastEntry;

    /**
     * <p>Logs SQL execution details, filtering out resultset category entries.</p>
     *
     * @param connectionId the connection identifier
     * @param s            the timestamp
     * @param l            the elapsed time in milliseconds
     * @param category     the SQL category
     * @param s1           the prepared statement
     * @param sql          the executed SQL
     * @param url          the database URL
     */
    @Override
    /**
     * <p>Log s q l.</p>
     * @param connectionId
     * @param s
     * @param l
     * @param category
     * @param s1
     * @param sql
     * @param url
     */
    public void logSQL(int connectionId, String s, long l, Category category, String s1,String sql, String url) {
        if (!"resultset".equals(category.getName())) {
            logger.info(trim(sql));
        }
    }

    /**
     * <p>Logs an exception at error level.</p>
     *
     * @param e the exception to log
     */
    @Override
    /**
     * <p>Log exception.</p>
     * @param e
     */
    public void logException(Exception e) {
        logger.error(e.getMessage(),e);
    }

    /** {@inheritDoc} */ @Override public void logText(String s) {
        logger.info(s);
        this.setLastEntry(s);
    }

    /** {@inheritDoc} */ @Override public boolean isCategoryEnabled(Category category) {
        return true;
    }

    /**
     * <p>Trims and normalizes whitespace in the SQL string.</p>
     *
     * @param sql the raw SQL string
     * @return the trimmed SQL string
     */
    private String trim(String sql){
        StringBuilder sb = new StringBuilder("\r\n");
        sb.append(sql.replaceAll("\n|\r|\t|'  '"," "));
        return sb.toString();
    }

}