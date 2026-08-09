package com.p6spy.spring.boot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link P6SpyProperties}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("P6SpyProperties Tests")
class P6SpyPropertiesTest {

    @Test
    @DisplayName("Default enabled value is false")
    void testDefaultEnabled() {
        P6SpyProperties props = new P6SpyProperties();
        assertThat(props.isEnabled()).isFalse();
    }

    @Test
    @DisplayName("Setter and getter for enabled work correctly")
    void testEnabledSetterGetter() {
        P6SpyProperties props = new P6SpyProperties();
        props.setEnabled(true);
        assertThat(props.isEnabled()).isTrue();
        props.setEnabled(false);
        assertThat(props.isEnabled()).isFalse();
    }

    @Test
    @DisplayName("PREFIX constant has expected value")
    void testPrefix() {
        assertThat(P6SpyProperties.PREFIX).isEqualTo("spring.datasource.p6spy");
    }

}
