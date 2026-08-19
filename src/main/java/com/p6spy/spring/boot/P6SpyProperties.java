package com.p6spy.spring.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Configuration properties for P6Spy integration.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@ConfigurationProperties(P6SpyProperties.PREFIX)
public class P6SpyProperties {

	public static final String PREFIX = "spring.datasource.p6spy";

	/**
	 * Enable P6Spy.
	 */
	private boolean enabled = false;

	/** @return whether P6Spy is enabled */ public boolean isEnabled() { return enabled; }
	/** @param enabled whether to enable P6Spy */ public void setEnabled(boolean enabled) { this.enabled = enabled; }

}