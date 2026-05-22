package com.faculdade.gerenciador_academico;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features") // Aponta para a pasta src/test/resources/features
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.faculdade.gerenciador_academico.bdd")
public class CucumberRunnerTest {
}