package com.p6spy.spring.boot.ext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link SQLFormatter}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@DisplayName("SQLFormatter Tests")
class SQLFormatterTest {

    private final SQLFormatter formatter = new SQLFormatter();

    @Test
    @DisplayName("Can instantiate SQLFormatter")
    void testInstantiation() {
        assertThat(formatter).isNotNull();
    }

    @Test
    @DisplayName("Format simple SELECT statement")
    void testFormatSelect() {
        String result = formatter.format("select * from users");
        assertThat(result).containsIgnoringCase("select");
        assertThat(result).containsIgnoringCase("from");
        assertThat(result).containsIgnoringCase("users");
    }

    @Test
    @DisplayName("Format INSERT statement")
    void testFormatInsert() {
        String result = formatter.format("insert into users (name, age) values ('John', 25)");
        assertThat(result).containsIgnoringCase("insert");
        assertThat(result).containsIgnoringCase("values");
    }

    @Test
    @DisplayName("Format UPDATE statement")
    void testFormatUpdate() {
        String result = formatter.format("update users set name = 'Jane' where id = 1");
        assertThat(result).containsIgnoringCase("update");
        assertThat(result).containsIgnoringCase("where");
    }

    @Test
    @DisplayName("Format DELETE statement")
    void testFormatDelete() {
        String result = formatter.format("delete from users where id = 1");
        assertThat(result).containsIgnoringCase("delete");
        assertThat(result).containsIgnoringCase("where");
    }

    @Test
    @DisplayName("Format SELECT with JOIN")
    void testFormatSelectWithJoin() {
        String result = formatter.format("select u.name, o.total from users u inner join orders o on u.id = o.user_id");
        assertThat(result).containsIgnoringCase("inner");
        assertThat(result).containsIgnoringCase("join");
    }

    @Test
    @DisplayName("Format SELECT with WHERE and AND")
    void testFormatSelectWithWhereAnd() {
        String result = formatter.format("select * from users where id = 1 and name = 'John'");
        assertThat(result).containsIgnoringCase("where");
        assertThat(result).containsIgnoringCase("and");
    }

    @Test
    @DisplayName("Format SELECT with subquery parentheses")
    void testFormatSelectWithParentheses() {
        String result = formatter.format("select * from users where id in (select user_id from orders)");
        assertThat(result).containsIgnoringCase("select");
        assertThat(result).containsIgnoringCase("in");
    }

    @Test
    @DisplayName("Format SELECT with GROUP BY")
    void testFormatSelectWithGroupBy() {
        String result = formatter.format("select count(*) from users group by age");
        assertThat(result).containsIgnoringCase("group");
        assertThat(result).containsIgnoringCase("by");
    }

    @Test
    @DisplayName("Format SELECT with ORDER BY")
    void testFormatSelectWithOrderBy() {
        String result = formatter.format("select * from users order by name");
        assertThat(result).containsIgnoringCase("order");
        assertThat(result).containsIgnoringCase("by");
    }

    @Test
    @DisplayName("Format SELECT with HAVING")
    void testFormatSelectWithHaving() {
        String result = formatter.format("select age, count(*) from users group by age having count(*) > 1");
        assertThat(result).containsIgnoringCase("having");
    }

    @Test
    @DisplayName("Format SELECT with UNION")
    void testFormatSelectWithUnion() {
        String result = formatter.format("select name from users union select name from admins");
        assertThat(result).containsIgnoringCase("union");
    }

    @Test
    @DisplayName("Format SELECT with BETWEEN")
    void testFormatSelectWithBetween() {
        String result = formatter.format("select * from users where age between 18 and 30");
        assertThat(result).containsIgnoringCase("between");
        assertThat(result).containsIgnoringCase("and");
    }

    @Test
    @DisplayName("Format SELECT with function calls")
    void testFormatSelectWithFunctions() {
        String result = formatter.format("select upper(name), count(*) from users");
        assertThat(result).containsIgnoringCase("upper");
    }

    @Test
    @DisplayName("Format SELECT with CASE expression")
    void testFormatSelectWithCase() {
        String result = formatter.format("select case when age > 18 then 'adult' else 'minor' end from users");
        assertThat(result).containsIgnoringCase("case");
    }

    @Test
    @DisplayName("Format SELECT with OR logical operator")
    void testFormatSelectWithOr() {
        String result = formatter.format("select * from users where id = 1 or id = 2");
        assertThat(result).containsIgnoringCase("or");
    }

    @Test
    @DisplayName("Format SELECT with INTO")
    void testFormatSelectWithInto() {
        String result = formatter.format("select * into backup from users");
        assertThat(result).containsIgnoringCase("into");
    }

    @Test
    @DisplayName("WHITESPACE constant is defined")
    void testWhitespaceConstant() {
        assertThat(SQLFormatter.WHITESPACE).isNotNull();
        assertThat(SQLFormatter.WHITESPACE).contains(" ", "\n", "\r", "\t");
    }

}
