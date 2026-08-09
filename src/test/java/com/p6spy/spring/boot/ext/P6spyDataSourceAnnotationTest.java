package com.p6spy.spring.boot.ext;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link P6spyDataSource} annotation.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("P6spyDataSource Annotation Tests")
class P6spyDataSourceAnnotationTest {

    @Test
    @DisplayName("Annotation is present on qualified method")
    void testAnnotationPresent() throws NoSuchMethodException {
        Method method = AnnotatedClass.class.getMethod("dataSource");
        assertThat(method.isAnnotationPresent(P6spyDataSource.class)).isTrue();
    }

    @Test
    @DisplayName("Annotation is also a Qualifier")
    void testIsQualifier() {
        assertThat(P6spyDataSource.class.isAnnotationPresent(Qualifier.class)).isTrue();
    }

    @Test
    @DisplayName("Annotation retention is RUNTIME")
    void testRetention() {
        assertThat(P6spyDataSource.class.getAnnotation(java.lang.annotation.Retention.class))
                .isNotNull();
    }

    @Test
    @DisplayName("Annotation target includes FIELD, METHOD, PARAMETER, TYPE, ANNOTATION_TYPE")
    void testTarget() {
        java.lang.annotation.Target target = P6spyDataSource.class.getAnnotation(java.lang.annotation.Target.class);
        assertThat(target).isNotNull();
        assertThat(target.value()).contains(
                java.lang.annotation.ElementType.FIELD,
                java.lang.annotation.ElementType.METHOD,
                java.lang.annotation.ElementType.PARAMETER,
                java.lang.annotation.ElementType.TYPE,
                java.lang.annotation.ElementType.ANNOTATION_TYPE
        );
    }

    static class AnnotatedClass {
        @P6spyDataSource
        public Object dataSource() {
            return new Object();
        }
    }

}
