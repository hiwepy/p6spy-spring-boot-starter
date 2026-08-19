package com.p6spy.spring.boot.ext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.BatchFileLogger;

/**
 * <p>P6Spy logger that formats SQL output using {@link SQLFormatter} and logs via SLF4J.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 * @see <a href="https://www.cnblogs.com/luodengxiong/p/6766357.html">Original reference</a>
 */
public class P6SpyLogger extends BatchFileLogger {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * <p>Logs SQL execution, filtering out commit statements and count queries.</p>
     *
     * @param connectionId the connection identifier
     * @param now          the current timestamp
     * @param elapsed      the execution duration in milliseconds
     * @param category     the statement category (statement, resultset, etc.)
     * @param prepared     the prepared statement template with parameter placeholders
     * @param sql          the SQL with actual parameter values substituted
     * @param url          the database URL
     */
    @Override
    /**
     * <p>Log s q l.</p>
     * @param connectionId
     * @param now
     * @param elapsed
     * @param category
     * @param prepared
     * @param sql
     * @param url
     */
    public void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql, String url) {
        if(!Category.COMMIT.equals(category) && !prepared.startsWith("select count("))
        this.logText(this.strategy.formatMessage(connectionId, now, elapsed, category.toString(), "-prepared-", sql, url));
    }

    /**
     * <p>Logs text with SQL formatting applied to the SQL portion.</p>
     *
     * @param text the raw log text to format and log
     */
    @Override
    /**
     * <p>Log text.</p>
     * @param text
     */
    public void logText(String text) {
        StringBuilder sb = new StringBuilder();
        //匹配到最后一个|作为分隔符
        String[] arrString = text.split("\\|(?![^\\|]*\\|)");
        if (arrString.length > 1) {
            sb.append(arrString[0]);
            //去最后一段语句做替换进行格式化
            String sss=arrString[1].trim();
            if(StringUtils.hasText(sss) && !";".equalsIgnoreCase(sss)){
                String sql = new SQLFormatter().format(arrString[1]);
                sb.append("\r\n");
                sb.append(sql);
                sb.append("\r\n");
            }else {
                sb.append(sss);
            }
            //this.getStream().println(sb.toString());
            logger.debug(sb.toString());
        } else {
            //this.getStream().println(text);
            logger.debug(text);
        }
        arrString = null;

    }

}