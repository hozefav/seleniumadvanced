package com.planittesting.automation.environment;

import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EnvironmentVariables {

	private static final Logger log = LoggerFactory.getLogger(EnvironmentVariables.class);

	private static final String SELENIUM_HEADLESS = "SELENIUM_HEADLESS";
	private static final String SELENIUM_GRID_URL = "SELENIUM_GRID_URL";
	private static final String SELENIUM_BROWSER = "SELENIUM_BROWSER";
	private static final String SELENIUM_WAIT = "SELENIUM_WAIT";
	private static final String SELENIUM_URL = "SELENIUM_URL";

	private static final List<String> mandatoryVars;
	private static final List<String> missingVars;

	private EnvironmentVariables() {
	}

	static {
		mandatoryVars = Collections.unmodifiableList(
				List.of(SELENIUM_HEADLESS, SELENIUM_BROWSER, SELENIUM_WAIT, SELENIUM_URL));
		missingVars = mandatoryVars.stream().filter(v -> !System.getenv().containsKey(v) || System.getenv(v).isBlank()).collect(Collectors.toList());

		if (!missingVars.isEmpty()) {
			log.error(()->"Environment variables not set: " + missingVars.toString());
			throw new RuntimeException("Environment variables not set: " + missingVars.toString());
		}

		if (!System.getenv().containsKey(SELENIUM_GRID_URL)) {
			log.info(() -> "Grid URL not set, using local WebDriver instance");
		}
	}

	public static boolean isHeadless() {
		return Boolean.parseBoolean(System.getenv(SELENIUM_HEADLESS));
	}

	public static String getGridUrl() {
		return System.getenv(SELENIUM_GRID_URL);
	}

	public static String getBrowser() {
		return System.getenv(SELENIUM_BROWSER);
	}

	public static int getImplicitWait() {
		return Integer.parseInt(System.getenv(SELENIUM_WAIT));
	}

	public static String getUrl() {
		return System.getenv(SELENIUM_URL);
	}

}