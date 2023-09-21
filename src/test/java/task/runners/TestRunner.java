package task.runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = "task/step_defs",
        stepNotifications = true,
        dryRun = false,
        tags = "@TC-2"
)
public class TestRunner {
}
