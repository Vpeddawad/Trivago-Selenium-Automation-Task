package runner.trivago;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


	@RunWith(Cucumber.class)
	@CucumberOptions(
		features = { "src/test/java/Features/trivago/search.feature" }, glue = { "stepDefinitions" },
		format = { "pretty", "html:test-output-trivago", "json:json_output/cucumber.json",
				"junit:junit_xml/cucumber.xml" }, // to
		monochrome = true, strict = true, dryRun = false
// , tags = { "~@SmokeTest", "~@RegressionTest", "~@End2End" }
			)
	 
public class TrivagoSearchRunnerTest {
	 
	}
	
	