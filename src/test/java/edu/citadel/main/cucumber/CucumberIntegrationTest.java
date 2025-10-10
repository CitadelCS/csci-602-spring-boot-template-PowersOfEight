package edu.citadel.main.cucumber;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("edu.citadel.main.cucumber")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "edu.citadel.main.cucumber")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "classpath:edu/citadel/main/cucumber")
public class CucumberIntegrationTest {
}
